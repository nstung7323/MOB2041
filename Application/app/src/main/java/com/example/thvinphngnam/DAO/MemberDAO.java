package com.example.thvinphngnam.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.thvinphngnam.Database.SQLiteDBHelper;
import com.example.thvinphngnam.Models.MemberModel;

import java.util.ArrayList;
import java.util.List;

public class MemberDAO {
    SQLiteDatabase database;
    SQLiteDBHelper dbHelper;

    public MemberDAO(Context context) {
        dbHelper = new SQLiteDBHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public List<MemberModel> getAllData() {
        List<MemberModel> listMember = new ArrayList<>();
        Cursor cursorMember = database.rawQuery("SELECT * FROM " + MemberModel.TB_NAME, null);

        cursorMember.moveToFirst();
        while (!cursorMember.isAfterLast()) {
            MemberModel member = new MemberModel();

            member.setIdMember(cursorMember.getInt(0));
            member.setNameMember(cursorMember.getString(1));
            member.setDateMember(cursorMember.getString(2));

            listMember.add(member);
            cursorMember.moveToNext();
        }
        cursorMember.close();

        return listMember;
    }

    public MemberModel getDataById(int id) {
        Cursor cursor = database.rawQuery("SELECT * FROM " + MemberModel.TB_NAME
                + " WHERE " + MemberModel.CL_ID + " = " + id, null);

        cursor.moveToFirst();

        MemberModel member = new MemberModel();
        member.setIdMember(cursor.getInt(0));
        member.setNameMember(cursor.getString(1));
        member.setDateMember(cursor.getString(2));

        cursor.close();

        return member;
    }

    public long insert(MemberModel member) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MemberModel.CL_NAME, member.getNameMember());
        contentValues.put(MemberModel.CL_DATE, member.getDateMember());

        return database.insert(MemberModel.TB_NAME, null, contentValues);
    }

    public int update(MemberModel member) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MemberModel.CL_NAME, member.getNameMember());
        contentValues.put(MemberModel.CL_DATE, member.getDateMember());

        return database.update(MemberModel.TB_NAME, contentValues,
                MemberModel.CL_ID + " = " + member.getIdMember(), null);
    }

    public int delete(MemberModel member) {
        return database.delete(MemberModel.TB_NAME,
                MemberModel.CL_ID + " = " + member.getIdMember(), null);
    }

//    public void close() {
//        database.close();
//    }
}
