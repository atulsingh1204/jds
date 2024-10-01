package com.bpointer.rkofficial.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bpointer.rkofficial.Model.Response.WinHistoryResponseModel.WinnerHistory;
import com.bpointer.rkofficial.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class WinHistoryAdapter extends RecyclerView.Adapter<WinHistoryAdapter.ViewHolder>{
    Context context;
    List<WinnerHistory> winnerHistoryList;
    public WinHistoryAdapter(Context context, List<WinnerHistory> winnerHistoryList) {
        this.context=context;
        this.winnerHistoryList=winnerHistoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.win_history_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WinnerHistory history = winnerHistoryList.get(position);

        holder.tv_game_name.setText("" + history.getPlay().getGameName());
        holder.tv_play_date.setText(getDate(history.getPlay().getPlayDate()));
        holder.tv_play_time.setText(getTime(history.getPlay().getPlayTime()));
        holder.tv_point.setText("" + history.getGiftPoints());
        holder.tv_digit.setText("" + history.getNumber());

        if (history.getPlay().getGameType()==1) {
            holder.tv_company.setText(""+history.getPlay().getCompany().getCompanyName()+ " Open");
        } else {
            holder.tv_company.setText(""+history.getPlay().getCompany().getCompanyName()+ " Close");
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

    @SuppressLint("SimpleDateFormat")
    private String getTime(String time) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            Date dateObj = sdf.parse(time);
            Log.e("TAG", "getTime: " + time + " :- " + new SimpleDateFormat("h:mm a").format(dateObj));
            return (new SimpleDateFormat("h:mm a").format(dateObj));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public int getItemCount() {
        return winnerHistoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_company, tv_game_name, tv_play_date, tv_play_time, tv_point,  tv_digit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_company = itemView.findViewById(R.id.tv_company);
            tv_game_name = itemView.findViewById(R.id.tv_game_name);
            tv_play_date = itemView.findViewById(R.id.tv_play_date);
            tv_play_time = itemView.findViewById(R.id.tv_play_time);
            tv_point = itemView.findViewById(R.id.tv_point);
            tv_digit = itemView.findViewById(R.id.tv_digit);
        }
    }
}

