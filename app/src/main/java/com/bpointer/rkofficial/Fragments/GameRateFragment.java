package com.bpointer.rkofficial.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bpointer.rkofficial.Adapter.GameRateAdapter;
import com.bpointer.rkofficial.Api.Api;
import com.bpointer.rkofficial.Api.Authentication;
import com.bpointer.rkofficial.Common.CustomDialog;
import com.bpointer.rkofficial.Model.Response.GameRatingResponseModel.GameRatingResponseModel;
import com.bpointer.rkofficial.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameRateFragment extends Fragment implements View.OnClickListener {
    View view;
    ImageView iv_back;
    TextView tv_title;
    RecyclerView rv_game_rate;
    CustomDialog customDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_game_rate, container, false);
    
        initView();

        getGameRatingAPI();

        return view;
    }

    private void getGameRatingAPI() {
       customDialog.showLoader();

        Call<GameRatingResponseModel> call = Api.getClient().create(Authentication.class).getGameRating();
        call.enqueue(new Callback<GameRatingResponseModel>() {
            @Override
            public void onResponse(Call<GameRatingResponseModel> call, Response<GameRatingResponseModel> response) {
                customDialog.closeLoader();
                if (response.body() != null) {
                    if (response.body().getStatus().equals("true")) {
                        GameRateAdapter gameRateAdapter = new GameRateAdapter(getContext(), response.body().getGameRating());
                        rv_game_rate.setAdapter(gameRateAdapter);
                    } else {
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<GameRatingResponseModel> call, Throwable t) {
                customDialog.closeLoader();
                Log.e("LoginResponse", "onFailure: " + t.getMessage());
            }
        });

    }

    private void initView() {
        iv_back = view.findViewById(R.id.iv_back);
        tv_title = view.findViewById(R.id.tv_title);
        rv_game_rate = view.findViewById(R.id.rv_game_rate);

        tv_title.setText("Game Rate");

        customDialog=new CustomDialog(getContext());

        rv_game_rate.setLayoutManager(new LinearLayoutManager(getContext()));

        iv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) {
            getActivity().onBackPressed();
        }
    }
}
