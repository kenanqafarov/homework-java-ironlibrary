package com.ironlibrary.ironLibraryApp.service;

import com.ironlibrary.ironLibraryApp.dao.*;
import com.ironlibrary.ironLibraryApp.model.*;
import java.time.LocalDate;
import java.util.List;

/**
 * LibraryService: Core business logic layer for library operations.
 * Handles book management, student records, author information, and book issue tracking.
 *
 * @author Library Management System
 * @version 2.0
 */
public class LibraryService {

    private final BookDAO bookDAO = new BookDAO();
    private final AuthorDAO authorDAO = new AuthorDAO();
    private final StudentDAO studentDAO = new StudentDAO();
    private final IssueDAO issueDAO = new IssueDAO();

    // ============================= BOOK MANAGEMENT =============================

    /**
     * Adds a new book to the library with associated author information.
     * Book is created first (with null author), then Author is created and linked back.
     *
     * @param isbn        Unique identifier for the book
     * @param title       Book title
     * @param category    Book category/genre
     * @param quantity    Available quantity (must be > 0)
     * @param authorName  Name of the author
     * @param authorEmail Email of the author (must be valid format)
     */
    public void addBookWithAuthor(String isbn, String title, String category, int quantity,
                                  String authorName, String authorEmail) {
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("ERROR: ISBN cannot be null or empty. Please provide a valid ISBN.");
        }
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("ERROR: Book title cannot be null or empty. Please enter a valid book title.");
        }
        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("ERROR: Category cannot be null or empty. Please specify a valid category.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("ERROR: Book quantity must be greater than 0. Please enter a positive number.");
        }
        if (authorName == null || authorName.trim().isEmpty()) {
            throw new IllegalArgumentException("ERROR: Author name cannot be null or empty. Please provide a valid author name.");
        }
        if (authorEmail == null || !isValidEmail(authorEmail)) {
            throw new IllegalArgumentException("ERROR: Invalid email format. Please provide a valid email address (e.g., author@example.com).");
        }

        // Step 1: Create book with null author (Author object does not exist yet)
        Book book = new Book(isbn, title, category, quantity, null);
        bookDAO.addBook(book);

        // Step 2: Create author with the book reference
        Author author = new Author(authorName, authorEmail, book);
        authorDAO.addAuthor(author);

        // Step 3: Link author back to the book and persist
        book.setAuthor(author);
        bookDAO.updateBook(book);

        System.out.println("SUCCESS: Book '" + title + "' with ISBN " + isbn + " and author '" + authorName + "' added successfully.");
    }

    /**
     * Issues a book to a student for a 7-day loan period.
     *
     * @param usn         Student Unique Serial Number
     * @param studentName Name of the student
     * @param isbn        ISBN of the book to issue
     */
    public void issueBook(String usn, String studentName, String isbn) {
        if (usn == null || usn.trim().isEmpty()) {
            throw new IllegalArgumentException("ERROR: Student USN cannot be null or empty. Please provide a valid student ID.");
        }
        if (studentName == null || studentName.trim().isEmpty()) {
            throw new IllegalArgumentException("ERROR: Student name cannot be null or empty. Please enter the student's name.");
        }
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("ERROR: ISBN cannot be null or empty. Please provide a valid book ISBN.");
        }

        Book book = bookDAO.findByIsbn(isbn);
        if (book == null) {
            throw new IllegalArgumentException("ERROR: Book with ISBN '" + isbn + "' not found in the library. Please verify the ISBN and try again.");
        }
        if (book.getQuantity() <= 0) {
            throw new IllegalArgumentException("ERROR: Book '" + book.getTitle() + "' is currently unavailable. All copies are already issued.");
        }

        Student student = new Student(usn, studentName);
        studentDAO.addOrUpdateStudent(student);

        String issueDate = LocalDate.now().toString();
        String returnDate = LocalDate.now().plusDays(7).toString();

        Issue issue = new Issue(issueDate, returnDate, student, book);
        issueDAO.addIssue(issue);

        book.setQuantity(book.getQuantity() - 1);
        bookDAO.updateBook(book);

        System.out.println("SUCCESS: Book '" + book.getTitle() + "' issued to " + studentName + ". Return date: " + returnDate);
    }

    // ============================= BOOK SEARCH =============================

    public Book searchBookByTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("ERROR: Search title cannot be null or empty. Please enter a valid book title.");
        }
        Book book = bookDAO.searchByTitle(title);
        if (book == null) {
            System.out.println("INFO: Book with title '" + title + "' not found.");
        } else {
            System.out.println("SUCCESS: Book found - '" + title + "' (ISBN: " + book.getIsbn() + ")");
        }
        return book;
    }

    public List<Book> searchBooksByCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("ERROR: Category cannot be null or empty. Please specify a valid category.");
        }
        List<Book> books = bookDAO.searchByCategory(category);
        if (books.isEmpty()) {
            System.out.println("INFO: No books found in category '" + category + "'.");
        } else {
            System.out.println("SUCCESS: Found " + books.size() + " book(s) in category '" + category + "'.");
        }
        return books;
    }

    public List<Book> searchBooksByAuthor(String author) {
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("ERROR: Author name cannot be null or empty. Please enter a valid author name.");
        }
        List<Book> books = bookDAO.searchByAuthor(author);
        if (books.isEmpty()) {
            System.out.println("INFO: No books found for author '" + author + "'.");
        } else {
            System.out.println("SUCCESS: Found " + books.size() + " book(s) by author '" + author + "'.");
        }
        return books;
    }

    public List<Book> getAllBooks() {
        List<Book> allBooks = bookDAO.getAllBooks();
        System.out.println("INFO: Retrieved " + allBooks.size() + " total book(s) from library.");
        return allBooks;
    }

    // ============================= AUTHOR SEARCH =============================

    public Author findAuthorByIsbn(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("ERROR: ISBN cannot be null or empty. Please provide a valid ISBN.");
        }
        Author author = authorDAO.findByBookIsbn(isbn);
        if (author == null) {
            System.out.println("INFO: Author not found for ISBN '" + isbn + "'.");
        } else {
            System.out.println("SUCCESS: Author '" + author.getName() + "' found for ISBN '" + isbn + "'.");
        }
        return author;
    }

    public Author findAuthorByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("ERROR: Author name cannot be null or empty. Please enter a valid author name.");
        }
        Author author = authorDAO.findByName(name);
        if (author == null) {
            System.out.println("INFO: Author '" + name + "' not found.");
        } else {
            System.out.println("SUCCESS: Author '" + author.getName() + "' found with email: " + author.getEmail());
        }
        return author;
    }

    // ============================= ISSUE MANAGEMENT =============================

    public List<Issue> getIssuesByStudent(String usn) {
        if (usn == null || usn.trim().isEmpty()) {
            throw new IllegalArgumentException("ERROR: Student USN cannot be null or empty. Please provide a valid student ID.");
        }
        List<Issue> issues = issueDAO.getIssuesByStudentUsn(usn);
        if (issues.isEmpty()) {
            System.out.println("INFO: No active book issues found for student '" + usn + "'.");
        } else {
            System.out.println("SUCCESS: Found " + issues.size() + " active issue(s) for student '" + usn + "'.");
        }
        return issues;
    }

    public Student findStudent(String usn) {
        Student student = studentDAO.findByUsn(usn);
        if (student == null) {
            System.out.println("Student not found: " + usn);
        }
        return student;
    }

    // ============================= UTILITY =============================

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email != null && email.matches(emailRegex) && email.contains(".");
    }
}