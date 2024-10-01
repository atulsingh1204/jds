
package com.bpointer.rkofficial.Model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentOption {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("payment_id")
    @Expose
    private String paymentId;
    @SerializedName("is_active")
    @Expose
    private int isActive;

    @SerializedName("payee_name")
    @Expose
    private String payeeName;

    public String getPayeeName() {
        return payeeName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

}
