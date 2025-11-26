package in.kenz.dao;

import in.kenz.model.Student;

import java.util.List;

public interface StudentDao {

    public Long addStudent (Student student);
    List<Student> findByStudentID(Long studentId);
    List<Student> findAllStudents();
    Student updateStudent(Student student);
    Student deleteByStudentId(Student student);
}
