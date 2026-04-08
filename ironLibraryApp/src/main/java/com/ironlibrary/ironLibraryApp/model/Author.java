package com.ironlibrary.ironLibraryApp.model;

public class Author{
    private int authorId;
    private String name;
    private String email;
    private Book authorBook;

    public Author (String name,String email,Book authorBook){
        this.name = name;
        this.email = email;
        this.authorBook = authorBook;
    }

//    Getters
    public int getAuthorId(){
        return authorId;
    }
    public String getName(){
        return name;
    }
    public String getEmail(){
        return email;
    }
    public Book getAuthorBook(){
        return authorBook;
    }
//    Setters
    public void setAuthorId(int authorId){
        this.authorId = authorId;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setAuthorBook(Book authorBook){
        this.authorBook = authorBook;
    }


}


