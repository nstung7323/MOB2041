package com.example.thvinphngnam.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDBHelper extends SQLiteOpenHelper {
    private static final String TB_NAME = "pblib.db";
    private static final int VERSION = 1;

    public SQLiteDBHelper(Context context) {
        super(context, TB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableCategoryBook = "" +
                "CREATE TABLE LoaiSach(" +
                "MaLoai INTEGER PRIMARY KEY AUTOINCREMENT," +
                "TenLoai TEXT NOT NULL," +
                "NhaCungCapTungNSPH25350 TEXT NOT NULL" +
                ")";
        db.execSQL(createTableCategoryBook);

        String createTableBook = "" +
                "CREATE TABLE Sach(" +
                "MaSach INTEGER PRIMARY KEY AUTOINCREMENT," +
                "TenSach TEXT NOT NULL," +
                "GiaThue INTEGER NOT NULL," +
                "MaLoai INTEGER NOT NULL REFERENCES LoaiSach(MaLoai)," +
                "TacGia TEXT NOT NULL" +
                ")";
        db.execSQL(createTableBook);

//        String createTableLibrarian = "" +
//                "CREATE TABLE ThuThu(" +
//                "MaTT TEXT PRIMARY KEY," +
//                "HoTen TEXT NOT NULL, " +
//                "MatKhau TEXT NOT NULL" +
//                ")";
//        db.execSQL(createTableLibrarian);

        String createTableMember = "" +
                "CREATE TABLE ThanhVien(" +
                "MaTV INTEGER PRIMARY KEY AUTOINCREMENT," +
                "HoTen TEXT NOT NULL," +
                "NamSinh TEXT NOT NULL" +
                ")";
        db.execSQL(createTableMember);

        String createTableLoanSlip = "" +
                "CREATE TABLE PhieuMuon(" +
                "MaPM INTEGER PRIMARY KEY AUTOINCREMENT," +
//                "MaTT TEXT NOT NULL REFERENCES ThuThu(MaTT)," +
                "MaTV TEXT NOT NULL REFERENCES ThanhVien(MaTV)," +
                "MaSach INTEGER NOT NULL REFERENCES LoaiSach(MaSach)," +
                "Ngay TEXT NOT NULL," +
                "TrangThai INTEGER NOT NULL," +
                "TienThue INTEGER NOT NULL" +
                ")";
        db.execSQL(createTableLoanSlip);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.insert("ThuThu", null, values);
//        db.update("ThuThu", values, "MaTT = ?", new String[]{});
//        db.delete("ThuThu", "MaTT = ?", new String[]{});
//        db.execSQL("SELECT * FROM ThuThu");
    }
}
