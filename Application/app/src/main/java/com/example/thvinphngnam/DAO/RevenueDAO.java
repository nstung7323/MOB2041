package com.example.thvinphngnam.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.thvinphngnam.Database.SQLiteDBHelper;
import com.example.thvinphngnam.Models.TopModel;

import java.util.ArrayList;
import java.util.List;

public class RevenueDAO {
    Context context;
    SQLiteDatabase database;
    SQLiteDBHelper dbHelper;

    public RevenueDAO(Context context) {
        this.context = context;
        dbHelper = new SQLiteDBHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public List<TopModel> getDataTop() {
        List<TopModel> listTop = new ArrayList<>();
        BookDAO bookDAO = new BookDAO(context);
        bookDAO.open();

        Cursor cursorTop = database.rawQuery("SELECT MaSach, count(MaPM) AS SoLuong " +
                "FROM PhieuMuon GROUP BY MaSach ORDER BY SoLuong DESC LIMIT 10", null);

        cursorTop.moveToFirst();
        while (!cursorTop.isAfterLast()) {
            TopModel top = new TopModel();

            top.setName(bookDAO.getDataById(cursorTop.getInt(0)).getNameBook());
            top.setAmount(cursorTop.getInt(1));

            listTop.add(top);
            cursorTop.moveToNext();
        }

        cursorTop.close();

        return listTop;
    }

    public int getDataRevenue(String startDate, String endDate) {
        int sum;
        Cursor cursor = database.rawQuery("SELECT SUM(TienThue) FROM PhieuMuon " +
                "WHERE Ngay BETWEEN \"" + startDate + "\" AND \"" + endDate + "\"", null);

        cursor.moveToFirst();
        try {
            sum = cursor.getInt(0);
        } catch (Exception e) {
            sum = 0;
        }
        cursor.close();

        return sum;
    }

//    public void close() {
//        database.close();
//    }
}
