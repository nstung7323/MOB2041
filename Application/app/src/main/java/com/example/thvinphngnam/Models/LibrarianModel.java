package com.example.thvinphngnam.Models;

public class LibrarianModel {
    private String idLibrarian;
    private String nameLibrarian;
    private String passwordsLibrarian;

    public static final String TB_NAME = "ThuThu";
    public static final String CL_ID = "MaTT";
    public static final String CL_NAME = "HoTen";
    public static final String CL_PASSWORD = "MatKhau";

    public String getIdLibrarian() {
        return idLibrarian;
    }

    public void setIdLibrarian(String idLibrarian) {
        this.idLibrarian = idLibrarian;
    }

    public String getNameLibrarian() {
        return nameLibrarian;
    }

    public void setNameLibrarian(String nameLibrarian) {
        this.nameLibrarian = nameLibrarian;
    }

    public String getPasswordsLibrarian() {
        return passwordsLibrarian;
    }

    public void setPasswordsLibrarian(String passwordsLibrarian) {
        this.passwordsLibrarian = passwordsLibrarian;
    }
}
