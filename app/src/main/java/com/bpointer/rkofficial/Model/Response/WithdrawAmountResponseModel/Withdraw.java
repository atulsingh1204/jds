package com.bpointer.rkofficial.Model.Response.WithdrawAmountResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Withdraw {
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("withdraw_amount")
    @Expose
    private String withdrawAmount;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("withdraw_amount_id")
    @Expose
    private Integer withdrawAmountId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getWithdrawAmount() {
        return withdrawAmount;
    }

    public void setWithdrawAmount(String withdrawAmount) {
        this.withdrawAmount = withdrawAmount;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getWithdrawAmountId() {
        return withdrawAmountId;
    }

    public void setWithdrawAmountId(Integer withdrawAmountId) {
        this.withdrawAmountId = withdrawAmountId;
    }
}
