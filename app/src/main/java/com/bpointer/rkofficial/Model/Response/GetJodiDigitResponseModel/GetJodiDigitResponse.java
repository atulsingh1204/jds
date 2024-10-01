package com.bpointer.rkofficial.Model.Response.GetJodiDigitResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetJodiDigitResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("wallet")
    @Expose
    private String wallet;
    @SerializedName("current_date")
    @Expose
    private String currentDate;
    @SerializedName("second_date")
    @Expose
    private String secondDate;
    @SerializedName("third_date")
    @Expose
    private String thirdDate;
    @SerializedName("fourth_date")
    @Expose
    private String fourthDate;

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

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getSecondDate() {
        return secondDate;
    }

    public void setSecondDate(String secondDate) {
        this.secondDate = secondDate;
    }

    public String getThirdDate() {
        return thirdDate;
    }

    public void setThirdDate(String thirdDate) {
        this.thirdDate = thirdDate;
    }

    public String getFourthDate() {
        return fourthDate;
    }

    public void setFourthDate(String fourthDate) {
        this.fourthDate = fourthDate;
    }

}
