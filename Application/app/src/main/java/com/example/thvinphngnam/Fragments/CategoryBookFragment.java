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

import com.example.thvinphngnam.Adapters.CategoryBookAdapter;
import com.example.thvinphngnam.DAO.CategoryBookDAO;
import com.example.thvinphngnam.Models.CategoryBookModel;
import com.example.thvinphngnam.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class CategoryBookFragment extends Fragment {
    RecyclerView rcvCategoryBook;
    FloatingActionButton fabCategoryBook;

    private TextInputLayout tilDialogAddCategoryBookName, tilDialogAddCategoryBookSupplier;
    private TextInputEditText tieDialogAddCategoryBookName, tieDialogAddCategoryBookSupplier;

    Button btnDialogAddCategoryBook, btnDialogAddCategoryBookCancel;

    CategoryBookDAO dao;
    CategoryBookAdapter adapter;
    List<CategoryBookModel> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View viewCategoryBook = inflater.inflate(R.layout.fragment_category_book, container, false);

        rcvCategoryBook = (RecyclerView) viewCategoryBook.findViewById(R.id.rcv_categoryBook);
        fabCategoryBook = (FloatingActionButton) viewCategoryBook.findViewById(R.id.fab_categoryBook);

        dao = new CategoryBookDAO(getContext());
        dao.open();
        list = dao.getAllData();

        adapter = new CategoryBookAdapter(list);
        rcvCategoryBook.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvCategoryBook.setAdapter(adapter);

        fabCategoryBook.setOnClickListener(v -> showDialogAddCategoryBook());

        return viewCategoryBook;
    }

    public void showDialogAddCategoryBook() {
        Dialog dialog = new Dialog(getContext(), com.airbnb.lottie.R.style.Theme_AppCompat_Light_Dialog_Alert);

        dialog.setContentView(R.layout.dialog_add_category_book);

        tilDialogAddCategoryBookName = (TextInputLayout) dialog.findViewById(R.id.til_dialog_add_category_book_name);
        tieDialogAddCategoryBookName = (TextInputEditText) dialog.findViewById(R.id.tie_dialog_add_category_book_name);
        tilDialogAddCategoryBookSupplier = (TextInputLayout) dialog.findViewById(R.id.til_dialog_add_category_book_supplier);
        tieDialogAddCategoryBookSupplier = (TextInputEditText) dialog.findViewById(R.id.tie_dialog_add_category_book_supplier);
        btnDialogAddCategoryBook = (Button) dialog.findViewById(R.id.btn_dialog_add_category_book);
        btnDialogAddCategoryBookCancel = (Button) dialog.findViewById(R.id.btn_dialog_add_category_book_cancel);

        btnDialogAddCategoryBook.setOnClickListener(v -> validate(dialog));
        btnDialogAddCategoryBookCancel.setOnClickListener(v -> {
            cancel();
            dialog.dismiss();
        });

        dialog.show();

    }

    private void validate(Dialog dialog) {
        if (tieDialogAddCategoryBookName.getText().length() == 0) {
            tieDialogAddCategoryBookName.requestFocus();
            tilDialogAddCategoryBookName.setError("Vui lòng nhập Loại sách");
        } else if (tieDialogAddCategoryBookSupplier.getText().length() == 0) {
            tieDialogAddCategoryBookSupplier.requestFocus();
            tilDialogAddCategoryBookSupplier.setError("Vui lòng nhập nhà cung cấp");
            tilDialogAddCategoryBookName.setError("");
        } else {
            tilDialogAddCategoryBookName.setError("");
            tilDialogAddCategoryBookSupplier.setError("");

            CategoryBookModel categoryBook = new CategoryBookModel();

            categoryBook.setNameCategoryBook(tieDialogAddCategoryBookName.getText().toString());
            categoryBook.setSupplier(tieDialogAddCategoryBookSupplier.getText().toString());

            add(categoryBook, dialog);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void add(CategoryBookModel categoryBook, Dialog dialog) {
        dao.open();
        long kq = dao.insert(categoryBook);

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
        tieDialogAddCategoryBookName.setText("");
        tilDialogAddCategoryBookName.setError("");

        tieDialogAddCategoryBookSupplier.setText("");
        tilDialogAddCategoryBookSupplier.setError("");
    }

    private void cancel() {
        clear();
    }
}