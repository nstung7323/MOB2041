package com.example.thvinphngnam.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thvinphngnam.Adapters.Top10Adapter;
import com.example.thvinphngnam.DAO.RevenueDAO;
import com.example.thvinphngnam.R;

public class TopFragment extends Fragment {
    RecyclerView rcvTop;
    RevenueDAO dao;
    Top10Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewTop = inflater.inflate(R.layout.fragment_top, container, false);

        rcvTop = viewTop.findViewById(R.id.rcv_top);
        dao = new RevenueDAO(getContext());
        dao.open();

        adapter = new Top10Adapter(dao.getDataTop());
        rcvTop.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvTop.setAdapter(adapter);

        return viewTop;
    }

}