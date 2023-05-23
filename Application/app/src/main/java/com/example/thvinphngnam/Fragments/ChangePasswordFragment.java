package com.example.thvinphngnam.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.thvinphngnam.DAO.LibrarianDAO;
import com.example.thvinphngnam.Models.LibrarianModel;
import com.example.thvinphngnam.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class ChangePasswordFragment extends Fragment {
    private TextInputLayout tilChangePasswordPasswordBefore, tilChangePasswordPasswordNew1, tilChangePasswordPasswordNew2;
    private TextInputEditText tieChangePasswordPasswordBefore, tieChangePasswordPasswordNew1, tieChangePasswordPasswordNew2;
    Button btnChangePassword, btnChangePasswordCancel;

    public String ID_USER;
    public String NAME_USER;
    public String PASS_BEFORE;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    LibrarianDAO dao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewChangePassword = inflater.inflate(R.layout.fragment_change_password, container, false);

        tilChangePasswordPasswordBefore = (TextInputLayout) viewChangePassword.findViewById(R.id.til_changePassword_passwordBefore);
        tieChangePasswordPasswordBefore = (TextInputEditText) viewChangePassword.findViewById(R.id.tie_changePassword_passwordBefore);
        tilChangePasswordPasswordNew1 = (TextInputLayout) viewChangePassword.findViewById(R.id.til_changePassword_passwordNew1);
        tieChangePasswordPasswordNew1 = (TextInputEditText) viewChangePassword.findViewById(R.id.tie_changePassword_passwordNew1);
        tilChangePasswordPasswordNew2 = (TextInputLayout) viewChangePassword.findViewById(R.id.til_changePassword_passwordNew2);
        tieChangePasswordPasswordNew2 = (TextInputEditText) viewChangePassword.findViewById(R.id.tie_changePassword_passwordNew2);
        btnChangePassword = (Button) viewChangePassword.findViewById(R.id.btn_changePassword);
        btnChangePasswordCancel = (Button) viewChangePassword.findViewById(R.id.btn_changePassword_cancel);

        dao = new LibrarianDAO(getContext());

        btnChangePassword.setOnClickListener(v -> changePassword());
        btnChangePasswordCancel.setOnClickListener(v -> cancel());

        return viewChangePassword;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        sharedPreferences = context.getSharedPreferences("FILE_USER", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void validate() {
        if (tieChangePasswordPasswordBefore.getText().length() == 0) {
            tieChangePasswordPasswordBefore.requestFocus();
            tilChangePasswordPasswordBefore.setError("Vui lòng nhập Mật Khẩu cũ");
            return;
        }
        if (tieChangePasswordPasswordNew1.getText().length() == 0) {
            tilChangePasswordPasswordBefore.setError("");
            tieChangePasswordPasswordNew1.requestFocus();
            tilChangePasswordPasswordNew1.setError("Vui lòng nhập Mật Khẩu mới");
            return;
        }
        if (tieChangePasswordPasswordNew2.getText().length() == 0) {
            tilChangePasswordPasswordBefore.setError("");
            tilChangePasswordPasswordNew1.setError("");
            tieChangePasswordPasswordNew2.requestFocus();
            tilChangePasswordPasswordNew2.setError("Vui lòng nhập PassWord");
            return;
        }
        if (ID_USER.equalsIgnoreCase("ADMIN")) {
            if (!tieChangePasswordPasswordBefore.getText().toString().equals(sharedPreferences.getString("ADMIN_PASS", ""))) {
                tilChangePasswordPasswordNew1.setError("");
                tilChangePasswordPasswordNew2.setError("");
                tilChangePasswordPasswordBefore.setError("Mật khẩu cũ không trùng khớp");
                return;
            }
        }
        else {
            if (!tieChangePasswordPasswordBefore.getText().toString().equals(PASS_BEFORE)) {
                tilChangePasswordPasswordNew1.setError("");
                tilChangePasswordPasswordNew2.setError("");
                tilChangePasswordPasswordBefore.setError("Mật khẩu cũ không trùng khớp");
                return;
            }
        }
        if (!tieChangePasswordPasswordNew1.getText().toString()
                .equals(tieChangePasswordPasswordNew2.getText().toString())) {
            tilChangePasswordPasswordBefore.setError("");
            tilChangePasswordPasswordNew1.setError("");
            tieChangePasswordPasswordNew2.setError("Mật khẩu không trùng khớp!");
            return;
        }

            tilChangePasswordPasswordBefore.setError("");
            tilChangePasswordPasswordNew1.setError("");
            tilChangePasswordPasswordNew2.setError("");

            LibrarianModel librarian = new LibrarianModel();

            librarian.setIdLibrarian(ID_USER);
            librarian.setNameLibrarian(NAME_USER);
            librarian.setPasswordsLibrarian(tieChangePasswordPasswordNew1.getText().toString());

            if (ID_USER.equalsIgnoreCase("ADMIN")) {
                PASS_BEFORE = tieChangePasswordPasswordNew1.getText().toString();
                editor.remove("ADMIN_PASS");
                editor.remove("PASS_WORD");
                editor.putString("ADMIN_PASS_2", PASS_BEFORE);
                showDiaLogLogOut();
                editor.apply();
            }
            else {
                update(librarian);
            }
    }

    public void update(LibrarianModel librarian) {
        dao.open();
        int kq = dao.update(librarian);

        if (kq > 0) {
            PASS_BEFORE = tieChangePasswordPasswordNew1.getText().toString();
            editor.remove("PASS_WORD");
            showDiaLogLogOut();
            editor.apply();
        } else {
            Toast.makeText(getContext(), "Cập nhập thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    public void changePassword() {
        validate();
    }

    public void clear() {
        tieChangePasswordPasswordBefore.setText("");
        tieChangePasswordPasswordNew1.setText("");
        tieChangePasswordPasswordNew2.setText("");

        tilChangePasswordPasswordBefore.setError("");
        tilChangePasswordPasswordNew1.setError("");
        tilChangePasswordPasswordNew2.setError("");
    }

    public void cancel() {
        clear();
    }

    public void showDiaLogLogOut() {
        AlertDialog.Builder buiderLogOut = new AlertDialog.Builder(getContext());
        buiderLogOut.setTitle("Đăng xuất!");
        buiderLogOut.setMessage("Sau khi đổi mật khẩu bạn nên đăng nhập lại để tránh quên mật khẩu");
        buiderLogOut.setPositiveButton("Đăng xuất", (dialog, which) -> {
            getActivity().finish();
            clear();
            dialog.dismiss();
            Toast.makeText(getContext(), "Cập nhập thành công", Toast.LENGTH_SHORT).show();
        });
        buiderLogOut.setNegativeButton("Không", (dialog, which) -> {
            editor.putString("PASS_WORD", PASS_BEFORE);
            dialog.dismiss();
            clear();
            editor.apply();
            Toast.makeText(getContext(), "Cập nhập thành công", Toast.LENGTH_SHORT).show();
        });

        Dialog dialogLogOut = buiderLogOut.create();
        dialogLogOut.show();
    }

}