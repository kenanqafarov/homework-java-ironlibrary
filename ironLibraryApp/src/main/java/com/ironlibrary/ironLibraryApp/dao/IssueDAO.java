package com.ironlibrary.ironLibraryApp.dao;

import com.ironlibrary.ironLibraryApp.model.Issue;
import com.ironlibrary.ironLibraryApp.model.Student;
import com.ironlibrary.ironLibraryApp.model.Book;
import com.ironlibrary.ironLibraryApp.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


//Issue DAO
public class IssueDAO {


//    Adding issue to db
    public void addIssue(Issue issue) {
        String sql = "INSERT INTO issue (issue_date, return_date, student_usn, book_isbn) " +
                "VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, issue.getIssueDate());
            pstmt.setString(2, issue.getReturnDate());
            pstmt.setString(3, issue.getIssueStudent().getUsn());
            pstmt.setString(4, issue.getIssueBook().getIsbn());

            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    issue.setIssueId(rs.getInt(1));
                }
            }
            System.out.println("Issue record created.");
        } catch (SQLException e) {
            System.err.println("Error issuing book: " + e.getMessage());
        }
    }

//    Searching all data due to their 'usn data'
    public List<Issue> getIssuesByStudentUsn(String usn) {
        List<Issue> issues = new ArrayList<>();
        String sql = "SELECT i.*, s.name as student_name, b.title as book_title " +
                "FROM issue i " +
                "JOIN student s ON i.student_usn = s.usn " +
                "JOIN book b ON i.book_isbn = b.isbn " +
                "WHERE i.student_usn = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usn);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Student student = new Student(rs.getString("student_usn"), rs.getString("student_name"));
                Book book = new Book(rs.getString("book_isbn"), rs.getString("book_title"), "", 0);

                Issue issue = new Issue(
                        rs.getString("issue_date"),
                        rs.getString("return_date"),
                        student,
                        book
                );
                issue.setIssueId(rs.getInt("issue_id"));
                issues.add(issue);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving issues by student: " + e.getMessage());
        }
        return issues;
    }

    // Bonus: Books to be returned today (optional)
    public List<Issue> getBooksToReturnToday() {
        // Implement similarly using CURRENT_DATE comparison if needed
        return new ArrayList<>();
    }
}