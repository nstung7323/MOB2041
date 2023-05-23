package com.example.thvinphngnam.Models;

public class BookModel {
    private int idBook;
    private String nameBook;
    private int priceBook;
    private int categoryBook;
    private String author;

    public static final String TB_NAME = "Sach";
    public static final String CL_ID = "MaSach";
    public static final String CL_NAME = "TenSach";
    public static final String CL_PRICE = "GiaThue";
    public static final String CL_FK = "MaLoai";
    public static final String CL_AUTHOR = "TacGia";

    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    public String getNameBook() {
        return nameBook;
    }

    public void setNameBook(String nameBook) {
        this.nameBook = nameBook;
    }

    public int getPriceBook() {
        return priceBook;
    }

    public void setPriceBook(int priceBook) {
        this.priceBook = priceBook;
    }

    public int getCategoryBook() {
        return categoryBook;
    }

    public void setCategoryBook(int categoryBook) {
        this.categoryBook = categoryBook;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
