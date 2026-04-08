package com.ironlibrary.ironLibraryApp.service;

import com.ironlibrary.ironLibraryApp.dao.*;
import com.ironlibrary.ironLibraryApp.model.*;
import java.time.LocalDate;
import java.util.List;

public class LibraryService {

    private final BookDAO bookDAO = new BookDAO();
    private final AuthorDAO authorDAO = new AuthorDAO();
    private final StudentDAO studentDAO = new StudentDAO();
    private final IssueDAO issueDAO = new IssueDAO();

    public void addBookWithAuthor(String isbn, String title, String category, int quantity,
                                  String authorName, String authorEmail) {
        Book book = new Book(isbn, title, category, quantity);
        bookDAO.addBook(book);

        Author author = new Author(authorName, authorEmail, book);
        authorDAO.addAuthor(author);
    }

    public void issueBook(String usn, String studentName, String isbn) {
        Book book = bookDAO.findByIsbn(isbn);
        if (book == null) {
            System.out.println("Book not found: " + isbn);
            return;
        }
        if (book.getQuantity() <= 0) {
            System.out.println("Book is not available.");
            return;
        }

        Student student = new Student(usn, studentName);
        studentDAO.addOrUpdateStudent(student);

        String issueDate = LocalDate.now().toString();
        String returnDate = LocalDate.now().plusDays(7).toString();

        Issue issue = new Issue(issueDate, returnDate, student, book);
        issueDAO.addIssue(issue);

        book.setQuantity(book.getQuantity() - 1);
        bookDAO.updateBook(book);

        System.out.println("Book issued. Return date: " + returnDate);
    }

    public Book searchBookByTitle(String title) {
        Book book = bookDAO.searchByTitle(title);
        if (book == null) {
            System.out.println("Book not found: " + title);
        }
        return book;
    }

    public List<Book> searchBooksByCategory(String category) {
        List<Book> books = bookDAO.searchByCategory(category);
        if (books.isEmpty()) {
            System.out.println("No books found in category: " + category);
        }
        return books;
    }

    public List<Book> searchBooksByAuthor(String author) {
        List<Book> books = bookDAO.searchByAuthor(author);
        if (books.isEmpty()) {
            System.out.println("No books found for author: " + author);
        }
        return books;
    }

    public List<Book> getAllBooks() {
        return bookDAO.getAllBooks();
    }

    public Author findAuthorByIsbn(String isbn) {
        Author author = authorDAO.findByBookIsbn(isbn);
        if (author == null) {
            System.out.println("Author not found for ISBN: " + isbn);
        }
        return author;
    }

    public Author findAuthorByName(String name) {
        Author author = authorDAO.findByName(name);
        if (author == null) {
            System.out.println("Author not found: " + name);
        }
        return author;
    }

    public List<Issue> getIssuesByStudent(String usn) {
        List<Issue> issues = issueDAO.getIssuesByStudentUsn(usn);
        if (issues.isEmpty()) {
            System.out.println("No active issues for student: " + usn);
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
}