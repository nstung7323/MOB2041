package com.example.thvinphngnam.Fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.thvinphngnam.DAO.RevenueDAO;
import com.example.thvinphngnam.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RevenueFragment extends Fragment {
    TextInputLayout tilRevenueStartDate, tilRevenueEndDate;
    TextInputEditText tieRevenueStartDate, tieRevenueEndDate;
    Button btnRevenue, btnRevenueCancel;
    TextView tvRevenueSum;

    DatePickerDialog datePickerDialogStart, datePickerDialogEnd;
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    Calendar calendar = Calendar.getInstance();

    RevenueDAO dao;

    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewRevenue = inflater.inflate(R.layout.fragment_revenue, container, false);

        dao = new RevenueDAO(getContext());
        dao.open();

        tilRevenueStartDate = (TextInputLayout) viewRevenue.findViewById(R.id.til_revenue_startDate);
        tieRevenueStartDate = (TextInputEditText) viewRevenue.findViewById(R.id.tie_revenue_startDate);
        tilRevenueEndDate = (TextInputLayout) viewRevenue.findViewById(R.id.til_revenue_endDate);
        tieRevenueEndDate = (TextInputEditText) viewRevenue.findViewById(R.id.tie_revenue_endDate);
        btnRevenue = (Button) viewRevenue.findViewById(R.id.btn_revenue);
        btnRevenueCancel = (Button) viewRevenue.findViewById(R.id.btn_revenue_cancel);
        tvRevenueSum = (TextView) viewRevenue.findViewById(R.id.tv_revenue_sum);

        datePickerDialogStart = new DatePickerDialog(getContext(), (view, year, month, dayOfMonth) -> {
            String m = String.valueOf(month + 1).length() == 2 ? String.valueOf(month + 1) : ("0" + (month + 1));
            String d = String.valueOf(dayOfMonth).length() == 2 ? String.valueOf(dayOfMonth) : ("0" + (dayOfMonth));
            tieRevenueStartDate.setText(year + "-" + (m) + "-" + d);
        }
                , calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialogEnd = new DatePickerDialog(getContext(), (view, year, month, dayOfMonth) -> {
            String m = String.valueOf(month + 1).length() == 2 ? String.valueOf(month + 1) : ("0" + (month + 1));
            String d = String.valueOf(dayOfMonth).length() == 2 ? String.valueOf(dayOfMonth) : ("0" + (dayOfMonth));
            tieRevenueEndDate.setText(year + "-" + (m) + "-" + d);
        }
                , calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH));

        tieRevenueStartDate.setOnTouchListener((v, event) -> {
            datePickerDialogStart.show();
            return false;
        });
        tieRevenueEndDate.setOnTouchListener((v, event) -> {
            datePickerDialogEnd.show();
            return false;
        });

        btnRevenue.setOnClickListener(v -> validate());
        btnRevenueCancel.setOnClickListener(v -> cancel());

        return viewRevenue;
    }

    @SuppressLint("SetTextI18n")
    private void validate() {
        if (tieRevenueStartDate.getText().length() == 0) {
            tilRevenueStartDate.setError("Vui lòng nhập Ngày bắt đầu");
            tieRevenueStartDate.requestFocus();
            datePickerDialogStart.show();
            return;
        }
        if (tieRevenueEndDate.getText().length() == 0) {
            tilRevenueStartDate.setError("");
            tilRevenueEndDate.setError("Vui lòng nhập Ngày kết thúc");
            tieRevenueEndDate.requestFocus();
            datePickerDialogEnd.show();
            return;
        }
        if (tieRevenueStartDate.getText().length() == 10) {
            tilRevenueEndDate.setError("");
            try {
                format.parse(tieRevenueStartDate.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
                tilRevenueStartDate.setError("Vui lòng nhập đúng định dạng");
                return;
            }

        } else {
            tilRevenueEndDate.setError("");
            tilRevenueStartDate.setError("Vui lòng nhập đúng định dạng");
            return;
        }
        if (tieRevenueEndDate.getText().length() == 10) {
            tilRevenueStartDate.setError("");
            try {
                format.parse(tieRevenueEndDate.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
                tilRevenueEndDate.setError("Vui lòng nhập đúng định dạng");
                return;
            }

        } else {
            tilRevenueStartDate.setError("");
            tilRevenueEndDate.setError("Vui lòng nhập đúng định dạng");
            return;
        }
        tilRevenueStartDate.setError("");
        tilRevenueEndDate.setError("");

        tvRevenueSum.setText(dao.getDataRevenue(tieRevenueStartDate.getText().toString(),
                tieRevenueEndDate.getText().toString()) + "đ");
    }

    @SuppressLint("SetTextI18n")
    private void cancel() {
        tieRevenueStartDate.setText("");
        tieRevenueEndDate.setText("");

        tilRevenueStartDate.setError("");
        tilRevenueEndDate.setError("");

        tvRevenueSum.setText("xxx.xxx.xxxđ");
    }

}