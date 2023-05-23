package com.example.thvinphngnam.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.thvinphngnam.Database.SQLiteDBHelper;
import com.example.thvinphngnam.Models.BookModel;

import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    SQLiteDatabase database;
    SQLiteDBHelper dbHelper;

    public BookDAO(Context context) {
        dbHelper = new SQLiteDBHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public List<BookModel> getAllData() {
        List<BookModel> listBook = new ArrayList<>();
        Cursor cursorBook = database.rawQuery("SELECT * FROM " + BookModel.TB_NAME, null);

        cursorBook.moveToFirst();
        while (!cursorBook.isAfterLast()) {
            BookModel book = new BookModel();

            book.setIdBook(cursorBook.getInt(0));
            book.setNameBook(cursorBook.getString(1));
            book.setPriceBook(cursorBook.getInt(2));
            book.setCategoryBook(cursorBook.getInt(3));

            //
            book.setAuthor(cursorBook.getString(4));

            listBook.add(book);
            cursorBook.moveToNext();
        }
        cursorBook.close();

        return listBook;
    }

    public BookModel getDataById(int id) {
        Cursor cursor = database.rawQuery("SELECT * FROM " + BookModel.TB_NAME
                + " WHERE " + BookModel.CL_ID + " = " + id, null);

        cursor.moveToFirst();

        BookModel book = new BookModel();
        book.setIdBook(cursor.getInt(0));
        book.setNameBook(cursor.getString(1));
        book.setPriceBook(cursor.getInt(2));
        book.setCategoryBook(cursor.getInt(3));

        //
        book.setAuthor(cursor.getString(4));

        cursor.close();

        return book;
    }

    public long insert(BookModel book) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(BookModel.CL_NAME, book.getNameBook());
        contentValues.put(BookModel.CL_PRICE, book.getPriceBook());
        contentValues.put(BookModel.CL_FK, book.getCategoryBook());

        //
        contentValues.put(BookModel.CL_AUTHOR, book.getAuthor());

        return database.insert(BookModel.TB_NAME, null, contentValues);
    }

    public int update(BookModel book) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(BookModel.CL_NAME, book.getNameBook());
        contentValues.put(BookModel.CL_PRICE, book.getPriceBook());
        contentValues.put(BookModel.CL_FK, book.getCategoryBook());

        //
        contentValues.put(BookModel.CL_AUTHOR, book.getAuthor());

        return database.update(BookModel.TB_NAME, contentValues,
                BookModel.CL_ID + " = " + book.getIdBook(), null);
    }

    public int delete(BookModel book) {
        return database.delete(BookModel.TB_NAME,
                BookModel.CL_ID + " = " + book.getIdBook(), null);
    }

//    public void close() {
//        database.close();
//    }

}
