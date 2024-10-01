package com.bpointer.rkofficial.Fragments;

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

public class StarLineRulesFragment extends Fragment {
    View view;
    ImageView iv_back;
    TextView tv_title, tv_market_name, tv_desc, tv_notice;
    String market_name;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_star_line_rules, container, false);
    
        initView();
        return view;
    }

    private void initView() {
        iv_back = view.findViewById(R.id.iv_back);
        tv_title = view.findViewById(R.id.tv_title);
        tv_market_name = view.findViewById(R.id.tv_market_name);
        tv_desc = view.findViewById(R.id.tv_desc);
        tv_notice = view.findViewById(R.id.tv_notice);

        Bundle bundle = getArguments();
        market_name = bundle.getString("market_name");

        tv_title.setText(market_name.toUpperCase() + " TERMS AND CONDITION");
        tv_market_name.setText(market_name.toUpperCase() + " TERMS AND CONDITION");
        tv_desc.setText(market_name.toUpperCase() + " will Run Every Day From Monday to Sunday There is No Jodi(Bracket) Game In " + market_name.toUpperCase() + " You can play bet in single ank and panna only below are game ratio details.");
        tv_notice.setText(" You can check the my " + market_name.toUpperCase() + " bid history and result history from the dashboard");

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }
}
