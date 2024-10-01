package com.bpointer.rkofficial.Model.Response.WithdrawHistoryResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WithdrawHistory {
    @SerializedName("withdraw_amount_id")
    @Expose
    private Integer withdrawAmountId;
    @SerializedName("withdraw_amount")
    @Expose
    private String withdrawAmount;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("is_accept")
    @Expose
    private Integer isAccept;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getWithdrawAmountId() {
        return withdrawAmountId;
    }

    public void setWithdrawAmountId(Integer withdrawAmountId) {
        this.withdrawAmountId = withdrawAmountId;
    }

    public String getWithdrawAmount() {
        return withdrawAmount;
    }

    public void setWithdrawAmount(String withdrawAmount) {
        this.withdrawAmount = withdrawAmount;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getIsAccept() {
        return isAccept;
    }

    public void setIsAccept(Integer isAccept) {
        this.isAccept = isAccept;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
