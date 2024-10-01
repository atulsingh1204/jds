package com.bpointer.rkofficial.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bpointer.rkofficial.Model.Response.BidHistoryReponseModel.PointDatum;
import com.bpointer.rkofficial.R;

import java.util.List;

public class PointDataAdapter extends RecyclerView.Adapter<PointDataAdapter.ViewHolder> {
    Context context;
    List<PointDatum> pointDatumList;
    String gameName;

    public PointDataAdapter(Context context, List<PointDatum> pointDatumList, String gameName) {
        this.context = context;
        this.pointDatumList = pointDatumList;
        this.gameName = gameName;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.point_data_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PointDatum datum = pointDatumList.get(position);

        holder.tv_point.setText(datum.getPoint() + " Rs.");

       /* if (gameName.equals("Half Sangam") || gameName.equals("Full Sangam")) {
            String number = datum.getNumber();
            String newNumber = number.replaceAll(" ", "\n");
            holder.tv_number.setText(newNumber);
        } else {*/
            holder.tv_number.setText(datum.getNumber());
//        }
    }

    @Override
    public int getItemCount() {
        return pointDatumList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_point, tv_number;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_point = itemView.findViewById(R.id.tv_point);
            tv_number = itemView.findViewById(R.id.tv_number);

        }
    }
}
