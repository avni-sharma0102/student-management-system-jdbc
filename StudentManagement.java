import java.sql.*;
import java.util.Scanner;

public class StudentManagement {

    static final String URL = "jdbc:mysql://localhost:3306/student";
    static final String USER = "root";
    static final String PASSWORD = "root123";

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

            while (true) {
                System.out.println("\n1. Add Student");
                System.out.println("2. View Students");
                System.out.println("3. Update Student");
                System.out.println("4. Delete Student");
                System.out.println("5. Exit");

                int choice = sc.nextInt();

                switch (choice) {

                    case 1:
                        System.out.print("Enter Name: ");
                        String name = sc.next();

                        System.out.print("Enter Age: ");
                        int age = sc.nextInt();

                        System.out.print("Enter Course: ");
                        String course = sc.next();

                        String insertQuery = "INSERT INTO students(name, age, course) VALUES (?, ?, ?)";
                        PreparedStatement ps = con.prepareStatement(insertQuery);

                        ps.setString(1, name);
                        ps.setInt(2, age);
                        ps.setString(3, course);

                        ps.executeUpdate();
                        System.out.println("Student Added!");
                        break;

                    case 2:
                        String selectQuery = "SELECT * FROM students";
                        Statement st = con.createStatement();
                        ResultSet rs = st.executeQuery(selectQuery);

                        System.out.println("\n--- Student Records ---");

                        while (rs.next()) {
                            System.out.println(
                                rs.getInt("id") + " | " +
                                rs.getString("name") + " | " +
                                rs.getInt("age") + " | " +
                                rs.getString("course")
                            );
                        }
                        break;

                    case 3:
                        System.out.print("Enter Student ID to update: ");
                        int updateId = sc.nextInt();

                        System.out.print("Enter New Name: ");
                        String newName = sc.next();

                        System.out.print("Enter New Age: ");
                        int newAge = sc.nextInt();

                        System.out.print("Enter New Course: ");
                        String newCourse = sc.next();

                        String updateQuery = "UPDATE students SET name=?, age=?, course=? WHERE id=?";
                        PreparedStatement ps2 = con.prepareStatement(updateQuery);

                        ps2.setString(1, newName);
                        ps2.setInt(2, newAge);
                        ps2.setString(3, newCourse);
                        ps2.setInt(4, updateId);

                        int rows = ps2.executeUpdate();

                        if (rows > 0) {
                            System.out.println("Student Updated!");
                        } else {
                            System.out.println("No student found!");
                        }
                        break;

                    case 4:
                        System.out.print("Enter Student ID to delete: ");
                        int deleteId = sc.nextInt();

                        String deleteQuery = "DELETE FROM students WHERE id=?";
                        PreparedStatement ps3 = con.prepareStatement(deleteQuery);

                        ps3.setInt(1, deleteId);

                        int delRows = ps3.executeUpdate();

                        if (delRows > 0) {
                            System.out.println("Student Deleted!");
                        } else {
                            System.out.println("No student found!");
                        }
                        break;

                    case 5:
                        System.exit(0);

                    default:
                        System.out.println("Invalid choice");
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}