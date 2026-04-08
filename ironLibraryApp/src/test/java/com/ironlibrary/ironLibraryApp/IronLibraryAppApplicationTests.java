package com.ironlibrary.ironLibraryApp;

import com.ironlibrary.ironLibraryApp.model.*;
import com.ironlibrary.ironLibraryApp.service.LibraryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive Unit Test Suite for IronLibrary Application
 * 
 * Test Coverage:
 * ✓ Book Management (Add, Search, Get All)
 * ✓ Author Management (Find by ISBN, Find by Name)
 * ✓ Book Issue/Loan Management
 * ✓ Exception Handling with detailed user feedback
 * ✓ Edge cases and invalid inputs
 * ✓ Boundary conditions
 * 
 * @author QA Team
 * @version 2.0 - Comprehensive Test Coverage
 */
@SpringBootTest
@DisplayName("Iron Library Application - Complete Test Suite")
class IronLibraryAppApplicationTests {

	// LibraryService instance used for all tests
	private LibraryService libraryService;

	/**
	 * Setup method - initializes LibraryService before each test
	 * Ensures clean state for every test execution
	 */
	@BeforeEach
	void setUp() {
		libraryService = new LibraryService();
	}

	// ============================= BOOT CONTEXT TESTS =============================

	/**
	 * Test 1: Verify Spring context loads successfully
	 * Purpose: Basic sanity check that application starts correctly
	 */
	@Test
	@DisplayName("Test 1: Spring Boot Context Loading")
	void contextLoads() {
		// If this test passes, Spring context loaded successfully
		assertNotNull(libraryService, "LibraryService should be initialized");
	}

	// ============================= ADD BOOK WITH AUTHOR TESTS =============================

	/**
	 * Test 2: Successfully add a valid book with author information
	 * Purpose: Verify successful book and author addition with valid inputs
	 */
	@Test
	@DisplayName("Test 2: Add Valid Book with Author - Success Case")
	void testAddBookWithAuthorSuccess() {
		// Arrange: Prepare valid test data
		String isbn = "978-0-13-110362-7";
		String title = "The Clean Code";
		String category = "Programming";
		int quantity = 5;
		String authorName = "Robert C. Martin";
		String authorEmail = "robert@example.com";

		// Act: Add book with author
		assertDoesNotThrow(() -> libraryService.addBookWithAuthor(isbn, title, category, quantity, authorName, authorEmail),
				"Adding valid book with author should not throw exception");

		// Assert: Verify no exception was thrown
	}

	/**
	 * Test 3: Exception when ISBN is null
	 * Purpose: Verify detailed error message for missing ISBN
	 */
	@Test
	@DisplayName("Test 3: Add Book with Null ISBN - Exception")
	void testAddBookWithNullIsbn() {
		// Arrange & Act & Assert: Verify exception with detailed message
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
				libraryService.addBookWithAuthor(null, "Java Basics", "Programming", 3, "Author", "author@example.com"),
				"Should throw exception for null ISBN");

		assertTrue(exception.getMessage().contains("ISBN cannot be null"),
				"Exception message should explain ISBN is required");
		assertTrue(exception.getMessage().contains("ERROR:"),
				"Exception message should start with ERROR prefix");
	}

	/**
	 * Test 4: Exception when ISBN is empty string
	 * Purpose: Verify validation rejects empty ISBN
	 */
	@Test
	@DisplayName("Test 4: Add Book with Empty ISBN - Exception")
	void testAddBookWithEmptyIsbn() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
				libraryService.addBookWithAuthor("", "Java Basics", "Programming", 3, "Author", "author@example.com"));

		assertTrue(exception.getMessage().contains("ISBN cannot be null or empty"),
				"Error message should clarify ISBN is empty");
	}

	/**
	 * Test 5: Exception when title is null
	 * Purpose: Verify detailed error message for missing title
	 */
	@Test
	@DisplayName("Test 5: Add Book with Null Title - Exception")
	void testAddBookWithNullTitle() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
				libraryService.addBookWithAuthor("978-123", null, "Programming", 3, "Author", "author@example.com"));

		assertTrue(exception.getMessage().contains("title cannot be null"),
				"Exception message should mention title");
	}

	/**
	 * Test 6: Exception when category is null
	 * Purpose: Verify category validation
	 */
	@Test
	@DisplayName("Test 6: Add Book with Null Category - Exception")
	void testAddBookWithNullCategory() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
				libraryService.addBookWithAuthor("978-123", "Java Book", null, 3, "Author", "author@example.com"));

		assertTrue(exception.getMessage().contains("Category cannot be null"),
				"Exception should mention category requirement");
	}

	/**
	 * Test 7: Exception when quantity is zero
	 * Purpose: Verify quantity must be positive
	 */
	@Test
	@DisplayName("Test 7: Add Book with Zero Quantity - Exception")
	void testAddBookWithZeroQuantity() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
				libraryService.addBookWithAuthor("978-123", "Java Book", "Programming", 0, "Author", "author@example.com"));

		assertTrue(exception.getMessage().contains("must be greater than 0"),
				"Exception should specify quantity must be positive");
	}

	/**
	 * Test 8: Exception when quantity is negative
	 * Purpose: Verify negative quantities are rejected
	 */
	@Test
	@DisplayName("Test 8: Add Book with Negative Quantity - Exception")
	void testAddBookWithNegativeQuantity() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
				libraryService.addBookWithAuthor("978-123", "Java Book", "Programming", -5, "Author", "author@example.com"));

		assertTrue(exception.getMessage().contains("must be greater than 0"),
				"Negative quantities should be rejected");
	}

	/**
	 * Test 9: Exception when author name is null
	 * Purpose: Verify author name validation
	 */
	@Test
	@DisplayName("Test 9: Add Book with Null Author Name - Exception")
	void testAddBookWithNullAuthorName() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
				libraryService.addBookWithAuthor("978-123", "Java Book", "Programming", 3, null, "author@example.com"));

		assertTrue(exception.getMessage().contains("Author name cannot be null"),
				"Should validate author name");
	}

	/**
	 * Test 10: Exception when email is invalid format
	 * Purpose: Verify email format validation with detailed feedback
	 */
	@Test
	@DisplayName("Test 10: Add Book with Invalid Email Format - Exception")
	void testAddBookWithInvalidEmail() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
				libraryService.addBookWithAuthor("978-123", "Java Book", "Programming", 3, "Author", "invalid-email"));

		assertTrue(exception.getMessage().contains("Invalid email format"),
				"Should reject invalid email format");
		assertTrue(exception.getMessage().contains("example.com"),
				"Should provide email format example");
	}

	/**
	 * Test 11: Exception when email is null
	 * Purpose: Verify null email rejection
	 */
	@Test
	@DisplayName("Test 11: Add Book with Null Email - Exception")
	void testAddBookWithNullEmail() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
				libraryService.addBookWithAuthor("978-123", "Java Book", "Programming", 3, "Author", null));

		assertTrue(exception.getMessage().contains("Invalid email format"),
				"Null email should be caught by email validation");
	}

	// ============================= ISSUE BOOK TESTS =============================

	/**
	 * Test 12: Successfully issue a book to a student
	 * Purpose: Verify book issuance with valid inputs
	 */
	@Test
	@DisplayName("Test 12: Issue Book with Valid Inputs - Success")
	void testIssueBookSuccess() {
		// First add a book to the library
		libraryService.addBookWithAuthor("978-456", "Spring Guide", "Web Development", 3, "Mark", "mark@example.com");

		// Act: Issue the book to a student
		assertDoesNotThrow(() -> libraryService.issueBook("USN001", "John Student", "978-456"),
				"Issuing valid book to student should succeed");
	}

	/**
	 * Test 13: Exception when student USN is null
	 * Purpose: Verify USN validation with clear error message
	 */
	@Test
	@DisplayName("Test 13: Issue Book with Null Student USN - Exception")
	void testIssueBookNullUsn() {
		// Add book first
		libraryService.addBookWithAuthor("978-456", "Spring Guide", "Web Development", 3, "Mark", "mark@example.com");

		// Act & Assert: Verify exception
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
				libraryService.issueBook(null, "John", "978-456"));

		assertTrue(exception.getMessage().contains("Student USN cannot be null"),
				"Error message should specify USN requirement");
	}

	/**
	 * Test 14: Exception when student name is empty
	 * Purpose: Verify student name validation
	 */
	@Test
	@DisplayName("Test 14: Issue Book with Empty Student Name - Exception")
	void testIssueBookEmptyStudentName() {
		libraryService.addBookWithAuthor("978-456", "Spring Guide", "Web Development", 3, "Mark", "mark@example.com");

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
				libraryService.issueBook("USN001", "  ", "978-456"));

		assertTrue(exception.getMessage().contains("Student name cannot be null"),
				"Should reject empty/whitespace-only names");
	}

	/**
	 * Test 15: Exception when ISBN is null during issue
	 * Purpose: Verify ISBN validation for book lookup
	 */
	@Test
	@DisplayName("Test 15: Issue Book with Null ISBN - Exception")
	void testIssueBookNullIsbn() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
				libraryService.issueBook("USN001", "John", null));

		assertTrue(exception.getMessage().contains("ISBN cannot be null"),
				"Should validate ISBN for book lookup");
	}

	/**
	 * Test 16: Exception when book is not found
	 * Purpose: Verify helpful error message when book ISBN doesn't exist
	 */
	@Test
	@DisplayName("Test 16: Issue Non-Existent Book - Exception")
	void testIssueNonExistentBook() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
				libraryService.issueBook("USN001", "John", "999-INVALID"));

		assertTrue(exception.getMessage().contains("not found in the library"),
				"Should indicate book not in library");
		assertTrue(exception.getMessage().contains("verify the ISBN"),
				"Should suggest verification step");
	}

	/**
	 * Test 17: Exception when book quantity is zero (unavailable)
	 * Purpose: Verify helpful message when no copies available
	 */
	@Test
	@DisplayName("Test 17: Issue Unavailable Book (Zero Quantity) - Exception")
	void testIssueUnavailableBook() {
		// Add book with zero quantity
		libraryService.addBookWithAuthor("978-789", "Advanced Java", "Programming", 0, "Josh", "josh@example.com");

		// Act & Assert: Verify exception with helpful message
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
				libraryService.issueBook("USN001", "John", "978-789"));

		assertTrue(exception.getMessage().contains("currently unavailable"),
				"Error message should indicate book unavailable");
		assertTrue(exception.getMessage().contains("All copies are already issued"),
				"Should explain why unavailable");
	}

	// ============================= SEARCH BOOK BY TITLE TESTS =============================

	/**
	 * Test 18: Successfully search book by title
	 * Purpose: Verify book search returns correct results
	 */
	@Test
	@DisplayName("Test 18: Search Book by Valid Title - Success")
	void testSearchBookByTitleSuccess() {
		// Add a book
		libraryService.addBookWithAuthor("978-111", "Database Design", "Databases", 2, "Philip", "philip@example.com");

		// Act: Search for the book
		Book foundBook = libraryService.searchBookByTitle("Database Design");

		// Assert: Verify book was found
		assertNotNull(foundBook, "Book should be found by title");
		assertEquals("Database Design", foundBook.getTitle(), "Title should match");
	}

	/**
	 * Test 19: Search with null title should throw exception
	 * Purpose: Verify null title rejection with clear error
	 */
	@Test
	@DisplayName("Test 19: Search Book with Null Title - Exception")
	void testSearchBookNullTitle() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
				libraryService.searchBookByTitle(null));

		assertTrue(exception.getMessage().contains("cannot be null or empty"),
				"Should reject null search titles");
	}

	/**
	 * Test 20: Search for non-existent book
	 * Purpose: Verify graceful handling when book not found
	 */
	@Test
	@DisplayName("Test 20: Search for Non-Existent Book Title")
	void testSearchNonExistentBookTitle() {
		// Act: Search for non-existent book
		Book foundBook = libraryService.searchBookByTitle("Non Existent Book 12345");

		// Assert: Verify null returned (without exception)
		assertNull(foundBook, "Should return null for non-existent book, not throw exception");
	}

	// ============================= SEARCH BOOKS BY CATEGORY TESTS =============================

	/**
	 * Test 21: Successfully search books by category
	 * Purpose: Verify category search functionality
	 */
	@Test
	@DisplayName("Test 21: Search Books by Valid Category - Success")
	void testSearchBooksByCategorySuccess() {
		// Add books in specific category
		libraryService.addBookWithAuthor("978-222", "OOP Principles", "Programming", 2, "Steven", "steven@example.com");
		libraryService.addBookWithAuthor("978-333", "Design Patterns", "Programming", 1, "Steven", "steven@example.com");

		// Act: Search for books in category
		List<Book> books = libraryService.searchBooksByCategory("Programming");

		// Assert: Verify results
		assertNotNull(books, "Search results should not be null");
		assertFalse(books.isEmpty(), "Should find books in category");
	}

	/**
	 * Test 22: Category search with null input
	 * Purpose: Verify null category rejection
	 */
	@Test
	@DisplayName("Test 22: Search Books with Null Category - Exception")
	void testSearchBooksCategoryNull() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
				libraryService.searchBooksByCategory(null));

		assertTrue(exception.getMessage().contains("Category cannot be null"),
				"Should require valid category");
	}

	/**
	 * Test 23: Search for non-existent category
	 * Purpose: Verify empty list returned for unknown categories
	 */
	@Test
	@DisplayName("Test 23: Search Books by Non-Existent Category")
	void testSearchNonExistentCategory() {
		// Act: Search for non-existent category
		List<Book> books = libraryService.searchBooksByCategory("Science Fiction 999");

		// Assert: Verify empty list returned
		assertNotNull(books, "Should return empty list, not null");
		assertTrue(books.isEmpty(), "Should return empty list for non-existent category");
	}

	// ============================= SEARCH BOOKS BY AUTHOR TESTS =============================

	/**
	 * Test 24: Successfully search books by author
	 * Purpose: Verify author search functionality
	 */
	@Test
	@DisplayName("Test 24: Search Books by Valid Author - Success")
	void testSearchBooksByAuthorSuccess() {
		// Add books by same author
		libraryService.addBookWithAuthor("978-444", "Book One", "Fiction", 1, "Jane Doe", "jane@example.com");
		libraryService.addBookWithAuthor("978-555", "Book Two", "Mystery", 2, "Jane Doe", "jane@example.com");

		// Act: Search for books by author
		List<Book> books = libraryService.searchBooksByAuthor("Jane Doe");

		// Assert: Verify results
		assertNotNull(books, "Search results should not be null");
		assertFalse(books.isEmpty(), "Should find books by author");
	}

	/**
	 * Test 25: Author search with null input
	 * Purpose: Verify null author name rejection
	 */
	@Test
	@DisplayName("Test 25: Search Books with Null Author - Exception")
	void testSearchBooksAuthorNull() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
				libraryService.searchBooksByAuthor(null));

		assertTrue(exception.getMessage().contains("Author name cannot be null"),
				"Should require valid author name");
	}

	/**
	 * Test 26: Search for non-existent author
	 * Purpose: Verify empty list returned for unknown authors
	 */
	@Test
	@DisplayName("Test 26: Search Books by Non-Existent Author")
	void testSearchNonExistentAuthor() {
		// Act: Search for non-existent author
		List<Book> books = libraryService.searchBooksByAuthor("Unknown Author XYZ");

		// Assert: Verify empty list returned
		assertTrue(books.isEmpty(), "Should return empty list for unknown author");
	}

	// ============================= GET ALL BOOKS TESTS =============================

	/**
	 * Test 27: Get all books from library
	 * Purpose: Verify retrieving complete book inventory
	 */
	@Test
	@DisplayName("Test 27: Get All Books - Returns List")
	void testGetAllBooks() {
		// Add multiple books
		libraryService.addBookWithAuthor("978-666", "Science Today", "Science", 3, "Dr. Smith", "smith@example.com");
		libraryService.addBookWithAuthor("978-777", "History Lessons", "History", 2, "Prof. Jones", "jones@example.com");

		// Act: Get all books
		List<Book> allBooks = libraryService.getAllBooks();

		// Assert: Verify list is not null and contains books
		assertNotNull(allBooks, "Book list should not be null");
		assertTrue(allBooks.size() >= 2, "Should contain at least 2 books");
	}

	// ============================= FIND AUTHOR BY ISBN TESTS =============================

	/**
	 * Test 28: Successfully find author by book ISBN
	 * Purpose: Verify author lookup by ISBN works correctly
	 */
	@Test
	@DisplayName("Test 28: Find Author by Valid ISBN - Success")
	void testFindAuthorByIsbnSuccess() {
		// Add book with author
		libraryService.addBookWithAuthor("978-888", "Web Development", "Technology", 1, "Ryan Cooper", "ryan@example.com");

		// Act: Find author by ISBN
		Author foundAuthor = libraryService.findAuthorByIsbn("978-888");

		// Assert: Verify author found
		assertNotNull(foundAuthor, "Author should be found by ISBN");
		assertEquals("Ryan Cooper", foundAuthor.getName(), "Author name should match");
	}

	/**
	 * Test 29: Find author with null ISBN
	 * Purpose: Verify null ISBN rejection
	 */
	@Test
	@DisplayName("Test 29: Find Author with Null ISBN - Exception")
	void testFindAuthorByNullIsbn() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
				libraryService.findAuthorByIsbn(null));

		assertTrue(exception.getMessage().contains("ISBN cannot be null"),
				"Should require valid ISBN");
	}

	/**
	 * Test 30: Find author for non-existent ISBN
	 * Purpose: Verify graceful handling when author not found
	 */
	@Test
	@DisplayName("Test 30: Find Author by Non-Existent ISBN")
	void testFindAuthorNonExistentIsbn() {
		// Act: Search for author by non-existent ISBN
		Author foundAuthor = libraryService.findAuthorByIsbn("999-NOBOOK");

		// Assert: Verify null returned
		assertNull(foundAuthor, "Should return null for non-existent ISBN");
	}

	// ============================= FIND AUTHOR BY NAME TESTS =============================

	/**
	 * Test 31: Successfully find author by name
	 * Purpose: Verify author name search functionality
	 */
	@Test
	@DisplayName("Test 31: Find Author by Valid Name - Success")
	void testFindAuthorByNameSuccess() {
		// Add book to create author record
		libraryService.addBookWithAuthor("978-999", "Machine Learning", "AI", 2, "Dr. Anne Smith", "anne@example.com");

		// Act: Find author by name
		Author foundAuthor = libraryService.findAuthorByName("Dr. Anne Smith");

		// Assert: Verify author found
		assertNotNull(foundAuthor, "Author should be found by name");
		assertEquals("Dr. Anne Smith", foundAuthor.getName(), "Author name should match");
	}

	/**
	 * Test 32: Find author with null name
	 * Purpose: Verify null name rejection
	 */
	@Test
	@DisplayName("Test 32: Find Author with Null Name - Exception")
	void testFindAuthorByNullName() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
				libraryService.findAuthorByName(null));

		assertTrue(exception.getMessage().contains("Author name cannot be null"),
				"Should require valid author name");
	}

	/**
	 * Test 33: Find author with non-existent name
	 * Purpose: Verify graceful handling when name not found
	 */
	@Test
	@DisplayName("Test 33: Find Author by Non-Existent Name")
	void testFindAuthorNonExistentName() {
		// Act: Search for non-existent author
		Author foundAuthor = libraryService.findAuthorByName("Author Not In System");

		// Assert: Verify null returned
		assertNull(foundAuthor, "Should return null for non-existent author");
	}

	// ============================= GET ISSUES BY STUDENT TESTS =============================

	/**
	 * Test 34: Successfully get student's book issues
	 * Purpose: Verify retrieval of student's borrowed books
	 */
	@Test
	@DisplayName("Test 34: Get Issues by Valid Student USN - Success")
	void testGetIssuesByStudentSuccess() {
		// Setup: Add book and issue it to student
		libraryService.addBookWithAuthor("978-1000", "Test Book", "Testing", 5, "Test Author", "test@example.com");
		libraryService.issueBook("STU001", "Alice Brown", "978-1000");

		// Act: Get issues for student
		List<Issue> studentIssues = libraryService.getIssuesByStudent("STU001");

		// Assert: Verify issues found
		assertNotNull(studentIssues, "Issues list should not be null");
	}

	/**
	 * Test 35: Get issues with null student USN
	 * Purpose: Verify null USN rejection
	 */
	@Test
	@DisplayName("Test 35: Get Issues with Null Student USN - Exception")
	void testGetIssuesByNullUsn() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
				libraryService.getIssuesByStudent(null));

		assertTrue(exception.getMessage().contains("Student USN cannot be null"),
				"Should require valid student USN");
	}

	/**
	 * Test 36: Get issues for student with no borrowed books
	 * Purpose: Verify empty list returned for students with no issues
	 */
	@Test
	@DisplayName("Test 36: Get Issues for Student with No Borrowed Books")
	void testGetIssuesNoBooks() {
		// Act: Get issues for student who hasn't borrowed anything
		List<Issue> studentIssues = libraryService.getIssuesByStudent("STU999");

		// Assert: Verify empty list
		assertNotNull(studentIssues, "Should return empty list, not null");
		assertTrue(studentIssues.isEmpty(), "Should return empty list when no issues");
	}

	// ============================= BOUNDARY AND EDGE CASE TESTS =============================

	/**
	 * Test 37: Add book with maximum quantity
	 * Purpose: Verify system handles large quantities appropriately
	 */
	@Test
	@DisplayName("Test 37: Add Book with Large Quantity - Boundary Test")
	void testAddBookLargeQuantity() {
		// Act & Assert: Should handle large quantities
		assertDoesNotThrow(() ->
				libraryService.addBookWithAuthor("978-2000", "Popular Book", "General", 10000, "Popular Author", "pop@example.com"),
				"Should handle large quantities without issue");
	}

	/**
	 * Test 38: Add book with quantity of 1
	 * Purpose: Verify minimum valid quantity accepted
	 */
	@Test
	@DisplayName("Test 38: Add Book with Minimum Quantity (1) - Boundary Test")
	void testAddBookMinimumQuantity() {
		// Act & Assert: Should accept quantity of 1
		assertDoesNotThrow(() ->
				libraryService.addBookWithAuthor("978-2001", "Rare Book", "Collectible", 1, "Rare Author", "rare@example.com"),
				"Should accept quantity of 1");
	}

	/**
	 * Test 39: Search with whitespace-only strings
	 * Purpose: Verify whitespace-only inputs are rejected
	 */
	@Test
	@DisplayName("Test 39: Search with Whitespace-Only Input - Exception")
	void testSearchWhitespaceOnly() {
		// Act & Assert: Whitespace-only should be treated as empty
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
				libraryService.searchBookByTitle("   "));

		assertTrue(exception.getMessage().contains("cannot be null or empty"),
				"Whitespace-only input should be rejected");
	}

	/**
	 * Test 40: Email validation with various formats
	 * Purpose: Verify email validation accepts valid formats
	 */
	@Test
	@DisplayName("Test 40: Email Validation - Valid Format Acceptance")
	void testValidEmailFormats() {
		// Test various valid email formats
		assertDoesNotThrow(() ->
				libraryService.addBookWithAuthor("978-3001", "Email Test 1", "Tech", 1, "Author1", "user@domain.com"));
		
		assertDoesNotThrow(() ->
				libraryService.addBookWithAuthor("978-3002", "Email Test 2", "Tech", 1, "Author2", "user.name@domain.co.uk"));
		
		assertDoesNotThrow(() ->
				libraryService.addBookWithAuthor("978-3003", "Email Test 3", "Tech", 1, "Author3", "user+tag@domain.org"));
	}
}
