package com.example.thvinphngnam.Adapters;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thvinphngnam.Models.TopModel;
import com.example.thvinphngnam.R;

import java.util.List;

public class Top10Adapter extends RecyclerView.Adapter<Top10Adapter.Top10ViewHolder> {
    List<TopModel> list;

    public Top10Adapter(List<TopModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Top10ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item_view = View.inflate(parent.getContext(), R.layout.item_view_top10, null);

        return new Top10ViewHolder(item_view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Top10ViewHolder holder, int position) {
        TopModel top = list.get(position);

        holder.itvTopId.setText("" + (position + 1));
        holder.itvTopName.setText(top.getName());
        holder.itvTopAmount.setText("Số lượng: " + top.getAmount());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Top10ViewHolder extends RecyclerView.ViewHolder {
        final private TextView itvTopId, itvTopName, itvTopAmount;

        public Top10ViewHolder(@NonNull View itemView) {
            super(itemView);

            itvTopId = itemView.findViewById(R.id.itv_top_id);
            itvTopName = itemView.findViewById(R.id.itv_top_name);
            itvTopAmount = itemView.findViewById(R.id.itv_top_amount);

        }
    }
}
