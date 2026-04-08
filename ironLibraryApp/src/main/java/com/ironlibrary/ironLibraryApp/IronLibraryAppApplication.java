package com.ironlibrary.ironLibraryApp;

import com.ironlibrary.ironLibraryApp.model.Author;
import com.ironlibrary.ironLibraryApp.model.Book;
import com.ironlibrary.ironLibraryApp.model.Issue;
import com.ironlibrary.ironLibraryApp.service.LibraryService;

import java.util.List;
import java.util.Scanner;

public class IronLibraryAppApplication {
	public static void main(String[] args) {
		LibraryService service = new LibraryService();
		Scanner scanner = new Scanner(System.in);

		while (true) {
			printMenu();
			try {
				int choice = Integer.parseInt(scanner.nextLine().trim());

				switch (choice) {
					case 1:
						addBook(service, scanner);
						break;
					case 2:
						searchBookByTitle(service, scanner);
						break;
					case 3:
						searchBooksByCategory(service, scanner);
						break;
					case 4:
						searchBooksByAuthor(service, scanner);
						break;
					case 5:
						getAllBooks(service, scanner);
						break;
					case 6:
						issueBook(service, scanner);
						break;
					case 7:
						listBooksByUsn(service, scanner);
						break;
					case 8:
						System.out.println("Exiting the system. Goodbye!");
						scanner.close();
						return;
					default:
						System.out.println("Invalid choice. Please try again.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Please enter a valid number.");
			} catch (Exception e) {
				System.out.println("An unexpected error occurred: " + e.getMessage());
			}
		}
	}

	private static void printMenu() {
		System.out.println("\n=== Library Management System ===");
		System.out.println("1. Add a book");
		System.out.println("2. Search book by title");
		System.out.println("3. Search book by category");
		System.out.println("4. Search book by Author");
		System.out.println("5. List all books along with author");
		System.out.println("6. Issue book to student");
		System.out.println("7. List books by usn");
		System.out.println("8. Exit");
		System.out.print("Enter your choice: ");
	}

	private static void addBook(LibraryService service, Scanner sc) {
		System.out.print("Enter isbn: ");
		String isbn = sc.nextLine();
		System.out.print("Enter title: ");
		String title = sc.nextLine();
		System.out.print("Enter category: ");
		String category = sc.nextLine();
		System.out.print("Enter Author name: ");
		String authorName = sc.nextLine();
		System.out.print("Enter Author mail: ");
		String email = sc.nextLine();
		System.out.print("Enter number of books: ");
		int qty = Integer.parseInt(sc.nextLine());

		service.addBookWithAuthor(isbn, title, category, qty, authorName, email);
	}

	private static void issueBook(LibraryService service, Scanner sc) {
		System.out.print("Enter usn: ");
		String usn = sc.nextLine();
		System.out.print("Enter student name: ");
		String studentName = sc.nextLine();
		System.out.print("Enter isbn: ");
		String isbn = sc.nextLine();
		service.issueBook(usn, studentName, isbn);
	}

	private static void searchBookByTitle(LibraryService service, Scanner sc) {
		System.out.print("Enter title of the book: ");
		String title = sc.nextLine();
		Book book = service.searchBookByTitle(title);
		if (book != null) {
			System.out.println("------------------------------------------------------------");
			System.out.printf("%-15s %-30s %-15s %-5s%n", "ISBN", "Title", "Category", "Qty");
			System.out.println("------------------------------------------------------------");
			System.out.printf("%-15s %-30s %-15s %-5d%n",
					book.getIsbn(), book.getTitle(), book.getCategory(), book.getQuantity());
			System.out.println("------------------------------------------------------------");
		}
	}

	private static void getAllBooks(LibraryService service, Scanner sc) {
		System.out.println("Here is the list!");
		List<Book> books = service.getAllBooks();

		if (books.isEmpty()) {
			System.out.println("No books found.");
			return;
		}

		System.out.println("------------------------------------------------------------");
		System.out.printf("%-15s %-30s %-15s %-5s%n", "ISBN", "Title", "Category", "Qty");
		System.out.println("------------------------------------------------------------");
		for (Book book : books) {
			System.out.printf("%-15s %-30s %-15s %-5d%n",
					book.getIsbn(), book.getTitle(), book.getCategory(), book.getQuantity());
		}
		System.out.println("------------------------------------------------------------");
	}

	private static void searchBooksByCategory(LibraryService service, Scanner sc) {
		System.out.print("Enter the category to search: ");
		String category = sc.nextLine();
		List<Book> books = service.searchBooksByCategory(category);
		printBookList(books);
	}

	private static void searchBooksByAuthor(LibraryService service, Scanner sc) {
		System.out.print("Enter the author name to search: ");
		String author = sc.nextLine();
		List<Book> books = service.searchBooksByAuthor(author);
		printBookList(books);
	}

	private static void listBooksByUsn(LibraryService service, Scanner sc) {
		System.out.print("Enter student USN: ");
		String usn = sc.nextLine();
		List<Issue> issues = service.getIssuesByStudent(usn);

		if (issues.isEmpty()) {
			return;
		}

		System.out.println("------------------------------------------------------------");
		System.out.printf("%-5s %-15s %-30s %-12s %-12s%n",
				"ID", "USN", "Book Title", "Issue Date", "Return Date");
		System.out.println("------------------------------------------------------------");
		for (Issue issue : issues) {
			System.out.printf("%-5d %-15s %-30s %-12s %-12s%n",
					issue.getIssueId(),
					issue.getIssueStudent().getUsn(),
					issue.getIssueBook().getTitle(),
					issue.getIssueDate(),
					issue.getReturnDate());
		}
		System.out.println("------------------------------------------------------------");
	}

	private static void printBookList(List<Book> books) {
		if (books == null || books.isEmpty()) {
			System.out.println("No books found.");
			return;
		}
		System.out.println("------------------------------------------------------------");
		System.out.printf("%-15s %-30s %-15s %-5s%n", "ISBN", "Title", "Category", "Qty");
		System.out.println("------------------------------------------------------------");
		for (Book book : books) {
			System.out.printf("%-15s %-30s %-15s %-5d%n",
					book.getIsbn(), book.getTitle(), book.getCategory(), book.getQuantity());
		}
		System.out.println("------------------------------------------------------------");
	}
}