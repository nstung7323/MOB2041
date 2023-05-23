package com.example.thvinphngnam.Fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.thvinphngnam.Adapters.BookAdapter;
import com.example.thvinphngnam.DAO.BookDAO;
import com.example.thvinphngnam.DAO.CategoryBookDAO;
import com.example.thvinphngnam.Models.BookModel;
import com.example.thvinphngnam.Models.CategoryBookModel;
import com.example.thvinphngnam.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class BookFragment extends Fragment {
    RecyclerView rcvBook;
    FloatingActionButton fabBook;

    private EditText edtSearchAuthor;
    private ImageView imgSearchAuthor;
    private TextInputLayout tilDialogAddBookName, tilDialogAddBookPrice, tilDialogAddBookAuthor;
    private TextInputEditText tieDialogAddBookName, tieDialogAddBookPrice, tieDialogAddBookAuthor;

    private Spinner spnAddCategoryBook;
    Button btnDialogAddBook, btnDialogAddBookCancel;

    CategoryBookDAO categoryBookDAO;
    String[] categoryBooks;

    BookDAO dao;
    BookAdapter adapter;
    List<BookModel> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewBook = inflater.inflate(R.layout.fragment_book, container, false);

        edtSearchAuthor = viewBook.findViewById(R.id.edt_search_author);
        imgSearchAuthor = viewBook.findViewById(R.id.img_search_author);
        rcvBook = viewBook.findViewById(R.id.rcv_book);
        fabBook = viewBook.findViewById(R.id.fab_book);

        categoryBookDAO = new CategoryBookDAO(getContext());
        categoryBookDAO.open();

        dao = new BookDAO(getContext());
        dao.open();
        list = dao.getAllData();

        adapter = new BookAdapter(list);
        rcvBook.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvBook.setAdapter(adapter);

        fabBook.setOnClickListener(v -> showDialogAddBook());
        imgSearchAuthor.setOnClickListener(v -> searchAuthor());

        return viewBook;
    }


    private void showDialogAddBook() {
        Dialog dialog = new Dialog(getContext(), com.airbnb.lottie.R.style.Theme_AppCompat_Light_Dialog_Alert);

        dialog.setContentView(R.layout.dialog_add_book);

        tilDialogAddBookName = dialog.findViewById(R.id.til_dialog_add_book_name);
        tieDialogAddBookName = dialog.findViewById(R.id.tie_dialog_add_book_name);
        tilDialogAddBookPrice = dialog.findViewById(R.id.til_dialog_add_book_price);
        tieDialogAddBookPrice = dialog.findViewById(R.id.tie_dialog_add_book_price);
        tilDialogAddBookAuthor = dialog.findViewById(R.id.til_dialog_add_book_author);
        tieDialogAddBookAuthor = dialog.findViewById(R.id.tie_dialog_add_book_author);
        spnAddCategoryBook = dialog.findViewById(R.id.spn_add_category_book);
        btnDialogAddBook = dialog.findViewById(R.id.btn_dialog_add_book);
        btnDialogAddBookCancel = dialog.findViewById(R.id.btn_dialog_add_book_cancel);
        btnDialogAddBook.setOnClickListener(v -> validate(dialog));
        btnDialogAddBookCancel.setOnClickListener(v -> {
            cancel();
            dialog.dismiss();
        });

        setValueSpinner(dialog);
    }

    private void setValueSpinner(Dialog dialog) {
        if (categoryBookDAO.getAllData().size() == 0) {
            Toast.makeText(getContext(), "Vui lòng thêm loại sách trước", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            return;
        } else {
            categoryBooks = new String[categoryBookDAO.getAllData().size()];

            for (int i = 0; i < categoryBookDAO.getAllData().size(); i++) {
                categoryBooks[i] = categoryBookDAO.getAllData().get(i).getNameCategoryBook();
            }
        }

        ArrayAdapter adapter = new ArrayAdapter(getContext(), com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item, categoryBooks);
        spnAddCategoryBook.setAdapter(adapter);
        dialog.show();
    }

    private void validate(Dialog dialog) {
        CategoryBookModel categoryBook = categoryBookDAO.getAllData().get(spnAddCategoryBook.getSelectedItemPosition());

        if (tieDialogAddBookName.getText().length() == 0) {
            tieDialogAddBookName.requestFocus();
            tilDialogAddBookName.setError("Vui lòng nhập tên Sách");
        } else if (tieDialogAddBookPrice.getText().length() == 0) {
            tilDialogAddBookName.setError("");
            tieDialogAddBookPrice.requestFocus();
            tilDialogAddBookPrice.setError("Vui lòng nhập giá Sách");
        } else if (tieDialogAddBookAuthor.getText().length() == 0) {
            tilDialogAddBookName.setError("");
            tilDialogAddBookPrice.setError("");
            tilDialogAddBookAuthor.setError("Vui lòng nhập tác giả");
            tieDialogAddBookAuthor.requestFocus();
        } else {
            tilDialogAddBookName.setError("");
            tilDialogAddBookPrice.setError("");

            BookModel book = new BookModel();

            book.setNameBook(tieDialogAddBookName.getText().toString());
            book.setPriceBook(Integer.parseInt(tieDialogAddBookPrice.getText().toString()));
            book.setCategoryBook(categoryBook.getIdCategoryBook());
            book.setAuthor(tieDialogAddBookAuthor.getText().toString());

            add(book, dialog);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void add(BookModel book, Dialog dialog) {
        dao.open();
        long kq = dao.insert(book);

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
        tieDialogAddBookName.setText("");
        tieDialogAddBookPrice.setText("");

        tilDialogAddBookName.setError("");
        tilDialogAddBookPrice.setError("");
    }

    private void cancel() {
        clear();
    }

    public void searchAuthor() {
        List<BookModel> listAuthor = new ArrayList<>();
        if (list.size() == 0) {
            Toast.makeText(getContext(), "Chưa có dữ liệu!", Toast.LENGTH_SHORT).show();
        } else {
            if (edtSearchAuthor.getText().length() == 0) {
                Toast.makeText(getContext(), "Vui lòng nhập để tìm", Toast.LENGTH_SHORT).show();
            } else {
                for (BookModel item : dao.getAllData()) {
                    if (item.getAuthor().indexOf(edtSearchAuthor.getText().toString()) != -1) {
                        listAuthor.add(item);
                    }
                }
                if (listAuthor.size() != 0) {
                    list.clear();
                    list.addAll(listAuthor);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Không có tác giả", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}