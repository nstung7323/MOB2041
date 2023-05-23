package com.example.thvinphngnam.Adapters;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thvinphngnam.DAO.BookDAO;
import com.example.thvinphngnam.DAO.LoanSlipDAO;
import com.example.thvinphngnam.DAO.MemberDAO;
import com.example.thvinphngnam.Models.BookModel;
import com.example.thvinphngnam.Models.LoanSlipModel;
import com.example.thvinphngnam.Models.MemberModel;
import com.example.thvinphngnam.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class LoanSlipAdapter extends RecyclerView.Adapter<LoanSlipAdapter.LoanSlipViewHolder> {
//    public String ID_USER;
    int indexSpinnerMember;
    int indexSpinnerBook;

    private Spinner spnUpdateLoanSlipMember, spnUpdateLoanSlipBook;
    private TextInputLayout tilDialogUpdateLoanSlipDate, tilDialogUpdateLoanSlipPrice;
    private TextInputEditText tieDialogUpdateLoanSlipDate, tieDialogUpdateLoanSlipPrice;
    private CheckBox chkDialogUpdateLoanSlipState;
    Button btnDialogUpdateLoanSlip, btnDialogUpdateLoanSlipCancel;

    List<LoanSlipModel> list;

    MemberDAO memberDAO;
    String[] members;

    BookDAO bookDAO;
    String[] books;

    LoanSlipDAO dao;

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    Calendar calendar = Calendar.getInstance();
    DatePickerDialog datePickerDialog;

//    public LoanSlipAdapter(List<LoanSlipModel> list, String ID_USER) {
//        this.list = list;
//        this.ID_USER = ID_USER;
//    }


    public LoanSlipAdapter(List<LoanSlipModel> list) {
        this.list = list;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public LoanSlipViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item_view = View.inflate(parent.getContext(), R.layout.item_view_loan_slip, null);

        datePickerDialog = new DatePickerDialog(parent.getContext(), (view, year, month, dayOfMonth) -> {
            String m = String.valueOf(month + 1).length() == 2 ? String.valueOf(month + 1) : ("0" + (month + 1));
            String d = String.valueOf(dayOfMonth).length() == 2 ? String.valueOf(dayOfMonth) : ("0" + (dayOfMonth));
            tieDialogUpdateLoanSlipDate.setText(year + "-" + (m) + "-" + d);
        }
                , calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH));

        memberDAO = new MemberDAO(parent.getContext());
        memberDAO.open();

        bookDAO = new BookDAO(parent.getContext());
        bookDAO.open();

        dao = new LoanSlipDAO(parent.getContext());

        return new LoanSlipViewHolder(item_view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull LoanSlipViewHolder holder, int position) {
        LoanSlipModel loanSlip = list.get(position);

        String nameBook = bookDAO.getDataById(loanSlip.getIdBookFK()).getNameBook();
        String member = memberDAO.getDataById(loanSlip.getIdMemberFK()).getNameMember();
        String state = loanSlip.getStateLoanSlip() == 0 ? "Chưa trả" : "Đã trả";

        holder.itvLoanSlipId.setText(position + 1 + "");
        holder.itvLoanSlipName.setText("Tên sách: " + nameBook);
        holder.itvLoanSlipMember.setText("Thành viên: " + member);
        holder.itvLoanSlipPrice.setText("Giá: " + loanSlip.getPriceLoanSlip() + "đ");
        holder.itvLoanSlipState.setText("Tình trạng: " + state);
        holder.itvLoanSlipState.setTextColor(loanSlip.getStateLoanSlip() == 0 ? Color.RED : Color.BLUE);

        holder.itvLoanSlipUpdate.setOnClickListener(v -> showDialogUpdate(loanSlip, holder.itemView.getContext(), position));
        holder.itvLoanSlipDelete.setOnClickListener(v -> showDialogDelete(loanSlip, holder.itemView.getContext()));
        holder.itemView.setOnLongClickListener(v -> {
            showInfoLoanSlip(loanSlip, holder.itemView.getContext());
            return false;
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class LoanSlipViewHolder extends RecyclerView.ViewHolder {
        final private TextView itvLoanSlipId, itvLoanSlipName, itvLoanSlipMember, itvLoanSlipPrice, itvLoanSlipState;
        final private ImageView itvLoanSlipUpdate, itvLoanSlipDelete;

        public LoanSlipViewHolder(@NonNull View itemView) {
            super(itemView);

            itvLoanSlipId = itemView.findViewById(R.id.itv_loan_slip_id);
            itvLoanSlipName = itemView.findViewById(R.id.itv_loan_slip_name);
            itvLoanSlipMember = itemView.findViewById(R.id.itv_loan_slip_member);
            itvLoanSlipPrice = itemView.findViewById(R.id.itv_loan_slip_price);
            itvLoanSlipState = itemView.findViewById(R.id.itv_loan_slip_state);
            itvLoanSlipUpdate = itemView.findViewById(R.id.itv_loan_slip_update);
            itvLoanSlipDelete = itemView.findViewById(R.id.itv_loan_slip_delete);

        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void showDialogUpdate(LoanSlipModel loanSlip, Context context, int position) {
        Dialog dialog = new Dialog(context, com.airbnb.lottie.R.style.Theme_AppCompat_Light_Dialog_Alert);

        dialog.setContentView(R.layout.dialog_update_loan_slip);

        spnUpdateLoanSlipMember = dialog.findViewById(R.id.spn_update_loan_slip_member);
        spnUpdateLoanSlipBook = dialog.findViewById(R.id.spn_update_loan_slip_book);
        tilDialogUpdateLoanSlipDate = dialog.findViewById(R.id.til_dialog_update_loan_slip_date);
        tieDialogUpdateLoanSlipDate = dialog.findViewById(R.id.tie_dialog_update_loan_slip_date);
        tilDialogUpdateLoanSlipPrice = dialog.findViewById(R.id.til_dialog_update_loan_slip_price);
        tieDialogUpdateLoanSlipPrice = dialog.findViewById(R.id.tie_dialog_update_loan_slip_price);
        chkDialogUpdateLoanSlipState = dialog.findViewById(R.id.chk_dialog_update_loan_slip_state);
        btnDialogUpdateLoanSlip = dialog.findViewById(R.id.btn_dialog_update_loan_slip);
        btnDialogUpdateLoanSlipCancel = dialog.findViewById(R.id.btn_dialog_update_loan_slip_cancel);

        tieDialogUpdateLoanSlipDate.setOnTouchListener((v, event) -> {
            datePickerDialog.show();
            return false;
        });
        btnDialogUpdateLoanSlip.setOnClickListener(v -> validate(loanSlip, context, position, dialog));
        btnDialogUpdateLoanSlipCancel.setOnClickListener(v -> {
            cancel();
            dialog.dismiss();
        });

        setValueSpinnerMember(context, loanSlip);
        setValueSpinnerBook(context, loanSlip);
        dialog.show();
    }

    private void setValueSpinnerMember(Context context, LoanSlipModel loanSlip) {
        members = new String[memberDAO.getAllData().size()];

        for (int i = 0; i < memberDAO.getAllData().size(); i++) {
            members[i] = memberDAO.getAllData().get(i).getNameMember();
            if (memberDAO.getAllData().get(i).getIdMember() == loanSlip.getIdMemberFK()) {
                indexSpinnerMember = i;
            }
        }

        ArrayAdapter adapter = new ArrayAdapter(context, com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item, members);
        spnUpdateLoanSlipMember.setAdapter(adapter);
        spnUpdateLoanSlipMember.setSelection(indexSpinnerMember);
    }

    private void setValueSpinnerBook(Context context, LoanSlipModel loanSlip) {
        books = new String[bookDAO.getAllData().size()];

        for (int i = 0; i < bookDAO.getAllData().size(); i++) {
            books[i] = bookDAO.getAllData().get(i).getNameBook();
            if (bookDAO.getAllData().get(i).getIdBook() == loanSlip.getIdBookFK()) {
                indexSpinnerBook = i;
            }
        }

        ArrayAdapter adapter = new ArrayAdapter(context, com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item, books);
        spnUpdateLoanSlipBook.setAdapter(adapter);
        spnUpdateLoanSlipBook.setSelection(indexSpinnerBook);
    }

    private void validate(LoanSlipModel loanSlip, Context context, int position, Dialog dialog) {
        MemberModel member = memberDAO.getAllData().get(spnUpdateLoanSlipMember.getSelectedItemPosition());
        BookModel book = bookDAO.getAllData().get(spnUpdateLoanSlipBook.getSelectedItemPosition());
        int state = chkDialogUpdateLoanSlipState.isChecked() ? 1 : 0;

        if (tieDialogUpdateLoanSlipDate.getText().length() == 0) {
            tilDialogUpdateLoanSlipDate.setError("Vui lòng Ngày thuê");
            datePickerDialog.show();
            return;
        }
        if (tieDialogUpdateLoanSlipPrice.getText().length() == 0) {
            tilDialogUpdateLoanSlipDate.setError("");
            tieDialogUpdateLoanSlipPrice.requestFocus();
            tilDialogUpdateLoanSlipPrice.setError("Vui lòng nhập Giá thuê");
            return;
        }
        if (tieDialogUpdateLoanSlipDate.getText().length() == 10) {
            tilDialogUpdateLoanSlipPrice.setError("");
            try {
                format.parse(tieDialogUpdateLoanSlipDate.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
                tilDialogUpdateLoanSlipDate.setError("Vui lòng nhập đúng định dạng");
                return;
            }

        } else {
            tilDialogUpdateLoanSlipPrice.setError("");
            tilDialogUpdateLoanSlipDate.setError("Vui lòng nhập đúng định dạng");
            return;
        }
        if (spnUpdateLoanSlipMember.getSelectedItemPosition() == indexSpinnerMember
                && spnUpdateLoanSlipBook.getSelectedItemPosition() == indexSpinnerBook
                && loanSlip.getDateLoanSlip().equalsIgnoreCase(tieDialogUpdateLoanSlipDate.getText().toString())
                && loanSlip.getPriceLoanSlip() == Integer.parseInt(tieDialogUpdateLoanSlipPrice.getText().toString())
                && loanSlip.getStateLoanSlip() == (chkDialogUpdateLoanSlipState.isChecked() ? 1 : 0)) {
            tilDialogUpdateLoanSlipDate.setError("");
            tilDialogUpdateLoanSlipPrice.setError("");
            Toast.makeText(context, "Dữ liệu giống nhau!", Toast.LENGTH_SHORT).show();
            return;
        }
            tilDialogUpdateLoanSlipDate.setError("");
            tilDialogUpdateLoanSlipPrice.setError("");

//            loanSlip.setIdLibrarianFK(ID_USER);
            loanSlip.setIdMemberFK(member.getIdMember());
            loanSlip.setIdBookFK(book.getIdBook());
            loanSlip.setDateLoanSlip(tieDialogUpdateLoanSlipDate.getText().toString());
            loanSlip.setStateLoanSlip(state);
            loanSlip.setPriceLoanSlip(Integer.parseInt(tieDialogUpdateLoanSlipPrice.getText().toString()));

            update(loanSlip, context, position, dialog);

    }

    @SuppressLint("NotifyDataSetChanged")
    private void update(LoanSlipModel loanSlip, Context context, int position, Dialog dialog) {
        dao.open();
        int kq = dao.update(loanSlip);

        if (kq > 0) {
            list.set(position, loanSlip);
            notifyDataSetChanged();
            clear();
            dialog.dismiss();
            Toast.makeText(context, "Cập nhập thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Cập nhập thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDialogDelete(LoanSlipModel loanSlip, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Xóa Phiếu mượn?");
        builder.setMessage("Bạn có muốn xóa phiếu mượn có mã " + loanSlip.getIdLoanSlip() + " không?");
        builder.setPositiveButton("Có", (dialog, which) -> {
            delete(loanSlip, context);
            dialog.dismiss();
        });
        builder.setNegativeButton("Không", (dialog, which) -> dialog.dismiss());

        Dialog dialog = builder.create();
        dialog.show();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void delete(LoanSlipModel loanSlip, Context context) {
        dao.open();
        int kq = dao.delete(loanSlip);

        if (kq > 0) {
            list.clear();
            list.addAll(dao.getAllData());
            notifyDataSetChanged();
            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    private void showInfoLoanSlip(LoanSlipModel loanSlip, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Chi tiết phiếu mượn");
        builder.setMessage("Mã PM: " + loanSlip.getIdLoanSlip()
//                + "\nMã TT: " + loanSlip.getIdLibrarianFK()
                + "\nMã TV: " + loanSlip.getIdMemberFK()
                + "\nMã Sách: " + loanSlip.getIdBookFK()
                + "\nThời gian: " + loanSlip.getDateLoanSlip()
                + "\nTrạng thái: " + (loanSlip.getStateLoanSlip() == 0 ? "Chưa trả" : "Đã trả")
                + "\nGía: " + loanSlip.getPriceLoanSlip() + "đ");
        builder.setNegativeButton("Đóng", (dialog, which) -> dialog.dismiss());

        Dialog dialog = builder.create();
        dialog.show();
    }

    private void clear() {
        tieDialogUpdateLoanSlipDate.setText("");
        tieDialogUpdateLoanSlipPrice.setText("");

        tilDialogUpdateLoanSlipDate.setError("");
        tilDialogUpdateLoanSlipPrice.setError("");
    }

    private void cancel() {
        clear();
    }
}
