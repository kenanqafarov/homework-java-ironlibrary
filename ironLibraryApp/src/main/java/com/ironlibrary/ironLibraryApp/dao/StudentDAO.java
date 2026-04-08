package com.ironlibrary.ironLibraryApp.dao;

import com.ironlibrary.ironLibraryApp.model.Student;
import com.ironlibrary.ironLibraryApp.util.DatabaseConnection;
import java.sql.*;


// Student DAO
public class StudentDAO {

//    Add Student or update data
    public void addOrUpdateStudent(Student student) {
        String sql = "INSERT INTO student (usn, name) VALUES (?, ?) " +
                "ON DUPLICATE KEY UPDATE name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, student.getUsn());
            pstmt.setString(2, student.getName());
            pstmt.setString(3, student.getName());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error adding/updating student: " + e.getMessage());
        }
    }


//    Finding student by their 'usn'
    public Student findByUsn(String usn) {
        String sql = "SELECT * FROM student WHERE usn = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usn);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Student(rs.getString("usn"), rs.getString("name"));
            }
        } catch (SQLException e) {
            System.err.println("Error finding student: " + e.getMessage());
        }
        return null;
    }
}