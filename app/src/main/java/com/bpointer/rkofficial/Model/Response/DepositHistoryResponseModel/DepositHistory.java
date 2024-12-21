package com.bpointer.rkofficial.Model.Response.DepositHistoryResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DepositHistory {
    @SerializedName("deposit_amount_id")
    @Expose
    private Integer depositAmountId;
    @SerializedName("deposit_amount")
    @Expose
    private String depositAmount;
    @SerializedName("transaction_number")
    @Expose
    private String transactionNumber;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    @SerializedName("payment_method")
    @Expose
    private String payment_method;

    @SerializedName("payment_status")
    @Expose
    private String payment_status;

    @SerializedName("payment_transaction_id")
    @Expose
    private String payment_transaction_id;

    @SerializedName("manual_transaction_img")
    @Expose
    private String manual_transaction_img;



    public Integer getDepositAmountId() {
        return depositAmountId;
    }

    public void setDepositAmountId(Integer depositAmountId) {
        this.depositAmountId = depositAmountId;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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


    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public String getPayment_transaction_id() {
        return payment_transaction_id;
    }

    public void setPayment_transaction_id(String payment_transaction_id) {
        this.payment_transaction_id = payment_transaction_id;
    }

    public String getManual_transaction_img() {
        return manual_transaction_img;
    }

    public void setManual_transaction_img(String manual_transaction_img) {
        this.manual_transaction_img = manual_transaction_img;
    }
}
