package com.bpointer.rkofficial.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bpointer.rkofficial.Interface.ItemClickListener;
import com.bpointer.rkofficial.Model.CartStarLineModel;
import com.bpointer.rkofficial.R;

import java.util.List;

public class CartStarLineAdapter extends RecyclerView.Adapter<CartStarLineAdapter.ViewHolder> {
    Context context;
    List<CartStarLineModel> cartStarLineModelList;
    ItemClickListener itemClickListener;

    public CartStarLineAdapter(Context context, List<CartStarLineModel> cartStarLineModelList, ItemClickListener itemClickListener) {
        this.context = context;
        this.cartStarLineModelList = cartStarLineModelList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_star_line_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartStarLineModel cartModel = cartStarLineModelList.get(position);

        holder.tv_no.setText("" + (position + 1));

        holder.tv_digit.setText(cartModel.getNumber());
        holder.tv_point.setText(cartModel.getPoint());

        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.itemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartStarLineModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_no, tv_digit, tv_point, tv_delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_no = itemView.findViewById(R.id.tv_no);
            tv_digit = itemView.findViewById(R.id.tv_digit);
            tv_point = itemView.findViewById(R.id.tv_point);
            tv_delete = itemView.findViewById(R.id.tv_delete);
        }
    }
}
