package com.bpointer.rkofficial.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bpointer.rkofficial.Activity.DepositHistoryActivity;
import com.bpointer.rkofficial.Model.Response.DepositHistoryResponseModel.DepositHistory;
import com.bpointer.rkofficial.Model.Response.WithdrawHistoryResponseModel.WithdrawHistory;
import com.bpointer.rkofficial.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DepositHistoryAdapter extends RecyclerView.Adapter<DepositHistoryAdapter.ViewHolder> {
    Context context;
    List<DepositHistory> depositHistoryList;

    public DepositHistoryAdapter(Context context, List<DepositHistory> depositHistoryList) {
        this.context = context;
        this.depositHistoryList = depositHistoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.deposit_history_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DepositHistory history = depositHistoryList.get(position);

        holder.tv_transaction_date.setText(getDate(history.getCreatedAt()));
        holder.tv_point.setText("" + history.getDepositAmount() + "/-");
        holder.tv_transaction_number.setText("" + history.getTransactionNumber());
    }

    @SuppressLint("SimpleDateFormat")
    private String getDate(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dateObj = sdf.parse(date);
            Log.e("TAG", "getDate: " + date + " :- " + new SimpleDateFormat("dd-MM-yyyy").format(dateObj));
            return (new SimpleDateFormat("dd-MM-yyyy").format(dateObj));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int getItemCount() {
        return depositHistoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_transaction_date, tv_point, tv_transaction_number;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_transaction_date = itemView.findViewById(R.id.tv_transaction_date);
            tv_point = itemView.findViewById(R.id.tv_point);
            tv_transaction_number = itemView.findViewById(R.id.tv_transaction_number);
        }
    }
}
