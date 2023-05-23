package com.example.thvinphngnam.Fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thvinphngnam.Adapters.LoanSlipAdapter;
import com.example.thvinphngnam.DAO.BookDAO;
import com.example.thvinphngnam.DAO.LoanSlipDAO;
import com.example.thvinphngnam.DAO.MemberDAO;
import com.example.thvinphngnam.Models.BookModel;
import com.example.thvinphngnam.Models.LoanSlipModel;
import com.example.thvinphngnam.Models.MemberModel;
import com.example.thvinphngnam.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class LoanSlipFragment extends Fragment {
//    public String ID_USER;

    private Spinner spnAddLoanSlipMember, spnAddLoanSlipBook;
    private TextInputLayout tilDialogAddLoanSlipDate, tilDialogAddLoanSlipPrice;
    private TextInputEditText tieDialogAddLoanSlipDate, tieDialogAddLoanSlipPrice;
    private CheckBox chkDialogLoanSlipState;
    Button btnDialogAddLoanSlip, btnDialogAddLoanSlipCancel;

    public RecyclerView rcvLoanSlip;
    FloatingActionButton fabLoanSlip;

    LoanSlipDAO dao;
    public LoanSlipAdapter adapter;

    MemberDAO memberDAO;
    String[] members;

    BookDAO bookDAO;
    String[] books;

    List<LoanSlipModel> list;

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    Calendar calendar = Calendar.getInstance();
    DatePickerDialog datePickerDialog;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewLoanSlip = inflater.inflate(R.layout.fragment_loan_slip, container, false);

        rcvLoanSlip = viewLoanSlip.findViewById(R.id.rcv_loanSlip);
        fabLoanSlip = viewLoanSlip.findViewById(R.id.fab_loanSlip);

        dao = new LoanSlipDAO(getContext());
        dao.open();
        list = dao.getAllData();
//        adapter = new LoanSlipAdapter(list, ID_USER);
        adapter = new LoanSlipAdapter(list);

        rcvLoanSlip.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvLoanSlip.setAdapter(adapter);

        memberDAO = new MemberDAO(getContext());
        memberDAO.open();

        bookDAO = new BookDAO(getContext());
        bookDAO.open();

        fabLoanSlip.setOnClickListener(v -> showDialogAddLoanSlip());

        datePickerDialog = new DatePickerDialog(getContext(), (view, year, month, dayOfMonth) -> {
            String m = String.valueOf(month + 1).length() == 2 ? String.valueOf(month + 1) : ("0" + (month + 1));
            String d = String.valueOf(dayOfMonth).length() == 2 ? String.valueOf(dayOfMonth) : ("0" + (dayOfMonth));
            tieDialogAddLoanSlipDate.setText(year + "-" + (m) + "-" + d);
        }
                , calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH));

        return viewLoanSlip;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void showDialogAddLoanSlip() {
        Dialog dialog = new Dialog(getContext(), com.airbnb.lottie.R.style.Theme_AppCompat_Light_Dialog_Alert);

        dialog.setContentView(R.layout.dialog_add_loan_slip);

        spnAddLoanSlipMember = dialog.findViewById(R.id.spn_add_loan_slip_member);
        spnAddLoanSlipBook = dialog.findViewById(R.id.spn_add_loan_slip_book);
        tilDialogAddLoanSlipDate = dialog.findViewById(R.id.til_dialog_add_loan_slip_date);
        tieDialogAddLoanSlipDate = dialog.findViewById(R.id.tie_dialog_add_loan_slip_date);
        tilDialogAddLoanSlipPrice = dialog.findViewById(R.id.til_dialog_add_loan_slip_price);
        tieDialogAddLoanSlipPrice = dialog.findViewById(R.id.tie_dialog_add_loan_slip_price);
        chkDialogLoanSlipState = dialog.findViewById(R.id.chk_dialog_loan_slip_state);
        btnDialogAddLoanSlip = dialog.findViewById(R.id.btn_dialog_add_loan_slip);
        btnDialogAddLoanSlipCancel = dialog.findViewById(R.id.btn_dialog_add_loan_slip_cancel);

        tieDialogAddLoanSlipDate.setOnTouchListener((v, event) -> {
            datePickerDialog.show();
            return false;
        });
        btnDialogAddLoanSlip.setOnClickListener(v -> validate(dialog));
        btnDialogAddLoanSlipCancel.setOnClickListener(v -> {
            cancel();
            dialog.dismiss();
        });

        setValueSpinnerMember(dialog);
        setValueSpinnerBook(dialog);
    }

    private void setValueSpinnerMember(Dialog dialog) {
        if (memberDAO.getAllData().size() == 0) {
            Toast.makeText(getContext(), "Vui lòng thêm thành viên trước", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            return;
        } else {
            members = new String[memberDAO.getAllData().size()];

            for (int i = 0; i < memberDAO.getAllData().size(); i++) {
                members[i] = memberDAO.getAllData().get(i).getNameMember();
            }
        }

        ArrayAdapter adapter = new ArrayAdapter(getContext(), com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item, members);
        spnAddLoanSlipMember.setAdapter(adapter);

        dialog.show();
    }

    private void setValueSpinnerBook(Dialog dialog) {
        if (bookDAO.getAllData().size() == 0) {
            Toast.makeText(getContext(), "Vui lòng thêm sách trước", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            return;
        } else {
            books = new String[bookDAO.getAllData().size()];

            for (int i = 0; i < bookDAO.getAllData().size(); i++) {
                books[i] = bookDAO.getAllData().get(i).getNameBook();
            }
        }

        ArrayAdapter adapter = new ArrayAdapter(getContext(), com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item, books);
        spnAddLoanSlipBook.setAdapter(adapter);

        dialog.show();
    }

    private void validate(Dialog dialog) {
        MemberModel member = memberDAO.getAllData().get(spnAddLoanSlipMember.getSelectedItemPosition());
        BookModel book = bookDAO.getAllData().get(spnAddLoanSlipBook.getSelectedItemPosition());
        int state = chkDialogLoanSlipState.isChecked() ? 1 : 0;

        if (tieDialogAddLoanSlipDate.getText().length() == 0) {
            tilDialogAddLoanSlipDate.setError("Vui lòng Ngày thuê");
            datePickerDialog.show();
            return;
        }
        if (tieDialogAddLoanSlipPrice.getText().length() == 0) {
            tilDialogAddLoanSlipDate.setError("");
            tieDialogAddLoanSlipPrice.requestFocus();
            tilDialogAddLoanSlipPrice.setError("Vui lòng nhập Giá thuê");
            return;
        }
        if (tieDialogAddLoanSlipDate.getText().length() == 10) {
            tilDialogAddLoanSlipPrice.setError("");
            try {
                format.parse(tieDialogAddLoanSlipDate.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
                tilDialogAddLoanSlipDate.setError("Vui lòng nhập đúng định dạng");
                return;
            }

        } else {
            tilDialogAddLoanSlipDate.setError("");
            tilDialogAddLoanSlipDate.setError("Vui lòng nhập đúng định dạng");
            return;
        }

        tilDialogAddLoanSlipDate.setError("");
        tilDialogAddLoanSlipPrice.setError("");

        LoanSlipModel loanSlip = new LoanSlipModel();

//        loanSlip.setIdLibrarianFK(ID_USER);
        loanSlip.setIdMemberFK(member.getIdMember());
        loanSlip.setIdBookFK(book.getIdBook());
        loanSlip.setDateLoanSlip(tieDialogAddLoanSlipDate.getText().toString());
        loanSlip.setStateLoanSlip(state);
        loanSlip.setPriceLoanSlip(Integer.parseInt(tieDialogAddLoanSlipPrice.getText().toString()));

        add(loanSlip, dialog);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void add(LoanSlipModel loanSlipModel, Dialog dialog) {
        dao.open();
        long kq = dao.insert(loanSlipModel);

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
        tieDialogAddLoanSlipDate.setText("");
        tieDialogAddLoanSlipPrice.setText("");

        tilDialogAddLoanSlipDate.setError("");
        tilDialogAddLoanSlipPrice.setError("");
    }

    private void cancel() {
        clear();
    }
}