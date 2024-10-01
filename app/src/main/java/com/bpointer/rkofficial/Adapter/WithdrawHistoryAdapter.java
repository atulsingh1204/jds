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

import com.bpointer.rkofficial.Model.Response.WithdrawHistoryResponseModel.WithdrawHistory;
import com.bpointer.rkofficial.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class WithdrawHistoryAdapter extends RecyclerView.Adapter<WithdrawHistoryAdapter.ViewHolder> {
    Context context;
    List<WithdrawHistory> withdrawHistoryList;

    public WithdrawHistoryAdapter(Context context,List<WithdrawHistory> withdrawHistoryList) {
        this.context=context;
        this.withdrawHistoryList = withdrawHistoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.withdraw_history_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WithdrawHistory history = withdrawHistoryList.get(position);

        holder.tv_transaction_date.setText(getDate(history.getCreatedAt()));
        holder.tv_point.setText("" + history.getWithdrawAmount()+"/-");

        if (history.getIsAccept() == 0) {
            holder.tv_status.setText("Success");
            holder.tv_status.setTextColor(ContextCompat.getColor(context, R.color.greenMenu));
        } else {
            holder.tv_status.setText("Pending");
            holder.tv_status.setTextColor(ContextCompat.getColor(context, R.color.black));
        }

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
        return withdrawHistoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_transaction_date, tv_point, tv_status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_transaction_date = itemView.findViewById(R.id.tv_transaction_date);
            tv_point = itemView.findViewById(R.id.tv_point);
            tv_status = itemView.findViewById(R.id.tv_status);
        }
    }

}
