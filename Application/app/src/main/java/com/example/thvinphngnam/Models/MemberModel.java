package com.example.thvinphngnam.Models;

public class MemberModel {
    private int idMember;
    private String nameMember;
    private String dateMember;

    public static final String TB_NAME = "ThanhVien";
    public static final String CL_ID = "MaTV";
    public static final String CL_NAME = "HoTen";
    public static final String CL_DATE = "NamSinh";

    public int getIdMember() {
        return idMember;
    }

    public void setIdMember(int idMember) {
        this.idMember = idMember;
    }

    public String getNameMember() {
        return nameMember;
    }

    public void setNameMember(String nameMember) {
        this.nameMember = nameMember;
    }

    public String getDateMember() {
        return dateMember;
    }

    public void setDateMember(String dateMember) {
        this.dateMember = dateMember;
    }
}
