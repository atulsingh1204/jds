package com.bpointer.rkofficial.Model.Response.BidHistoryReponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Game {
    @SerializedName("play_date")
    @Expose
    private String playDate;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("game_name")
    @Expose
    private String gameName;
    @SerializedName("is_win")
    @Expose
    private Integer isWin;
    @SerializedName("total_amount")
    @Expose
    private Integer totalAmount;
    @SerializedName("point_data")
    @Expose
    private List<PointDatum> pointData = null;

    public String getPlayDate() {
        return playDate;
    }

    public void setPlayDate(String playDate) {
        this.playDate = playDate;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public Integer getIsWin() {
        return isWin;
    }

    public void setIsWin(Integer isWin) {
        this.isWin = isWin;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<PointDatum> getPointData() {
        return pointData;
    }

    public void setPointData(List<PointDatum> pointData) {
        this.pointData = pointData;
    }
}
