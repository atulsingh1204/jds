package com.bpointer.rkofficial.Model.Response.AddFundResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Deposit {
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("deposit_amount")
    @Expose
    private String depositAmount;
    @SerializedName("transaction_number")
    @Expose
    private String transactionNumber;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("deposit_amount_id")
    @Expose
    private Integer depositAmountId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(String depositAmount) {
        this.depositAmount = depositAmount;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
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

    public Integer getDepositAmountId() {
        return depositAmountId;
    }

    public void setDepositAmountId(Integer depositAmountId) {
        this.depositAmountId = depositAmountId;
    }
}
