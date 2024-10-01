package com.bpointer.rkofficial.Model.Response.DepositHistoryResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DepositHistoryResponseModel {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("Deposit History")
    @Expose
    private List<DepositHistory> depositHistory = null;

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

    public List<DepositHistory> getDepositHistory() {
        return depositHistory;
    }

    public void setDepositHistory(List<DepositHistory> depositHistory) {
        this.depositHistory = depositHistory;
    }

}
