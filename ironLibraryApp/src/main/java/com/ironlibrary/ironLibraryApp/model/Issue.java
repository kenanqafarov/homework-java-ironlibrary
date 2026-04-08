package com.ironlibrary.ironLibraryApp.model;

public class Issue{
    private int issueId;
    private String issueDate;
    private String returnDate;
    private Student issueStudent;
    private Book issueBook;

    public Issue(int issueId,String issueDate, String returnDate,Student issueStudent,Book issueBook ){
        this.issueId = issueId;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
        this.issueStudent = issueStudent;
        this.issueBook = issueBook;
    }

    public Issue(String issueDate, String returnDate, Student issueStudent, Book issueBook) {
        this.issueDate = issueDate;
        this.returnDate = returnDate;
        this.issueStudent = issueStudent;
        this.issueBook = issueBook;
    }

    //    Getters
    public int getIssueId(){
        return issueId;
    }
    public String getIssueDate(){
        return issueDate;
    }
    public String getReturnDate(){
        return returnDate;
    }
    public Student getIssueStudent(){
        return issueStudent;
    }
    public Book getIssueBook(){
        return issueBook;
    }
//    Setters
    public void setIssueId(int issueId){
        this.issueId = issueId;
    }
    public void setIssueDate(String issueDate){
        this.issueDate = issueDate;
    }
    public void setReturnDate(String returnDate){
        this.returnDate = returnDate;
    }
    public void setIssueStudent(Student issueStudent){
        this.issueStudent = issueStudent;
    }
    public void setIssueBook(Book issueBook){
        this.issueBook = issueBook;
    }


}