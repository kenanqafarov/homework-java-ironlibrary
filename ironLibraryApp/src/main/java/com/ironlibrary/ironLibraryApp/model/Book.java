package com.ironlibrary.ironLibraryApp.model;

public class Book {

    private String isbn;
    private String title;
    private String category;
    private int quantity;
    private Author author;

    public Book(String isbn, String title, String category, int quantity, Author author) {
        if (isbn == null || isbn.trim().isEmpty())
            throw new IllegalArgumentException("ERROR: ISBN cannot be null or empty.");
        if (title == null || title.trim().isEmpty())
            throw new IllegalArgumentException("ERROR: Book title cannot be null or empty.");
        if (category == null || category.trim().isEmpty())
            throw new IllegalArgumentException("ERROR: Book category cannot be null or empty.");
        if (quantity < 0)
            throw new IllegalArgumentException("ERROR: Quantity cannot be negative. Provided: " + quantity);

        this.isbn = isbn;
        this.title = title;
        this.category = category;
        this.quantity = quantity;
        this.author = author;
    }

    // Constructor without Author (used by DAOs before author is linked)
    public Book(String isbn, String title, String category, int quantity) {
        this(isbn, title, category, quantity, null);
    }

    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getCategory() { return category; }
    public int getQuantity() { return quantity; }
    public Author getAuthor() { return author; }

    public void setIsbn(String isbn) {
        if (isbn == null || isbn.trim().isEmpty())
            throw new IllegalArgumentException("ERROR: ISBN cannot be null or empty.");
        this.isbn = isbn;
    }

    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty())
            throw new IllegalArgumentException("ERROR: Book title cannot be null or empty.");
        this.title = title;
    }

    public void setCategory(String category) {
        if (category == null || category.trim().isEmpty())
            throw new IllegalArgumentException("ERROR: Book category cannot be null or empty.");
        this.category = category;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0)
            throw new IllegalArgumentException("ERROR: Quantity cannot be negative. Provided: " + quantity);
        this.quantity = quantity;
    }

    public void setAuthor(Author author) { this.author = author; }

    public void addQuantity(int count) {
        if (count < 0)
            throw new IllegalArgumentException("ERROR: Cannot add negative quantity. Provided: " + count);
        this.quantity += count;
    }

    public void reduceQuantity(int count) {
        if (count < 0)
            throw new IllegalArgumentException("ERROR: Cannot reduce by negative quantity. Provided: " + count);
        if (count > this.quantity)
            throw new IllegalArgumentException("ERROR: Insufficient copies. Available: " + this.quantity + ", Requested: " + count);
        this.quantity -= count;
    }

    @Override
    public String toString() {
        return "Book{isbn='" + isbn + "', title='" + title + "', category='" + category +
                "', quantity=" + quantity + ", author=" + (author != null ? author.getName() : "Unknown") + '}';
    }
}