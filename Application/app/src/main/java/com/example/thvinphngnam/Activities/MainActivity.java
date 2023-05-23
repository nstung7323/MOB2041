package com.example.thvinphngnam.Activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.thvinphngnam.Fragments.AddUserFragment;
import com.example.thvinphngnam.Fragments.BookFragment;
import com.example.thvinphngnam.Fragments.CategoryBookFragment;
import com.example.thvinphngnam.Fragments.ChangePasswordFragment;
import com.example.thvinphngnam.Fragments.LoanSlipFragment;
import com.example.thvinphngnam.Fragments.MemberFragment;
import com.example.thvinphngnam.Fragments.RevenueFragment;
import com.example.thvinphngnam.Fragments.TopFragment;
import com.example.thvinphngnam.R;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerMain;
    private Toolbar toolbarMain;
    private NavigationView nvView;

//    public static String ID_USER;
//    public static String NAME_USER;
//    public static String PASS_USER;

    ActionBarDrawerToggle actionBarDrawerToggle;
    Fragment fragment;
    String titleToolBar = "Quản lý Phiếu Mượn";

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerMain = findViewById(R.id.drawer_main);
        toolbarMain = findViewById(R.id.toolbar_main);
        nvView = findViewById(R.id.nv_view);

        toolbarMain.setTitle(titleToolBar);
        setSupportActionBar(toolbarMain);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerMain, toolbarMain,
                R.string.navagation_drawer_open, R.string.navagation_drawer_close);
        drawerMain.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

//        checkUser();
        choseItem();
        setFragmentDefault();

        Toast.makeText(this, "Chào mừng đến với trang chủ", Toast.LENGTH_LONG).show();
    }

    private void setFragmentDefault() {
        toolbarMain.setTitle(titleToolBar);
        LoanSlipFragment loanSlipFragment = new LoanSlipFragment();
//        loanSlipFragment.ID_USER = ID_USER;

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_main_container, loanSlipFragment)
                .commit();
    }

    @SuppressLint("NonConstantResourceId")
    public void choseItem() {
        nvView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_loanSip:
                    LoanSlipFragment loanSlipFragment = new LoanSlipFragment();
//                    loanSlipFragment.ID_USER = ID_USER;

                    fragment = loanSlipFragment;
                    titleToolBar = "Quản lý Phiếu Mượn";
                    break;
                case R.id.nav_categoryBook:
                    fragment = new CategoryBookFragment();
                    titleToolBar = "Quản lý Loại Sách";
                    break;
                case R.id.nav_book:
                    fragment = new BookFragment();
                    titleToolBar = "Quản lý Sách";
                    break;
                case R.id.nav_member:
                    fragment = new MemberFragment();
                    titleToolBar = "Quản lý Thành Viên";
                    break;
                case R.id.nav_top:
                    fragment = new TopFragment();
                    titleToolBar = "Thống kê Top 10 Sách";
                    break;
                case R.id.nav_revenue:
                    fragment = new RevenueFragment();
                    titleToolBar = "Doanh Thu";
                    break;

//                case R.id.nav_addUser:
//                    fragment = new AddUserFragment();
//                    titleToolBar = "Thêm thành viên";
//                    break;
//
//                case R.id.nav_changePassword:
//                    ChangePasswordFragment changePasswordFragment = new ChangePasswordFragment();
//                    changePasswordFragment.ID_USER = ID_USER;
//                    changePasswordFragment.NAME_USER = NAME_USER;
//                    changePasswordFragment.PASS_BEFORE = PASS_USER;
//
//                    fragment = changePasswordFragment;
//                    titleToolBar = "Đổi mật khẩu";
//                    break;
//
//                case R.id.nav_logOut:
//                    showDiaLogLogOut();
//                    break;
            }

            try {
                toolbarMain.setTitle(titleToolBar);

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_main_container, fragment)
                        .commit();
                drawerMain.closeDrawer(GravityCompat.START);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return false;
        });
    }

//    public void showDiaLogLogOut() {
//        AlertDialog.Builder buiderLogOut = new AlertDialog.Builder(MainActivity.this);
//        buiderLogOut.setTitle("Đăng xuất?");
//        buiderLogOut.setMessage("Bạn có muốn đăng xuất không?");
//        buiderLogOut.setPositiveButton("Có", (dialog, which) -> {
//            finish();
//            dialog.dismiss();
//        });
//        buiderLogOut.setNegativeButton("Không", (dialog, which) -> dialog.dismiss());
//
//        Dialog dialogLogOut = buiderLogOut.create();
//        dialogLogOut.show();
//    }

//    public void checkUser() {
//        Intent intent = getIntent();
//        Bundle bundle = intent.getBundleExtra("USER");
//
//        ID_USER = bundle.getString("ID");
//
//        if (ID_USER.equalsIgnoreCase("ADMIN")) {
//            NAME_USER = "ADMIN";
//            nvView.getMenu().findItem(R.id.nav_addUser).setVisible(true);
//        } else {
//            NAME_USER = bundle.getString("NAME");
//        }
//
//        PASS_USER = bundle.getString("PASS");
//
//        View headerView = nvView.getHeaderView(0);
//        TextView tvHeaderName = headerView.findViewById(R.id.tv_nav_header_name);
//        tvHeaderName.setText(NAME_USER);
//    }

}
