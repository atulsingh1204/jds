package com.bpointer.rkofficial.Model.Response.BidHistoryReponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PointDatum {
    @SerializedName("point")
    @Expose
    private Integer point;
    @SerializedName("number")
    @Expose
    private String number;

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
