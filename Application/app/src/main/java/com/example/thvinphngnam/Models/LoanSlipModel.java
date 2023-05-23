package com.example.thvinphngnam.Models;

public class LoanSlipModel {
    private int idLoanSlip;
//    private String idLibrarianFK;
    private int idMemberFK;
    private int idBookFK;
    private String dateLoanSlip;
    private int stateLoanSlip;
    private int priceLoanSlip;

    public static final String TB_NAME = "PhieuMuon";
    public static final String CL_ID = "MaPM";
//    public static final String CL_FK_LIBRARIAN = "MaTT";
    public static final String CL_FK_MEMBER = "MaTV";
    public static final String CL_FK_BOOK = "MaSach";
    public static final String CL_DATE = "Ngay";
    public static final String CL_STATE = "TrangThai";
    public static final String CL_PRICE = "TienThue";

    public int getIdLoanSlip() {
        return idLoanSlip;
    }

    public void setIdLoanSlip(int idLoanSlip) {
        this.idLoanSlip = idLoanSlip;
    }

//    public String getIdLibrarianFK() {
//        return idLibrarianFK;
//    }
//
//    public void setIdLibrarianFK(String idLibrarianFK) {
//        this.idLibrarianFK = idLibrarianFK;
//    }

    public int getIdMemberFK() {
        return idMemberFK;
    }

    public void setIdMemberFK(int idMemberFK) {
        this.idMemberFK = idMemberFK;
    }

    public int getIdBookFK() {
        return idBookFK;
    }

    public void setIdBookFK(int idBookFK) {
        this.idBookFK = idBookFK;
    }

    public String getDateLoanSlip() {
        return dateLoanSlip;
    }

    public void setDateLoanSlip(String dateLoanSlip) {
        this.dateLoanSlip = dateLoanSlip;
    }

    public int getStateLoanSlip() {
        return stateLoanSlip;
    }

    public void setStateLoanSlip(int stateLoanSlip) {
        this.stateLoanSlip = stateLoanSlip;
    }

    public int getPriceLoanSlip() {
        return priceLoanSlip;
    }

    public void setPriceLoanSlip(int priceLoanSlip) {
        this.priceLoanSlip = priceLoanSlip;
    }
}
