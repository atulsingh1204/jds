package com.bpointer.rkofficial.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bpointer.rkofficial.Model.Response.BidHistoryReponseModel.Game;
import com.bpointer.rkofficial.R;
import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.bpointer.rkofficial.Common.AppConstant.SliderImgURL;

public class GameHistoryAdapter extends RecyclerView.Adapter<GameHistoryAdapter.ViewHolder> {
    Context context;
    List<Game> gameList;

    public GameHistoryAdapter(Context context, List<Game> gameList) {
        this.context = context;
        this.gameList = gameList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_history_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Game game = gameList.get(position);
        holder.tv_company.setText(game.getCompanyName() +" : "+game.getType().toUpperCase());
        holder.tv_game_name.setText(game.getGameName());
        holder.tv_total.setText("Total Amount = " + game.getTotalAmount());

        if (game.getPlayDate() == null) {
            holder.tv_date.setText("Date All");
        } else {
            holder.tv_date.setText("Date " + getDate(game.getPlayDate()));
        }

        PointDataAdapter pointDataAdapter = new PointDataAdapter(context, game.getPointData(), game.getGameName());
        holder.rv_point_data.setAdapter(pointDataAdapter);

        if (game.getIsWin()==1) {
            Glide.with(context).load(R.drawable.win).into(holder.iv_image);
        }else {
            Glide.with(context).load(R.drawable.lose).into(holder.iv_image);
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
        return gameList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_company, tv_date, tv_game_name, tv_total;
        RecyclerView rv_point_data;
        ImageView iv_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_company = itemView.findViewById(R.id.tv_company);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_game_name = itemView.findViewById(R.id.tv_game_name);
            tv_total = itemView.findViewById(R.id.tv_total);
            rv_point_data = itemView.findViewById(R.id.rv_point_data);
            iv_image = itemView.findViewById(R.id.iv_image);

            rv_point_data.setLayoutManager(new LinearLayoutManager(context));
        }
    }
}
