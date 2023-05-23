package com.example.thvinphngnam.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.thvinphngnam.Database.SQLiteDBHelper;
import com.example.thvinphngnam.Models.CategoryBookModel;

import java.util.ArrayList;
import java.util.List;

public class CategoryBookDAO {
    SQLiteDatabase database;
    SQLiteDBHelper dbHelper;

    public CategoryBookDAO(Context context) {
        dbHelper = new SQLiteDBHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public List<CategoryBookModel> getAllData() {
        List<CategoryBookModel> listCategoryBook = new ArrayList<>();
        Cursor cursorCategoryBook = database.rawQuery("SELECT * FROM " + CategoryBookModel.TB_NAME, null);

        cursorCategoryBook.moveToFirst();
        while (!cursorCategoryBook.isAfterLast()) {
            CategoryBookModel categoryBook = new CategoryBookModel();

            categoryBook.setIdCategoryBook(cursorCategoryBook.getInt(0));
            categoryBook.setNameCategoryBook(cursorCategoryBook.getString(1));
            categoryBook.setSupplier(cursorCategoryBook.getString(2));

            listCategoryBook.add(categoryBook);
            cursorCategoryBook.moveToNext();
        }
        cursorCategoryBook.close();

        return listCategoryBook;
    }

    public CategoryBookModel getDataById(int id) {
        Cursor cursor = database.rawQuery("SELECT * FROM " + CategoryBookModel.TB_NAME
                + " WHERE " + CategoryBookModel.CL_ID + " = " + id, null);

        cursor.moveToFirst();

        CategoryBookModel categoryBook = new CategoryBookModel();
        categoryBook.setIdCategoryBook(cursor.getInt(0));
        categoryBook.setNameCategoryBook(cursor.getString(1));
        categoryBook.setSupplier(cursor.getString(2));

        cursor.close();

        return categoryBook;
    }

    public long insert(CategoryBookModel categoryBook) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(CategoryBookModel.CL_NAME, categoryBook.getNameCategoryBook());
        contentValues.put(CategoryBookModel.CL_SUPPLIER, categoryBook.getSupplier());

        return database.insert(CategoryBookModel.TB_NAME, null, contentValues);
    }

    public int update(CategoryBookModel categoryBook) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(CategoryBookModel.CL_NAME, categoryBook.getNameCategoryBook());
        contentValues.put(CategoryBookModel.CL_SUPPLIER, categoryBook.getSupplier());

        return database.update(CategoryBookModel.TB_NAME, contentValues,
                CategoryBookModel.CL_ID + " = " + categoryBook.getIdCategoryBook(), null);
    }

    public int delete(CategoryBookModel categoryBook) {
        return database.delete(CategoryBookModel.TB_NAME,
                CategoryBookModel.CL_ID + " = " + categoryBook.getIdCategoryBook(), null);
    }

//    public void close() {
//        database.close();
//    }
}
