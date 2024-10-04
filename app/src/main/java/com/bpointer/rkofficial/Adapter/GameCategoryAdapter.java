package com.bpointer.rkofficial.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bpointer.rkofficial.Activity.GameActivity;
import com.bpointer.rkofficial.Activity.WebViewActivity;
import com.bpointer.rkofficial.Common.CustomDialog;
import com.bpointer.rkofficial.Common.PreferenceManager;
import com.bpointer.rkofficial.Model.Response.HomeResponseModel.Company;
import com.bpointer.rkofficial.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.bpointer.rkofficial.Common.AppConstant.APP_STATUS;
import static com.bpointer.rkofficial.Common.AppConstant.ID;

public class GameCategoryAdapter extends RecyclerView.Adapter<GameCategoryAdapter.ViewHolder> {
    Context mContext;
    List<Company> companyList;
    CustomDialog customDialog;
    PreferenceManager preferenceManager;
    String chart_url = "";

    public GameCategoryAdapter(Context mContext, List<Company> companyList) {
        this.mContext = mContext;
        this.companyList = companyList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.category_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Company company = companyList.get(position);

        customDialog = new CustomDialog(mContext);
        preferenceManager = new PreferenceManager(mContext);

        if (5 == preferenceManager.getIntPreference(ID)) {
            holder.bt_play.setVisibility(View.INVISIBLE);
        } else {
            holder.bt_play.setVisibility(View.VISIBLE);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (preferenceManager.getStringPreference(APP_STATUS).equalsIgnoreCase("1")) {
                        if (holder.tv_msg.getText().toString().trim().equals("Market Open Let's Enjoy")) {
                            Intent intent = new Intent(mContext, GameActivity.class);
                            intent.putExtra("company_id", company.getCompanyId());
                            intent.putExtra("company_name", company.getCompanyName());
                            intent.putExtra("open_time", company.getOpenTime());
                            intent.putExtra("close_time", company.getCloseTime());
                            mContext.startActivity(intent);
                        } else {
                            customDialog.showFailureDialog("Ops ! Market Is Closed For Today");
                        }
                    }
                }
            });

            holder.bt_play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*if (preferenceManager.getStringPreference(APP_STATUS).equalsIgnoreCase("1")) {
                        if (holder.tv_msg.getText().toString().trim().equals("Market Open Let's Enjoy")) {
                            Intent intent = new Intent(mContext, GameActivity.class);
                            intent.putExtra("company_id", company.getCompanyId());
                            intent.putExtra("company_name", company.getCompanyName());
                            intent.putExtra("open_time", company.getOpenTime());
                            intent.putExtra("close_time",company.getCloseTime());
                            mContext.startActivity(intent);
                        } else {
                            customDialog.showFailureDialog("Ops ! Market Is Closed For Today");
                        }
                    }*/

                    /*if (preferenceManager.getStringPreference(APP_STATUS).equalsIgnoreCase("1")){
                        chart_url = companyList.get(position).getChart_url();

                        if (holder.tv_msg.getText().toString().trim().equalsIgnoreCase("Market Open Let's Enjoy")){
                            Intent intent = new Intent(mContext, WebViewActivity.class);
                            intent.putExtra("chart_url", chart_url);
                            mContext.startActivity(intent);
                        }
                        else {
                            customDialog.showFailureDialog("Ops! Market is Closed for Today");
                        }
                    }*/ //  commented this code because don't need of validations

                    chart_url = companyList.get(position).getChart_url();
                    Intent intent = new Intent(mContext, WebViewActivity.class);
                    intent.putExtra("chart_url", chart_url);
                    mContext.startActivity(intent);







                }
            });

        }

        holder.tv_result.setText(company.getResult());
        holder.tv_name.setText(company.getCompanyName());
        holder.tv_open_bid.setText(getTime(company.getOpenTime()));
        holder.tv_close_bid.setText(getTime(company.getCloseTime()));

        holder.tv_result.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.shake));


        Calendar now = Calendar.getInstance();
        int day = now.get(Calendar.DAY_OF_WEEK);
        int hour = now.get(Calendar.HOUR_OF_DAY); // Get hour in 24 hour format
        int minute = now.get(Calendar.MINUTE);
        Date currentTime = parseDate(hour + ":" + minute);
        Date closeTime = parseDate(company.getCloseTime());

        if (company.getAllCompanyStatus() == 1) {
            switch (day) {
                case Calendar.SUNDAY:
                    if (company.getSunday() == 1) {
                        checkMarketStatus(currentTime, closeTime, holder.tv_msg);
                    } else {
                        closeCompany(holder.tv_msg);
                    }
                    break;

                case Calendar.MONDAY:
                    if (company.getMonday() == 1) {
                        checkMarketStatus(currentTime, closeTime, holder.tv_msg);
                    } else {
                        closeCompany(holder.tv_msg);
                    }
                    break;

                case Calendar.TUESDAY:
                    if (company.getTuesday() == 1) {
                        checkMarketStatus(currentTime, closeTime, holder.tv_msg);
                    } else {
                        closeCompany(holder.tv_msg);
                    }
                    break;

                case Calendar.WEDNESDAY:
                    if (company.getWednesday() == 1) {
                        checkMarketStatus(currentTime, closeTime, holder.tv_msg);
                    } else {
                        closeCompany(holder.tv_msg);
                    }
                    break;

                case Calendar.THURSDAY:
                    if (company.getThursday() == 1) {
                        checkMarketStatus(currentTime, closeTime, holder.tv_msg);
                    } else {
                        closeCompany(holder.tv_msg);
                    }
                    break;

                case Calendar.FRIDAY:
                    if (company.getFriday() == 1) {
                        checkMarketStatus(currentTime, closeTime, holder.tv_msg);
                    } else {
                        closeCompany(holder.tv_msg);
                    }
                    break;

                case Calendar.SATURDAY:
                    if (company.getSaturday() == 1) {
                        checkMarketStatus(currentTime, closeTime, holder.tv_msg);
                    } else {
                        closeCompany(holder.tv_msg);
                    }
                    break;
            }
        } else {
            closeCompany(holder.tv_msg);
        }
    }

    private void checkMarketStatus(Date currentTime, Date closeTime, TextView textView) {
        if (currentTime.before(closeTime)) {
            openCompany(textView);
        } else {
            closeCompany(textView);
        }
    }

    private void openCompany(TextView textView) {
        textView.setText("Market Open Let's Enjoy");
        textView.setTextColor(ContextCompat.getColor(mContext, R.color.greenMenu));
    }

    private void closeCompany(TextView textView) {
        textView.setText("Market Close For Today");
        textView.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
    }

    private Date parseDate(String date) {
        final String inputFormat = "HH:mm";
        SimpleDateFormat inputParser = new SimpleDateFormat(inputFormat, Locale.US);
        try {
            return inputParser.parse(date);
        } catch (java.text.ParseException e) {
            return new Date(0);
        }
    }


    private String getTime(String Time) {
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            final Date dateObj = sdf.parse(Time);
            Log.e("Time", dateObj + " after formatting " + new SimpleDateFormat("h:mm a").format(dateObj));
            return (new SimpleDateFormat("h:mm a").format(dateObj));
        } catch (final ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        holder.tv_result.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.shake));
    }

    @Override
    public int getItemCount() {
        return companyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_result, tv_msg, tv_open_bid, tv_close_bid;
        ImageView bt_play;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bt_play = itemView.findViewById(R.id.bt_play);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_result = itemView.findViewById(R.id.tv_result);
            tv_msg = itemView.findViewById(R.id.tv_msg);
            tv_open_bid = itemView.findViewById(R.id.tv_open_bid);
            tv_close_bid = itemView.findViewById(R.id.tv_close_bid);
        }
    }
}

