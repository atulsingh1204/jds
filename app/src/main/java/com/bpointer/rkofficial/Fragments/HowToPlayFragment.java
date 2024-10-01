package com.bpointer.rkofficial.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bpointer.rkofficial.R;

public class HowToPlayFragment extends Fragment implements View.OnClickListener {
    View view;
    ImageView iv_back;
    TextView tv_title, tv_name;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_how_to_play, container, false);
    
        initView();

        return view;
    }

    private void initView() {
        iv_back = view.findViewById(R.id.iv_back);
        tv_title = view.findViewById(R.id.tv_title);
        tv_name = view.findViewById(R.id.tv_name);

        tv_title.setText("How To Play");

        tv_name.setText("Demo Video");

        iv_back.setOnClickListener(this);
        tv_name.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                getActivity().onBackPressed();
                break;

            case R.id.tv_name:
                openLink();
                break;
        }
    }

    private void openLink() {
//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=QM2-MX1Lz-A"));
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=zL-VaXqCKBw&rco=1"));
        startActivity(intent);
    }
}
