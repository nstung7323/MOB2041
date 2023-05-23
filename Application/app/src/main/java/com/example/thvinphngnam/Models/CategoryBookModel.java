package com.example.thvinphngnam.Models;

public class CategoryBookModel {
    private int idCategoryBook;
    private String nameCategoryBook;
    private String supplier;

    public static final String TB_NAME = "LoaiSach";
    public static final String CL_ID = "MaLoai";
    public static final String CL_NAME = "TenLoai";
    public static final String CL_SUPPLIER = "NhaCungCapTungNSPH25350";

    public int getIdCategoryBook() {
        return idCategoryBook;
    }

    public void setIdCategoryBook(int idCategoryBook) {
        this.idCategoryBook = idCategoryBook;
    }

    public String getNameCategoryBook() {
        return nameCategoryBook;
    }

    public void setNameCategoryBook(String nameCategoryBook) {
        this.nameCategoryBook = nameCategoryBook;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }
}
