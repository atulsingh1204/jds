package com.bpointer.rkofficial.Model.Response.StarLineMarketTimeResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StarLineMarketTimeResponseModel {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("wallet")
    @Expose
    private String wallet;
    @SerializedName("star_line_company")
    @Expose
    private List<StarLineCompany> starLineCompany = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public List<StarLineCompany> getStarLineCompany() {
        return starLineCompany;
    }

    public void setStarLineCompany(List<StarLineCompany> starLineCompany) {
        this.starLineCompany = starLineCompany;
    }
}
