package in.kenz;

import in.kenz.dao.impl.StudentDaoImpl;
import in.kenz.model.Student;
import java.util.List;
import java.util.Scanner;
//Used IDE: IntelliJ
public class App {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        StudentDaoImpl dao = new StudentDaoImpl();

        int choice;

        do {
            System.out.println("\n===== STUDENT MANAGEMENT MENU =====");
            System.out.println("1. Add Student");
            System.out.println("2. Find Student by ID");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. List All Students");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();
            sc.nextLine(); // clear newline

            switch (choice) {

                case 1:
                    System.out.print("Enter Student Name: ");
                    String name = sc.nextLine();
                    Student s = new Student(name);

                    Long id = dao.addStudent(s);
                    System.out.println("Saved Successfully! Student ID = " + id);
                    break;

                case 2:
                    System.out.print("Enter Student ID: ");
                    Long findId = sc.nextLong();

                    List<Student> found = dao.findByStudentID(findId);
                    if (found.isEmpty()) {
                        System.out.println("No student found.");
                    } else {
                        found.forEach(System.out::println);
                    }
                    break;

                case 3:
                    System.out.print("Enter Student ID to Update: ");
                    Long updateId = sc.nextLong();
                    sc.nextLine(); // clear newline

                    List<Student> f = dao.findByStudentID(updateId);
                    if (f.isEmpty()) {
                        System.out.println("Student not found.");
                        break;
                    }

                    Student existing = f.get(0);
                    System.out.println("Current Name: " + existing.getStudentName());
                    System.out.print("Enter New Name: ");
                    String newName = sc.nextLine();

                    existing.setStudentName(newName);
                    Student updated = dao.updateStudent(existing);
                    System.out.println("Updated Successfully: " + updated);

                    break;

                case 4:
                    System.out.print("Enter Student ID to Delete: ");
                    Long delId = sc.nextLong();

                    List<Student> d = dao.findByStudentID(delId);
                    if (d.isEmpty()) {
                        System.out.println("Student not found.");
                        break;
                    }

                    Student deleted = dao.deleteByStudentId(d.get(0));
                    System.out.println("Deleted Successfully: " + deleted);
                    break;

                case 5:
                    System.out.println("\n--- All Students ---");
                    dao.findAllStudents().forEach(System.out::println);
                    break;

                case 0:
                    System.out.println("Exiting... Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option! Please try again.");
            }

        } while (choice != 0);

        sc.close();
    }
}
