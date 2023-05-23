package com.example.thvinphngnam.Fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thvinphngnam.Adapters.MemberAdapter;
import com.example.thvinphngnam.DAO.MemberDAO;
import com.example.thvinphngnam.Models.MemberModel;
import com.example.thvinphngnam.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MemberFragment extends Fragment {
    RecyclerView rcvMember;
    FloatingActionButton fabMember;

    private TextInputLayout tilDialogAddMemberName, tilDialogAddMemberDate;
    private TextInputEditText tieDialogAddMemberName, tieDialogAddMemberDate;
    Button btnDialogAddMember, btnDialogAddMemberCancel;

    MemberDAO dao;
    MemberAdapter adapter;
    List<MemberModel> list = new ArrayList<>();

    Calendar calendar = Calendar.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewMember = inflater.inflate(R.layout.fragment_member, container, false);

        rcvMember = (RecyclerView) viewMember.findViewById(R.id.rcv_member);
        fabMember = (FloatingActionButton) viewMember.findViewById(R.id.fab_member);

        dao = new MemberDAO(getContext());
        dao.open();
        list = dao.getAllData();

        adapter = new MemberAdapter(list);
        rcvMember.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvMember.setAdapter(adapter);

        fabMember.setOnClickListener(v -> showDialogAddMember());

        return viewMember;
    }

    private void showDialogAddMember() {
        Dialog dialog = new Dialog(getContext(), com.airbnb.lottie.R.style.Theme_AppCompat_Light_Dialog_Alert);

        dialog.setContentView(R.layout.dialog_add_member);

        tilDialogAddMemberName = (TextInputLayout) dialog.findViewById(R.id.til_dialog_add_member_name);
        tieDialogAddMemberName = (TextInputEditText) dialog.findViewById(R.id.tie_dialog_add_member_name);
        tilDialogAddMemberDate = (TextInputLayout) dialog.findViewById(R.id.til_dialog_add_member_date);
        tieDialogAddMemberDate = (TextInputEditText) dialog.findViewById(R.id.tie_dialog_add_member_date);
        btnDialogAddMember = (Button) dialog.findViewById(R.id.btn_dialog_add_member);
        btnDialogAddMemberCancel = (Button) dialog.findViewById(R.id.btn_dialog_add_member_cancel);

        btnDialogAddMember.setOnClickListener(v -> validate(dialog));
        btnDialogAddMemberCancel.setOnClickListener(v -> {
            cancel();
            dialog.dismiss();
        });

        dialog.show();

    }

    private void validate(Dialog dialog) {
        if (tieDialogAddMemberName.getText().length() == 0) {
            tieDialogAddMemberName.requestFocus();
            tilDialogAddMemberName.setError("Vui lòng nhập Họ Tên");
        } else if (tieDialogAddMemberDate.getText().length() == 0) {
            tilDialogAddMemberName.setError("");
            tieDialogAddMemberDate.requestFocus();
            tilDialogAddMemberDate.setError("Vui lòng nhập Năm Sinh");
        } else if (!tieDialogAddMemberDate.getText().toString().matches("[0-9]{4}")) {
            tilDialogAddMemberName.setError("");
            tilDialogAddMemberDate.requestFocus();
            tilDialogAddMemberDate.setError("Vui lòng nhập đúng định dạng");
        } else if (Integer.parseInt(tieDialogAddMemberDate.getText().toString()) < (calendar.get(Calendar.YEAR) - 120)
                || Integer.parseInt(tieDialogAddMemberDate.getText().toString()) > calendar.get(Calendar.YEAR)) {
            tilDialogAddMemberName.setError("");
            tilDialogAddMemberDate.requestFocus();
            tilDialogAddMemberDate.setError("Vui lòng nhập đúng năm sinh");
        } else {
            tilDialogAddMemberName.setError("");
            tilDialogAddMemberDate.setError("");

            MemberModel member = new MemberModel();

            member.setNameMember(tieDialogAddMemberName.getText().toString());
            member.setDateMember(tieDialogAddMemberDate.getText().toString());

            add(member, dialog);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void add(MemberModel member, Dialog dialog) {
        dao.open();
        long kq = dao.insert(member);

        if (kq > 0) {
            list.clear();
            list.addAll(dao.getAllData());
            adapter.notifyDataSetChanged();
            clear();
            dialog.dismiss();
            Toast.makeText(getContext(), "Thêm mới thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Thêm mới thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    private void clear() {
        tieDialogAddMemberName.setText("");
        tieDialogAddMemberDate.setText("");

        tilDialogAddMemberName.setError("");
        tilDialogAddMemberDate.setError("");
    }

    private void cancel() {
        clear();
    }
}