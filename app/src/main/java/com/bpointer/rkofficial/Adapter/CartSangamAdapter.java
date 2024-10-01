package com.bpointer.rkofficial.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bpointer.rkofficial.Interface.ItemClickListener;
import com.bpointer.rkofficial.Model.CartModel;
import com.bpointer.rkofficial.R;

import java.util.List;

public class CartSangamAdapter extends RecyclerView.Adapter<CartSangamAdapter.ViewHolder> {
    Context context;
    List<CartModel> cartModelList;
    ItemClickListener itemClickListener;
    String sangamType;

    public CartSangamAdapter(Context context, List<CartModel> cartModelList, ItemClickListener itemClickListener, String sangamType) {
        this.context = context;
        this.cartModelList = cartModelList;
        this.itemClickListener = itemClickListener;
        this.sangamType = sangamType;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_sangam_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartModel cartModel = cartModelList.get(position);

        holder.tv_no.setText("" + (position + 1));
        holder.tv_type.setText(cartModel.getNumber()+" "+sangamType);
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
        return cartModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_no, tv_type, tv_point, tv_delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_no = itemView.findViewById(R.id.tv_no);
            tv_type = itemView.findViewById(R.id.tv_type);
            tv_point = itemView.findViewById(R.id.tv_point);
            tv_delete = itemView.findViewById(R.id.tv_delete);
        }
    }
}

