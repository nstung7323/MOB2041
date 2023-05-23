package com.example.thvinphngnam.Adapters;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thvinphngnam.DAO.CategoryBookDAO;
import com.example.thvinphngnam.Models.CategoryBookModel;
import com.example.thvinphngnam.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class CategoryBookAdapter extends RecyclerView.Adapter<CategoryBookAdapter.CategoryViewHolder> {
    private TextInputLayout tilDialogUpdateCategoryBookName, tilDialogUpdateCategoryBookSupplier;
    private TextInputEditText tieDialogUpdateCategoryBookName, tieDialogUpdateCategoryBookSupplier;
    Button btnDialogUpdateCategoryBook, btnDialogUpdateCategoryBookCancel;

    List<CategoryBookModel> list;
    CategoryBookDAO dao;

    public CategoryBookAdapter(List<CategoryBookModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item_view = View.inflate(parent.getContext(), R.layout.item_view_category_book, null);
        dao = new CategoryBookDAO(parent.getContext());

        return new CategoryViewHolder(item_view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CategoryBookModel categoryBook = list.get(position);

        holder.itvCategoryBookId.setText(position + 1 + "");
        holder.itvCategoryBookName.setText(categoryBook.getNameCategoryBook());
        if (categoryBook.getNameCategoryBook().indexOf("N") != -1) {
            holder.itvCategoryBookName.setTextColor(Color.GREEN);
        } else if (categoryBook.getNameCategoryBook().indexOf("A") != -1) {
            holder.itvCategoryBookName.setTextColor(Color.RED);
        }
        else {
            holder.itvCategoryBookName.setTextColor(Color.BLACK);
        }
        holder.itvCategoryBookSupplier.setText(categoryBook.getSupplier());
        holder.itvCategoryBookUpdate.setOnClickListener(v -> showDialogUpdate(categoryBook, holder.itemView.getContext(), position));
        holder.itvCategoryBookDelete.setOnClickListener(v -> showDialogDelete(categoryBook, holder.itemView.getContext()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        private final TextView itvCategoryBookId;
        private final TextView itvCategoryBookName;
        private TextView itvCategoryBookSupplier;

        private final ImageView itvCategoryBookUpdate;
        private final ImageView itvCategoryBookDelete;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            itvCategoryBookId = (TextView) itemView.findViewById(R.id.itv_category_book_id);
            itvCategoryBookName = (TextView) itemView.findViewById(R.id.itv_category_book_name);
            itvCategoryBookSupplier = (TextView) itemView.findViewById(R.id.itv_category_book_supplier);
            itvCategoryBookUpdate = (ImageView) itemView.findViewById(R.id.itv_category_book_update);
            itvCategoryBookDelete = (ImageView) itemView.findViewById(R.id.itv_category_book_delete);
        }
    }

    private void showDialogUpdate(CategoryBookModel categoryBook, Context context, int position) {
        Dialog dialog = new Dialog(context, com.airbnb.lottie.R.style.Theme_AppCompat_Light_Dialog_Alert);

        dialog.setContentView(R.layout.dialog_update_category_book);

        tilDialogUpdateCategoryBookName = (TextInputLayout) dialog.findViewById(R.id.til_dialog_update_category_book_name);
        tieDialogUpdateCategoryBookName = (TextInputEditText) dialog.findViewById(R.id.tie_dialog_update_category_book_name);
        tilDialogUpdateCategoryBookSupplier = (TextInputLayout) dialog.findViewById(R.id.til_dialog_update_category_book_supplier);
        tieDialogUpdateCategoryBookSupplier = (TextInputEditText) dialog.findViewById(R.id.tie_dialog_update_category_book_supplier);
        btnDialogUpdateCategoryBook = (Button) dialog.findViewById(R.id.btn_dialog_update_category_book);
        btnDialogUpdateCategoryBookCancel = (Button) dialog.findViewById(R.id.btn_dialog_update_category_book_cancel);

        btnDialogUpdateCategoryBook.setOnClickListener(v -> validate(categoryBook, context, position, dialog));
        btnDialogUpdateCategoryBookCancel.setOnClickListener(v -> {
            cancel();
            dialog.dismiss();
        });

        dialog.show();
    }

    private void validate(CategoryBookModel categoryBook, Context context, int position, Dialog dialog) {
        if (tieDialogUpdateCategoryBookName.getText().length() == 0) {
            tieDialogUpdateCategoryBookName.requestFocus();
            tilDialogUpdateCategoryBookName.setError("Vui lòng nhập Loại sách");
        } else if (tieDialogUpdateCategoryBookSupplier.getText().length() == 0) {
            tieDialogUpdateCategoryBookSupplier.requestFocus();
            tilDialogUpdateCategoryBookSupplier.setError("Vui lòng nhập Nhà cung cấp");
            tilDialogUpdateCategoryBookName.setError("");
        } else if (tieDialogUpdateCategoryBookName.getText().toString().equals(categoryBook.getNameCategoryBook())
                && tieDialogUpdateCategoryBookSupplier.getText().toString().equals(categoryBook.getSupplier())) {
            Toast.makeText(context, "Dữ liệu giống nhau", Toast.LENGTH_SHORT).show();
        } else {
            tilDialogUpdateCategoryBookName.setError("");
            tilDialogUpdateCategoryBookSupplier.setError("");

            categoryBook.setNameCategoryBook(tieDialogUpdateCategoryBookName.getText().toString());
            categoryBook.setSupplier(tieDialogUpdateCategoryBookSupplier.getText().toString());

            update(categoryBook, context, position, dialog);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void update(CategoryBookModel categoryBook, Context context, int position, Dialog dialog) {
        dao.open();
        int kq = dao.update(categoryBook);

        if (kq > 0) {
            list.set(position, categoryBook);
            notifyDataSetChanged();
            clear();
            dialog.dismiss();
            Toast.makeText(context, "Cập nhập thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Cập nhập thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDialogDelete(CategoryBookModel categoryBook, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Xóa Thành Viên?");
        builder.setMessage("Bạn có muốn xóa thành viên có mã " + categoryBook.getIdCategoryBook() + " không?");
        builder.setPositiveButton("Có", (dialog, which) -> {
            delete(categoryBook, context);
            dialog.dismiss();
        });
        builder.setNegativeButton("Không", (dialog, which) -> dialog.dismiss());

        Dialog dialog = builder.create();
        dialog.show();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void delete(CategoryBookModel categoryBook, Context context) {
        dao.open();
        int kq = dao.delete(categoryBook);

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
        tieDialogUpdateCategoryBookName.setText("");
        tilDialogUpdateCategoryBookName.setError("");

        tieDialogUpdateCategoryBookSupplier.setText("");
        tilDialogUpdateCategoryBookSupplier.setError("");
    }

    private void cancel() {
        clear();
    }
}
