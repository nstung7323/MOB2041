package com.example.thvinphngnam.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.thvinphngnam.Database.SQLiteDBHelper;
import com.example.thvinphngnam.Models.LoanSlipModel;

import java.util.ArrayList;
import java.util.List;

public class LoanSlipDAO {
    SQLiteDatabase database;
    SQLiteDBHelper dbHelper;

    public LoanSlipDAO(Context context) {
        dbHelper = new SQLiteDBHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public List<LoanSlipModel> getAllData() {
        List<LoanSlipModel> listLoanSlip = new ArrayList<>();
        Cursor cursorLoanSlip = database.rawQuery("SELECT * FROM " + LoanSlipModel.TB_NAME, null);

        cursorLoanSlip.moveToFirst();
        while (!cursorLoanSlip.isAfterLast()) {
            LoanSlipModel loanSlip = new LoanSlipModel();

//            loanSlip.setIdLoanSlip(cursorLoanSlip.getInt(0));
//            loanSlip.setIdLibrarianFK(cursorLoanSlip.getString(1));
//            loanSlip.setIdMemberFK(cursorLoanSlip.getInt(2));
//            loanSlip.setIdBookFK(cursorLoanSlip.getInt(3));
//            loanSlip.setDateLoanSlip(cursorLoanSlip.getString(4));
//            loanSlip.setStateLoanSlip(cursorLoanSlip.getInt(5));
//            loanSlip.setPriceLoanSlip(cursorLoanSlip.getInt(6));

            loanSlip.setIdLoanSlip(cursorLoanSlip.getInt(0));
            loanSlip.setIdMemberFK(cursorLoanSlip.getInt(1));
            loanSlip.setIdBookFK(cursorLoanSlip.getInt(2));
            loanSlip.setDateLoanSlip(cursorLoanSlip.getString(3));
            loanSlip.setStateLoanSlip(cursorLoanSlip.getInt(4));
            loanSlip.setPriceLoanSlip(cursorLoanSlip.getInt(5));

            listLoanSlip.add(loanSlip);
            cursorLoanSlip.moveToNext();
        }
        cursorLoanSlip.close();

        return listLoanSlip;
    }

    public long insert(LoanSlipModel loanSlip) {
        ContentValues contentValues = new ContentValues();

//        contentValues.put(LoanSlipModel.CL_FK_LIBRARIAN, loanSlip.getIdLibrarianFK());
        contentValues.put(LoanSlipModel.CL_FK_MEMBER, loanSlip.getIdMemberFK());
        contentValues.put(LoanSlipModel.CL_FK_BOOK, loanSlip.getIdBookFK());
        contentValues.put(LoanSlipModel.CL_DATE, String.valueOf(loanSlip.getDateLoanSlip()));
        contentValues.put(LoanSlipModel.CL_STATE, loanSlip.getStateLoanSlip());
        contentValues.put(LoanSlipModel.CL_PRICE, loanSlip.getPriceLoanSlip());

        return database.insert(LoanSlipModel.TB_NAME, null, contentValues);
    }

    public int update(LoanSlipModel loanSlip) {
        ContentValues contentValues = new ContentValues();

//        contentValues.put(LoanSlipModel.CL_FK_LIBRARIAN, loanSlip.getIdLibrarianFK());
        contentValues.put(LoanSlipModel.CL_FK_MEMBER, loanSlip.getIdMemberFK());
        contentValues.put(LoanSlipModel.CL_FK_BOOK, loanSlip.getIdBookFK());
        contentValues.put(LoanSlipModel.CL_DATE, String.valueOf(loanSlip.getDateLoanSlip()));
        contentValues.put(LoanSlipModel.CL_STATE, loanSlip.getStateLoanSlip());
        contentValues.put(LoanSlipModel.CL_PRICE, loanSlip.getPriceLoanSlip());

        return database.update(LoanSlipModel.TB_NAME, contentValues,
                LoanSlipModel.CL_ID + " = " + loanSlip.getIdLoanSlip(), null);
    }

    public int delete(LoanSlipModel loanSlip) {
        return database.delete(LoanSlipModel.TB_NAME,
                LoanSlipModel.CL_ID + " = " + loanSlip.getIdLoanSlip(), null);
    }

//    public void close() {
//        database.close();
//    }
}

