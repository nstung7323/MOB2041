package com.example.thvinphngnam.Adapters;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thvinphngnam.DAO.BookDAO;
import com.example.thvinphngnam.DAO.CategoryBookDAO;
import com.example.thvinphngnam.Models.BookModel;
import com.example.thvinphngnam.Models.CategoryBookModel;
import com.example.thvinphngnam.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private TextInputLayout tilDialogUpdateBookName, tilDialogUpdateBookPrice, tilDialogUpdateBookAuthor;
    private TextInputEditText tieDialogUpdateBookName, tieDialogUpdateBookPrice, tieDialogUpdateBookAuthor;

    private Spinner spnUpdateCategoryBook;
    Button btnDialogUpdateBook, btnDialogUpdateBookCancel;

    CategoryBookDAO categoryBookDAO;
    String[] categoryBooks;

    List<BookModel> list;
    BookDAO dao;

    public BookAdapter(List<BookModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item_view = View.inflate(parent.getContext(), R.layout.item_view_book, null);

        categoryBookDAO = new CategoryBookDAO(parent.getContext());
        categoryBookDAO.open();
        dao = new BookDAO(parent.getContext());

        return new BookViewHolder(item_view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        BookModel book = list.get(position);

        holder.itvBookId.setText(position + 1 + "");
        holder.itvBookName.setText("Tên sách: " + book.getNameBook());
        holder.itvBookPrice.setText("Loại sách: " + categoryBookDAO.getDataById(book.getCategoryBook()).getNameCategoryBook());
        holder.itvCategoryBook.setText("Giá: " + book.getPriceBook());
        holder.itvBookAuthor.setText("Tác giả: " + book.getAuthor());
        holder.itvBookUpdate.setOnClickListener(v -> showDialogUpdate(book, holder.itemView.getContext(), position));
        holder.itvBookDelete.setOnClickListener(v -> showDialogDelete(book, holder.itemView.getContext()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        final private TextView itvBookId, itvBookName, itvCategoryBook, itvBookPrice, itvBookAuthor;
        final private ImageView itvBookUpdate, itvBookDelete;


        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            itvBookId = itemView.findViewById(R.id.itv_book_id);
            itvBookName = itemView.findViewById(R.id.itv_book_name);
            itvCategoryBook = itemView.findViewById(R.id.itv_category_book);
            itvBookPrice = itemView.findViewById(R.id.itv_book_price);
            itvBookAuthor = itemView.findViewById(R.id.itv_book_author);
            itvBookUpdate = itemView.findViewById(R.id.itv_book_update);
            itvBookDelete = itemView.findViewById(R.id.itv_book_delete);
        }
    }

    private void showDialogUpdate(BookModel book, Context context, int position) {
        Dialog dialog = new Dialog(context, com.airbnb.lottie.R.style.Theme_AppCompat_Light_Dialog_Alert);

        dialog.setContentView(R.layout.dialog_update_book);

        tilDialogUpdateBookName = dialog.findViewById(R.id.til_dialog_update_book_name);
        tieDialogUpdateBookName = dialog.findViewById(R.id.tie_dialog_update_book_name);
        tilDialogUpdateBookPrice = dialog.findViewById(R.id.til_dialog_update_book_price);
        tieDialogUpdateBookPrice = dialog.findViewById(R.id.tie_dialog_update_book_price);
        tilDialogUpdateBookAuthor = dialog.findViewById(R.id.til_dialog_update_book_author);
        tieDialogUpdateBookAuthor = dialog.findViewById(R.id.tie_dialog_update_book_author);
        spnUpdateCategoryBook = dialog.findViewById(R.id.spn_update_category_book);
        btnDialogUpdateBook = dialog.findViewById(R.id.btn_dialog_update_book);
        btnDialogUpdateBookCancel = dialog.findViewById(R.id.btn_dialog_update_book_cancel);

        btnDialogUpdateBook.setOnClickListener(v -> validate(book, context, position, dialog));
        btnDialogUpdateBookCancel.setOnClickListener(v -> {
            cancel();
            dialog.dismiss();
        });

        setValueSpinner(context, book);
        dialog.show();
    }

    private void setValueSpinner(Context context, BookModel book) {
        int index = 0;
        categoryBooks = new String[categoryBookDAO.getAllData().size()];

        for (int i = 0; i < categoryBookDAO.getAllData().size(); i++) {
            categoryBooks[i] = categoryBookDAO.getAllData().get(i).getNameCategoryBook();
            if (categoryBookDAO.getAllData().get(i).getIdCategoryBook() == book.getCategoryBook()) {
                index = i;
            }
        }

        ArrayAdapter adapter = new ArrayAdapter(context, com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item, categoryBooks);
        spnUpdateCategoryBook.setAdapter(adapter);
        spnUpdateCategoryBook.setSelection(index);
    }

    private void validate(BookModel book, Context context, int position, Dialog dialog) {
        CategoryBookModel categoryBook = categoryBookDAO.getAllData().get(spnUpdateCategoryBook.getSelectedItemPosition());

        if (tieDialogUpdateBookName.getText().length() == 0) {
            tieDialogUpdateBookName.requestFocus();
            tilDialogUpdateBookName.setError("Vui lòng nhập tên Sách");
        } else if (tieDialogUpdateBookPrice.getText().length() == 0) {
            tilDialogUpdateBookName.setError("");
            tieDialogUpdateBookPrice.requestFocus();
            tilDialogUpdateBookPrice.setError("Vui lòng nhập giá Sách");
        } else if (tieDialogUpdateBookAuthor.getText().length() == 0) {
            tilDialogUpdateBookName.setError("");
            tilDialogUpdateBookPrice.setError("");
            tieDialogUpdateBookAuthor.requestFocus();
            tilDialogUpdateBookAuthor.setError("Vui lòng nhập tác giả");
        } else if (tieDialogUpdateBookName.getText().toString().equals(book.getNameBook())
                && tieDialogUpdateBookPrice.getText().toString().equals(String.valueOf(book.getPriceBook()))
                && categoryBook.getIdCategoryBook() == book.getCategoryBook()
                && tieDialogUpdateBookAuthor.getText().toString().equals(book.getAuthor())) {
            Toast.makeText(context, "Dữ liệu giống nhau", Toast.LENGTH_SHORT).show();
        } else {
            tilDialogUpdateBookName.setError("");
            tilDialogUpdateBookPrice.setError("");

            book.setNameBook(tieDialogUpdateBookName.getText().toString());
            book.setPriceBook(Integer.parseInt(tieDialogUpdateBookPrice.getText().toString()));
            book.setCategoryBook(categoryBook.getIdCategoryBook());
            book.setAuthor(tieDialogUpdateBookAuthor.getText().toString());

            update(book, context, position, dialog);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void update(BookModel book, Context context, int position, Dialog dialog) {
        dao.open();
        int kq = dao.update(book);

        if (kq > 0) {
            list.set(position, book);
            notifyDataSetChanged();
            clear();
            dialog.dismiss();
            Toast.makeText(context, "Cập nhập thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Cập nhập thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDialogDelete(BookModel book, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Xóa Thành Viên?");
        builder.setMessage("Bạn có muốn xóa sách có mã " + book.getIdBook() + " không?");
        builder.setPositiveButton("Có", (dialog, which) -> {
            delete(book, context);
            dialog.dismiss();
        });
        builder.setNegativeButton("Không", (dialog, which) -> dialog.dismiss());

        Dialog dialog = builder.create();
        dialog.show();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void delete(BookModel book, Context context) {
        dao.open();
        int kq = dao.delete(book);

        if (kq > 0) {
            list.clear();
            list.addAll(dao.getAllData());
            notifyDataSetChanged();
            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    private void clear() {
        tieDialogUpdateBookName.setText("");
        tieDialogUpdateBookPrice.setText("");

        tilDialogUpdateBookName.setError("");
        tilDialogUpdateBookPrice.setError("");
    }

    private void cancel() {
        clear();
    }
}
