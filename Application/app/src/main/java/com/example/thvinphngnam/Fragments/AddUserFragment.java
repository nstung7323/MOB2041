package com.example.thvinphngnam.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.thvinphngnam.DAO.LibrarianDAO;
import com.example.thvinphngnam.Models.LibrarianModel;
import com.example.thvinphngnam.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AddUserFragment extends Fragment {
    private TextInputLayout tilAddUserUserName, tilAddUserFullName, tilAddUserPassword1, tilAddUserPassword2;
    private TextInputEditText tieAddUserUserName, tieAddUserFullName, tieAddUserPassword1, tieAddUserPassword2;
    Button btnAddUser, btnAddUserCancel;

    LibrarianDAO dao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewAddUser = inflater.inflate(R.layout.fragment_add_user, container, false);

        tilAddUserUserName = viewAddUser.findViewById(R.id.til_addUser_userName);
        tieAddUserUserName = viewAddUser.findViewById(R.id.tie_addUser_userName);
        tilAddUserFullName = viewAddUser.findViewById(R.id.til_addUser_fullName);
        tieAddUserFullName = viewAddUser.findViewById(R.id.tie_addUser_fullName);
        tilAddUserPassword1 = viewAddUser.findViewById(R.id.til_addUser_password1);
        tieAddUserPassword1 = viewAddUser.findViewById(R.id.tie_addUser_password1);
        tilAddUserPassword2 = viewAddUser.findViewById(R.id.til_addUser_password2);
        tieAddUserPassword2 = viewAddUser.findViewById(R.id.tie_addUser_password2);
        btnAddUser = viewAddUser.findViewById(R.id.btn_addUser);
        btnAddUserCancel = viewAddUser.findViewById(R.id.btn_addUser_cancel);

        dao = new LibrarianDAO(getContext());
        dao.open();

        btnAddUser.setOnClickListener(v -> addUser());
        btnAddUserCancel.setOnClickListener(v -> clear());

        return viewAddUser;
    }

    private void validate() {
        if (tieAddUserUserName.getText().length() == 0) {
            clearError();
            tieAddUserUserName.requestFocus();
            tilAddUserUserName.setError("Vui lòng nhập UserName");
        } else if (tieAddUserFullName.getText().length() == 0) {
            clearError();
            tieAddUserFullName.requestFocus();
            tilAddUserFullName.setError("Vui lòng nhập Họ Tên");
        } else if (tieAddUserPassword1.getText().length() == 0) {
            clearError();
            tieAddUserPassword1.requestFocus();
            tilAddUserPassword1.setError("Vui lòng nhập PassWord");
        } else if (tieAddUserPassword2.getText().length() == 0) {
            clearError();
            tieAddUserPassword2.requestFocus();
            tilAddUserPassword2.setError("Vui lòng nhập PassWord");
        }
        else if (dao.getDataById(tieAddUserUserName.getText().toString()) != null) {
            clearError();
            tilAddUserUserName.setError("Mã Thủ thư đã tồn tại");
            tieAddUserUserName.requestFocus();
        }
        else if (!tieAddUserPassword1.getText().toString()
                .equals(tieAddUserPassword2.getText().toString())) {
            clearError();
            tieAddUserPassword2.requestFocus();
            tilAddUserPassword2.setError("Mật khẩu không trùng khớp!");
        }
        else {
            clearError();
            LibrarianModel librarian = new LibrarianModel();

            librarian.setIdLibrarian(tieAddUserUserName.getText().toString());
            librarian.setNameLibrarian(tieAddUserFullName.getText().toString());
            librarian.setPasswordsLibrarian(tieAddUserPassword1.getText().toString());

            add(librarian);
        }
    }

    private void add(LibrarianModel librarian) {
        dao.open();
        long kq = dao.insert(librarian);

        if (kq > 0) {
            clear();
            Toast.makeText(getContext(), "Thêm mới thành công", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getContext(), "Thêm mới thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    private void addUser() {
        validate();
    }

    private void clear() {
        clearText();
        clearError();
    }
    private void clearText() {
        tieAddUserUserName.setText("");
        tieAddUserFullName.setText("");
        tieAddUserPassword1.setText("");
        tieAddUserPassword2.setText("");
    }
    private void clearError() {
        tilAddUserUserName.setError("");
        tilAddUserFullName.setError("");
        tilAddUserPassword1.setError("");
        tilAddUserPassword2.setError("");
    }

}