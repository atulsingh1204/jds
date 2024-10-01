package com.bpointer.rkofficial.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bpointer.rkofficial.Model.Response.HomeResponseModel.Slider;
import com.bpointer.rkofficial.R;
import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

import static com.bpointer.rkofficial.Common.AppConstant.SliderImgURL;


public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterViewHolder> {
    List<Slider> bannerList;

    public SliderAdapter(List<Slider> bannerList) {
        this.bannerList = bannerList;
    }

    // We are inflating the slider_layout
    // inside on Create View Holder method.
    @Override
    public SliderAdapterViewHolder onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item, null);
        return new SliderAdapterViewHolder(inflate);
    }

    // Inside on bind view holder we will
    // set data to item of Slider View.
    @Override
    public void onBindViewHolder(SliderAdapterViewHolder viewHolder, final int position) {
        Slider slider = bannerList.get(position);

        Glide.with(viewHolder.itemView).load(SliderImgURL + slider.getSliderImage()).into(viewHolder.iv_image);
    }

    // this method will return
    // the count of our list.
    @Override
    public int getCount() {
        return bannerList.size();
    }

    static class SliderAdapterViewHolder extends SliderViewAdapter.ViewHolder {
        // Adapter class for initializing
        // the views of our slider view.
        View itemView;
        ImageView iv_image;

        public SliderAdapterViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            iv_image = itemView.findViewById(R.id.iv_image);
        }

    }
}