package com.example.thvinphngnam.Adapters;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thvinphngnam.DAO.MemberDAO;
import com.example.thvinphngnam.Models.MemberModel;
import com.example.thvinphngnam.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.List;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MemberViewHolder> {
    private TextInputLayout tilDialogUpdateMemberName, tilDialogUpdateMemberDate;
    private TextInputEditText tieDialogUpdateMemberName, tieDialogUpdateMemberDate;
    Button btnDialogUpdateMember, btnDialogUpdateMemberCancel;

    List<MemberModel> list;
    MemberDAO dao;

    Calendar calendar = Calendar.getInstance();

    public MemberAdapter(List<MemberModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item_view = View.inflate(parent.getContext(), R.layout.item_view_member, null);
        dao = new MemberDAO(parent.getContext());

        return new MemberViewHolder(item_view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MemberViewHolder holder, int position) {
        MemberModel member = list.get(position);

        holder.itvMemberId.setText(position + 1 + "");
        holder.itvMemberName.setText(member.getNameMember());
        holder.itvMemberDate.setText("Năm sinh: " + member.getDateMember());
        holder.itvMemberUpdate.setOnClickListener(v -> showDialogUpdate(member, holder.itemView.getContext(), position));
        holder.itvMemberDelete.setOnClickListener(v -> showDialogDelete(member, holder.itemView.getContext()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MemberViewHolder extends RecyclerView.ViewHolder {
        TextView itvMemberId, itvMemberName, itvMemberDate;
        ImageView itvMemberUpdate, itvMemberDelete;

        public MemberViewHolder(@NonNull View itemView) {
            super(itemView);

            itvMemberId = itemView.findViewById(R.id.itv_member_id);
            itvMemberName = itemView.findViewById(R.id.itv_member_name);
            itvMemberDate = itemView.findViewById(R.id.itv_member_date);
            itvMemberUpdate = itemView.findViewById(R.id.itv_member_update);
            itvMemberDelete = itemView.findViewById(R.id.itv_member_delete);

        }
    }

    private void showDialogUpdate(MemberModel member, Context context, int position) {
        Dialog dialog = new Dialog(context, com.airbnb.lottie.R.style.Theme_AppCompat_Light_Dialog_Alert);

        dialog.setContentView(R.layout.dialog_update_member);

        tilDialogUpdateMemberName = dialog.findViewById(R.id.til_dialog_update_member_name);
        tieDialogUpdateMemberName = dialog.findViewById(R.id.tie_dialog_update_member_name);
        tilDialogUpdateMemberDate = dialog.findViewById(R.id.til_dialog_update_member_date);
        tieDialogUpdateMemberDate = dialog.findViewById(R.id.tie_dialog_update_member_date);
        btnDialogUpdateMember = dialog.findViewById(R.id.btn_dialog_update_member);
        btnDialogUpdateMemberCancel = dialog.findViewById(R.id.btn_dialog_update_member_cancel);

        btnDialogUpdateMember.setOnClickListener(v -> validate(member, context, position, dialog));
        btnDialogUpdateMemberCancel.setOnClickListener(v -> {
            cancel();
            dialog.dismiss();
        });

        dialog.show();
    }

    private void validate(MemberModel member, Context context, int position, Dialog dialog) {
        if (tieDialogUpdateMemberName.getText().length() == 0) {
            tieDialogUpdateMemberName.requestFocus();
            tilDialogUpdateMemberName.setError("Vui lòng nhập Họ Tên");
        } else if (tieDialogUpdateMemberDate.getText().length() == 0) {
            tilDialogUpdateMemberName.setError("");
            tieDialogUpdateMemberDate.requestFocus();
            tilDialogUpdateMemberDate.setError("Vui lòng nhập Năm Sinh");
        } else if (!tieDialogUpdateMemberDate.getText().toString().matches("[0-9]{4}")) {
            tilDialogUpdateMemberName.setError("");
            tilDialogUpdateMemberDate.requestFocus();
            tilDialogUpdateMemberDate.setError("Vui lòng nhập đúng định dạng");
        } else if (Integer.parseInt(tieDialogUpdateMemberDate.getText().toString()) < (calendar.get(Calendar.YEAR) - 120)
                || Integer.parseInt(tieDialogUpdateMemberDate.getText().toString()) > calendar.get(Calendar.YEAR)) {
            tilDialogUpdateMemberName.setError("");
            tilDialogUpdateMemberDate.requestFocus();
            tilDialogUpdateMemberDate.setError("Vui lòng nhập đúng năm sinh");
        } else if (tieDialogUpdateMemberName.getText().toString().equals(member.getNameMember())
                && tieDialogUpdateMemberDate.getText().toString().equalsIgnoreCase(member.getDateMember())) {
            Toast.makeText(context, "Dữ liệu giống nhau", Toast.LENGTH_SHORT).show();
        } else {
            tilDialogUpdateMemberName.setError("");
            tilDialogUpdateMemberDate.setError("");

            member.setNameMember(tieDialogUpdateMemberName.getText().toString());
            member.setDateMember(tieDialogUpdateMemberDate.getText().toString());

            update(member, context, position, dialog);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void update(MemberModel member, Context context, int position, Dialog dialog) {
        dao.open();
        int kq = dao.update(member);

        if (kq > 0) {
            list.set(position, member);
            notifyDataSetChanged();
            clear();
            dialog.dismiss();
            Toast.makeText(context, "Cập nhập thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Cập nhập thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDialogDelete(MemberModel member, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Xóa Thành Viên?");
        builder.setMessage("Bạn có muốn xóa loại sách có mã " + member.getIdMember() + " không?");
        builder.setPositiveButton("Có", (dialog, which) -> {
            delete(member, context);
            dialog.dismiss();
        });
        builder.setNegativeButton("Không", (dialog, which) -> dialog.dismiss());

        Dialog dialog = builder.create();
        dialog.show();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void delete(MemberModel member, Context context) {
        dao.open();
        int kq = dao.delete(member);

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
        tieDialogUpdateMemberName.setText("");
        tieDialogUpdateMemberDate.setText("");

        tilDialogUpdateMemberName.setError("");
        tilDialogUpdateMemberDate.setError("");
    }

    private void cancel() {
        clear();
    }

}
