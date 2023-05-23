package com.example.thvinphngnam.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.thvinphngnam.DAO.LibrarianDAO;
import com.example.thvinphngnam.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout tilLoginUserName;
    private TextInputEditText tieLoginUserName;
    private TextInputLayout tilLoginPassword;
    private TextInputEditText tieLoginPassword;
    private CheckBox cbLoginSaveInfo;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    LibrarianDAO dao;
    Intent intent;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tilLoginUserName = findViewById(R.id.til_login_userName);
        tieLoginUserName = findViewById(R.id.tie_login_userName);
        tilLoginPassword = findViewById(R.id.til_login_password);
        tieLoginPassword = findViewById(R.id.tie_login_password);
        cbLoginSaveInfo = findViewById(R.id.cb_login_saveInfo);

        dao = new LibrarianDAO(this);
        intent = new Intent(this, MainActivity.class);
        bundle = new Bundle();
    }

    @Override
    protected void onResume() {
        super.onResume();

        sharedPreferences = getSharedPreferences("FILE_USER", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        try {
            sharedPreferences.getString("ADMIN_PASS_2",null).equalsIgnoreCase(null);
            editor.putString("ADMIN_PASS", sharedPreferences.getString("ADMIN_PASS_2",null));
        } catch (Exception e) {
            editor.putString("ADMIN_PASS", "123");
        }
        editor.commit();

        checkPreferences(sharedPreferences.getString("USER_NAME", "")
                , sharedPreferences.getString("PASS_WORD", ""));
    }

    public void checkPreferences(String user, String pass) {
        if (!user.equalsIgnoreCase("")) {
            tieLoginUserName.setText(sharedPreferences.getString("USER_NAME", ""));

        } else {
            tieLoginUserName.setText("");
        }

        cbLoginSaveInfo.setChecked(false);

        if (!pass.equalsIgnoreCase("")) {
            tieLoginPassword.setText(sharedPreferences.getString("PASS_WORD", ""));
            cbLoginSaveInfo.setChecked(true);
        }
        else {
            tieLoginPassword.setText("");
        }
    }

    public void validate() {
        if (tieLoginUserName.getText().length() == 0) {
            tieLoginUserName.requestFocus();
            tilLoginUserName.setError("Vui lòng nhập UserName");
        } else if (tieLoginPassword.getText().length() == 0) {
            tilLoginUserName.setError("");
            tieLoginPassword.requestFocus();
            tilLoginPassword.setError("Vui lòng nhập Password");
        }
        else {
            checkLogin();
        }
    }

    public void checkLogin() {
        if (tieLoginUserName.getText().toString().equalsIgnoreCase("admin")
                && tieLoginPassword.getText().toString()
                .equalsIgnoreCase(sharedPreferences.getString("ADMIN_PASS", ""))) {
            bundle.putString("ID", "ADMIN");
            bundle.putString("NAME", "ADMIN");
            bundle.putString("PASS", tieLoginPassword.getText().toString());
            intent.putExtra("USER", bundle);
            startActivity(intent);
        }
        else {
            dao.open();
            String name = dao.checkLogin(tieLoginUserName.getText().toString(), tieLoginPassword.getText().toString());

            if (name == null) {
                Toast.makeText(this, "UserName hoặc PassWord không đúng", Toast.LENGTH_SHORT).show();
                cbLoginSaveInfo.setChecked(false);
            }
            else {
                bundle.putString("ID", tieLoginUserName.getText().toString());
                bundle.putString("NAME", name);
                bundle.putString("PASS", tieLoginPassword.getText().toString());
                intent.putExtra("USER", bundle);
                startActivity(intent);
            }
        }
    }

    public void savePreferences() {
        editor = sharedPreferences.edit();

        if (cbLoginSaveInfo.isChecked()) {
            editor.putString("USER_NAME", tieLoginUserName.getText().toString());
            editor.putString("PASS_WORD", tieLoginPassword.getText().toString());
        } else {
            editor.remove("USER_NAME");
            editor.remove("PASS_WORD");
        }

        editor.commit();
    }

    public void login(View view) {
        validate();
        savePreferences();
    }

}
