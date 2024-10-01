package com.bpointer.rkofficial.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bpointer.rkofficial.Common.PreferenceManager;
import com.bpointer.rkofficial.R;

import static com.bpointer.rkofficial.Common.AppConstant.ADMIN_WHATSAPP;

public class NoticeBoardFragment extends Fragment implements View.OnClickListener {
    View view;
    ImageView iv_back;
    TextView tv_title, tv_whatsapp;
    PreferenceManager preferenceManager;
    RelativeLayout rl_whats_app;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notice_board, container, false);
    
        initView();
        return view;
    }

    private void initView() {
        iv_back = view.findViewById(R.id.iv_back);
        tv_title = view.findViewById(R.id.tv_title);
        rl_whats_app = view.findViewById(R.id.rl_whats_app);
        tv_whatsapp = view.findViewById(R.id.tv_whatsapp);

        preferenceManager = new PreferenceManager(getContext());

        tv_title.setText("Notice Board And Rule/Regulation");

        tv_whatsapp.setText("" + preferenceManager.getStringPreference(ADMIN_WHATSAPP));

        iv_back.setOnClickListener(this);
        rl_whats_app.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                getActivity().onBackPressed();
                break;

            case R.id.rl_whats_app:
                callNumber();
                break;
        }
    }

    private void callNumber() {
        String sMobile = preferenceManager.getStringPreference(ADMIN_WHATSAPP);
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        Log.e("Phone", "Phone" + sMobile);
        callIntent.setData(Uri.parse("tel:" + sMobile));
        startActivity(callIntent);
    }
}
