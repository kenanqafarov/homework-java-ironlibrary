package com.ironlibrary.ironLibraryApp.dao;

import com.ironlibrary.ironLibraryApp.model.Author;
import com.ironlibrary.ironLibraryApp.model.Book;
import com.ironlibrary.ironLibraryApp.util.DatabaseConnection;
import java.sql.*;


//Author DAO
public class AuthorDAO {

//  Adding new author
    public void addAuthor(Author author) {
        String sql = "INSERT INTO author (name, email, book_isbn) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, author.getName());
            pstmt.setString(2, author.getEmail());
            pstmt.setString(3, author.getAuthorBook().getIsbn());

            pstmt.executeUpdate();

            // Get generated author_id
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    author.setAuthorId(rs.getInt(1));
                }
            }
            System.out.println("Author added successfully.");
        } catch (SQLException e) {
            System.err.println("Error adding author: " + e.getMessage());
        }
    }


//
    public Author findByBookIsbn(String isbn) {
        String sql = "SELECT a.*, b.* FROM author a JOIN book b ON a.book_isbn = b.isbn WHERE a.book_isbn = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, isbn);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Book book = new Book(
                        rs.getString("b.isbn"),
                        rs.getString("b.title"),
                        rs.getString("b.category"),
                        rs.getInt("b.quantity")
                );

                Author author = new Author(
                        rs.getString("a.name"),
                        rs.getString("a.email"),
                        book
                );
                author.setAuthorId(rs.getInt("a.author_id"));
                return author;
            }
        } catch (SQLException e) {
            System.err.println("Error finding author by book: " + e.getMessage());
        }
        return null;
    }

    /**
     * Finds author by name (for search by author).
     */
    public Author findByName(String name) {
        String sql = "SELECT a.*, b.* FROM author a JOIN book b ON a.book_isbn = b.isbn WHERE a.name LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + name + "%");
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Book book = new Book(
                        rs.getString("b.isbn"),
                        rs.getString("b.title"),
                        rs.getString("b.category"),
                        rs.getInt("b.quantity")
                );

                Author author = new Author(rs.getString("a.name"), rs.getString("a.email"), book);
                author.setAuthorId(rs.getInt("a.author_id"));
                return author;
            }
        } catch (SQLException e) {
            System.err.println("Error finding author by name: " + e.getMessage());
        }
        return null;
    }
}