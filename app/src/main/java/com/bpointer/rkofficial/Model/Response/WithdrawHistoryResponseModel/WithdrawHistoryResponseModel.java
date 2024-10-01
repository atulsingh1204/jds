package com.bpointer.rkofficial.Model.Response.WithdrawHistoryResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WithdrawHistoryResponseModel {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("Withdraw History")
    @Expose
    private List<WithdrawHistory> withdrawHistory = null;

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

    public List<WithdrawHistory> getWithdrawHistory() {
        return withdrawHistory;
    }

    public void setWithdrawHistory(List<WithdrawHistory> withdrawHistory) {
        this.withdrawHistory = withdrawHistory;
    }
}
