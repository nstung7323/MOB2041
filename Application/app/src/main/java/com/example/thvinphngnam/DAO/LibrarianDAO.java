package com.example.thvinphngnam.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.thvinphngnam.Database.SQLiteDBHelper;
import com.example.thvinphngnam.Models.LibrarianModel;

import java.util.ArrayList;
import java.util.List;

public class LibrarianDAO {
    SQLiteDatabase database;
    SQLiteDBHelper dbHelper;

    public LibrarianDAO(Context context) {
        dbHelper = new SQLiteDBHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public String checkLogin(String userName, String passwords) {
        String name;
        Cursor cursor = database.rawQuery("SELECT * FROM ThuThu " +
                "WHERE MaTT = \"" +  userName + "\" " + "AND MatKhau = \"" + passwords + "\"", null);

        cursor.moveToFirst();
        try {
            name = cursor.getString(1);
        } catch (Exception e){
            name = null;
        }
        cursor.close();

        return name;
    }

    public String getDataById(String id) {
        String ID;
        Cursor cursor = database.rawQuery("SELECT * FROM " + LibrarianModel.TB_NAME
                + " WHERE " + LibrarianModel.CL_ID + " = \"" + id + "\"", null);

        cursor.moveToFirst();
        try {
            ID = cursor.getString(0);
        }
        catch (Exception e) {
            ID = null;
            e.printStackTrace();
        }
        cursor.close();

        return ID;
    }

    public List<LibrarianModel> getAllData() {
        List<LibrarianModel> listLibrarian = new ArrayList<>();
        Cursor cursorLibrarian = database.rawQuery("SELECT * FROM " + LibrarianModel.TB_NAME, null);

        cursorLibrarian.moveToFirst();
        while (!cursorLibrarian.isAfterLast()) {
            LibrarianModel librarian = new LibrarianModel();

            librarian.setIdLibrarian(cursorLibrarian.getString(0));
            librarian.setNameLibrarian(cursorLibrarian.getString(1));
            librarian.setPasswordsLibrarian(cursorLibrarian.getString(2));

            listLibrarian.add(librarian);
            cursorLibrarian.moveToNext();
        }
        cursorLibrarian.close();

        return listLibrarian;
    }

    public long insert(LibrarianModel librarian) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(LibrarianModel.CL_ID, librarian.getIdLibrarian());
        contentValues.put(LibrarianModel.CL_NAME, librarian.getNameLibrarian());
        contentValues.put(LibrarianModel.CL_PASSWORD, librarian.getPasswordsLibrarian());

        return database.insert(LibrarianModel.TB_NAME, null, contentValues);
    }

    public int update(LibrarianModel librarian) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(LibrarianModel.CL_NAME, librarian.getNameLibrarian());
        contentValues.put(LibrarianModel.CL_PASSWORD, librarian.getPasswordsLibrarian());

        return database.update(LibrarianModel.TB_NAME, contentValues,
                LibrarianModel.CL_ID + " = \"" + librarian.getIdLibrarian() + "\"", null);
    }

//    public int delete(LibrarianModel librarian) {
//        return database.delete(LibrarianModel.TB_NAME,
//                LibrarianModel.CL_ID + " = \"" + librarian.getIdLibrarian() + "\"", null);
//    }
//
//    public void close() {
//        database.close();
//    }
}
