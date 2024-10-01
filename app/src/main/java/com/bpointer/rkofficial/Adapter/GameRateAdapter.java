package com.bpointer.rkofficial.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bpointer.rkofficial.Model.Response.GameRatingResponseModel.GameRating;
import com.bpointer.rkofficial.R;

import java.util.List;

public class GameRateAdapter extends RecyclerView.Adapter<GameRateAdapter.ViewHolder> {
    Context context;
    List<GameRating> gameRatingList;

    public GameRateAdapter(Context context, List<GameRating> gameRatingList) {
        this.context = context;
        this.gameRatingList = gameRatingList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_rate_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GameRating gameRating = gameRatingList.get(position);

        holder.tv_rate.setText("*" + gameRating.getGameName() + "(10:" + gameRating.getGameRate() + ")");
    }

    @Override
    public int getItemCount() {
        return gameRatingList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_rate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_rate = itemView.findViewById(R.id.tv_rate);
        }
    }
}
