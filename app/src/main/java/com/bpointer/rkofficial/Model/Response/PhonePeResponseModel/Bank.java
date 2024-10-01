package com.bpointer.rkofficial.Model.Response.PhonePeResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bank {
    @SerializedName("bank_id")
    @Expose
    private Integer bankId;
    @SerializedName("bank_name")
    @Expose
    private String bankName;
    @SerializedName("ifsc")
    @Expose
    private String ifsc;
    @SerializedName("account_number")
    @Expose
    private String accountNumber;
    @SerializedName("holder_name")
    @Expose
    private String holderName;
    @SerializedName("phonepay")
    @Expose
    private Long phonepay;
    @SerializedName("paytm")
    @Expose
    private Object paytm;
    @SerializedName("gpay")
    @Expose
    private Object gpay;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("is_active")
    @Expose
    private Integer isActive;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public Long getPhonepay() {
        return phonepay;
    }

    public void setPhonepay(Long phonepay) {
        this.phonepay = phonepay;
    }

    public Object getPaytm() {
        return paytm;
    }

    public void setPaytm(Object paytm) {
        this.paytm = paytm;
    }

    public Object getGpay() {
        return gpay;
    }

    public void setGpay(Object gpay) {
        this.gpay = gpay;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
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
