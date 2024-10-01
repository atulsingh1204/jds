package com.bpointer.rkofficial.Model.Response.StarLineCompanyResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StarLineCompanyResponseModel {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
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

    public List<StarLineCompany> getStarLineCompany() {
        return starLineCompany;
    }

    public void setStarLineCompany(List<StarLineCompany> starLineCompany) {
        this.starLineCompany = starLineCompany;
    }
}
