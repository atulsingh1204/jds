package com.bpointer.rkofficial.Model.Response.GPayResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bank {
    @SerializedName("bank_id")
    @Expose
    private Integer bankId;
    @SerializedName("bank_name")
    @Expose
    private Object bankName;
    @SerializedName("ifsc")
    @Expose
    private Object ifsc;
    @SerializedName("account_number")
    @Expose
    private Object accountNumber;
    @SerializedName("holder_name")
    @Expose
    private Object holderName;
    @SerializedName("phonepay")
    @Expose
    private Object phonepay;
    @SerializedName("paytm")
    @Expose
    private String paytm;
    @SerializedName("gpay")
    @Expose
    private Long gpay;
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

    public Object getBankName() {
        return bankName;
    }

    public void setBankName(Object bankName) {
        this.bankName = bankName;
    }

    public Object getIfsc() {
        return ifsc;
    }

    public void setIfsc(Object ifsc) {
        this.ifsc = ifsc;
    }

    public Object getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Object accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Object getHolderName() {
        return holderName;
    }

    public void setHolderName(Object holderName) {
        this.holderName = holderName;
    }

    public Object getPhonepay() {
        return phonepay;
    }

    public void setPhonepay(Object phonepay) {
        this.phonepay = phonepay;
    }

    public String getPaytm() {
        return paytm;
    }

    public void setPaytm(String paytm) {
        this.paytm = paytm;
    }

    public Long getGpay() {
        return gpay;
    }

    public void setGpay(Long gpay) {
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
