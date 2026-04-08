package com.ironlibrary.ironLibraryApp.dao;

import com.ironlibrary.ironLibraryApp.model.Book;
import com.ironlibrary.ironLibraryApp.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    public void addBook(Book book) {
        String sql = "INSERT INTO book (isbn, title, category, quantity) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, book.getIsbn());
            pstmt.setString(2, book.getTitle());
            pstmt.setString(3, book.getCategory());
            pstmt.setInt(4, book.getQuantity());

            pstmt.executeUpdate();
            System.out.println("Book added successfully.");
        } catch (SQLException e) {
            System.err.println("Error adding book: " + e.getMessage());
        }
    }

    public Book searchByTitle(String title) {
        String sql = "SELECT * FROM book WHERE title LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + title + "%");
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Book(
                        rs.getString("isbn"),
                        rs.getString("title"),
                        rs.getString("category"),
                        rs.getInt("quantity")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error searching book by title: " + e.getMessage());
        }
        return null;
    }

    public List<Book> searchByCategory(String category) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM book WHERE category LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + category + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                books.add(new Book(
                        rs.getString("isbn"),
                        rs.getString("title"),
                        rs.getString("category"),
                        rs.getInt("quantity")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error searching books by category: " + e.getMessage());
        }
        return books;
    }

    public List<Book> searchByAuthor(String author) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT b.* FROM book b JOIN author a ON b.isbn = a.book_isbn WHERE a.name LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + author + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                books.add(new Book(
                        rs.getString("isbn"),
                        rs.getString("title"),
                        rs.getString("category"),
                        rs.getInt("quantity")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error searching books by author: " + e.getMessage());
        }
        return books;
    }

    public Book findByIsbn(String isbn) {
        String sql = "SELECT * FROM book WHERE isbn = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, isbn);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Book(
                        rs.getString("isbn"),
                        rs.getString("title"),
                        rs.getString("category"),
                        rs.getInt("quantity")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error finding book by ISBN: " + e.getMessage());
        }
        return null;
    }

    public void updateBook(Book book) {
        String sql = "UPDATE book SET title = ?, category = ?, quantity = ? WHERE isbn = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getCategory());
            pstmt.setInt(3, book.getQuantity());
            pstmt.setString(4, book.getIsbn());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating book: " + e.getMessage());
        }
    }

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM book";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                books.add(new Book(
                        rs.getString("isbn"),
                        rs.getString("title"),
                        rs.getString("category"),
                        rs.getInt("quantity")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving all books: " + e.getMessage());
        }
        return books;
    }
}