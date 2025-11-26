package in.kenz.dao.impl;

import in.kenz.dao.StudentDao;
import in.kenz.model.Student;
import in.kenz.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Collections;
import java.util.List;


public class StudentDaoImpl implements StudentDao {

    @Override
    public Long addStudent(Student student) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(student);           // student becomes managed, id generated
            tx.commit();
            return student.getStudentId();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    @Override
    public List<Student> findByStudentID(Long studentId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Student s = session.get(Student.class, studentId);
            if (s == null) {
                return Collections.emptyList();
            }
            return Collections.singletonList(s);

        }
    }

    @Override
    public List<Student> findAllStudents() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Student", Student.class).list();
        }
    }

    @Override
    public Student updateStudent(Student student) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Student merged = session.merge(student);
            tx.commit();
            return merged;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    @Override
    public Student deleteByStudentId(Student student) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            // ensure we have a managed instance
            Student managed = student;
            if (student.getStudentId() != null) {
                managed = session.get(Student.class, student.getStudentId());
            }
            if (managed == null) {
                tx.rollback();
                return null;
            }
            // copy to return after deletion (optional)
            Student toReturn = new Student();
            toReturn.setStudentId(managed.getStudentId());
            toReturn.setStudentName(managed.getStudentName());

            session.remove(managed);
            tx.commit();
            return toReturn;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }
}
