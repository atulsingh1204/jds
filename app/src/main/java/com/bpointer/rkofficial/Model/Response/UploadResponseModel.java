package com.bpointer.rkofficial.Model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UploadResponseModel {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("wallet_amount")
    @Expose
    private String walletAmount;

    @SerializedName("data")
    @Expose
    private Data data;

    // Getters and Setters

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

    public String getWalletAmount() {
        return walletAmount;
    }

    public void setWalletAmount(String walletAmount) {
        this.walletAmount = walletAmount;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        @SerializedName("user_id")
        @Expose
        private int userId;

        @SerializedName("deposit_amount")
        @Expose
        private String depositAmount;

        @SerializedName("payment_status")
        @Expose
        private int paymentStatus;

        @SerializedName("payment_method")
        @Expose
        private int paymentMethod;

        @SerializedName("manual_transaction_img")
        @Expose
        private String manualTransactionImg;

        @SerializedName("updated_at")
        @Expose
        private String updatedAt;

        @SerializedName("created_at")
        @Expose
        private String createdAt;

        @SerializedName("deposit_amount_id")
        @Expose
        private int depositAmountId;

        // Getters and Setters
        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getDepositAmount() {
            return depositAmount;
        }

        public void setDepositAmount(String depositAmount) {
            this.depositAmount = depositAmount;
        }

        public int getPaymentStatus() {
            return paymentStatus;
        }

        public void setPaymentStatus(int paymentStatus) {
            this.paymentStatus = paymentStatus;
        }

        public int getPaymentMethod() {
            return paymentMethod;
        }

        public void setPaymentMethod(int paymentMethod) {
            this.paymentMethod = paymentMethod;
        }

        public String getManualTransactionImg() {
            return manualTransactionImg;
        }

        public void setManualTransactionImg(String manualTransactionImg) {
            this.manualTransactionImg = manualTransactionImg;
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

        public int getDepositAmountId() {
            return depositAmountId;
        }

        public void setDepositAmountId(int depositAmountId) {
            this.depositAmountId = depositAmountId;
        }
    }
}
