package com.bpointer.rkofficial.Model.Response.WinHistoryResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WinHistoryResponseModel {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("winner_status")
    @Expose
    private String winnerStatus;
    @SerializedName("Winner History")
    @Expose
    private List<WinnerHistory> winnerHistory = null;

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

    public String getWinnerStatus() {
        return winnerStatus;
    }

    public void setWinnerStatus(String winnerStatus) {
        this.winnerStatus = winnerStatus;
    }

    public List<WinnerHistory> getWinnerHistory() {
        return winnerHistory;
    }

    public void setWinnerHistory(List<WinnerHistory> winnerHistory) {
        this.winnerHistory = winnerHistory;
    }

}
