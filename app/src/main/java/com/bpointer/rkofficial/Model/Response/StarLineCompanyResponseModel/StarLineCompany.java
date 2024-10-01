package com.bpointer.rkofficial.Model.Response.StarLineCompanyResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StarLineCompany {
    @SerializedName("star_line_bazar_id")
    @Expose
    private Integer starLineBazarId;
    @SerializedName("star_line_company_name")
    @Expose
    private String starLineCompanyName;
    @SerializedName("sunday")
    @Expose
    private Integer sunday;
    @SerializedName("monday")
    @Expose
    private Integer monday;
    @SerializedName("tuesday")
    @Expose
    private Integer tuesday;
    @SerializedName("wednesday")
    @Expose
    private Integer wednesday;
    @SerializedName("thursday")
    @Expose
    private Integer thursday;
    @SerializedName("friday")
    @Expose
    private Integer friday;
    @SerializedName("saturday")
    @Expose
    private Integer saturday;
    @SerializedName("is_highlight")
    @Expose
    private Integer isHighlight;
    @SerializedName("is_active")
    @Expose
    private Integer isActive;
    @SerializedName("is_delete")
    @Expose
    private Integer isDelete;
    @SerializedName("is_assigned")
    @Expose
    private Integer isAssigned;
    @SerializedName("is_open")
    @Expose
    private Integer isOpen;
    @SerializedName("created_at")
    @Expose
    private Object createdAt;
    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;

    public Integer getStarLineBazarId() {
        return starLineBazarId;
    }

    public void setStarLineBazarId(Integer starLineBazarId) {
        this.starLineBazarId = starLineBazarId;
    }

    public String getStarLineCompanyName() {
        return starLineCompanyName;
    }

    public void setStarLineCompanyName(String starLineCompanyName) {
        this.starLineCompanyName = starLineCompanyName;
    }

    public Integer getSunday() {
        return sunday;
    }

    public void setSunday(Integer sunday) {
        this.sunday = sunday;
    }

    public Integer getMonday() {
        return monday;
    }

    public void setMonday(Integer monday) {
        this.monday = monday;
    }

    public Integer getTuesday() {
        return tuesday;
    }

    public void setTuesday(Integer tuesday) {
        this.tuesday = tuesday;
    }

    public Integer getWednesday() {
        return wednesday;
    }

    public void setWednesday(Integer wednesday) {
        this.wednesday = wednesday;
    }

    public Integer getThursday() {
        return thursday;
    }

    public void setThursday(Integer thursday) {
        this.thursday = thursday;
    }

    public Integer getFriday() {
        return friday;
    }

    public void setFriday(Integer friday) {
        this.friday = friday;
    }

    public Integer getSaturday() {
        return saturday;
    }

    public void setSaturday(Integer saturday) {
        this.saturday = saturday;
    }

    public Integer getIsHighlight() {
        return isHighlight;
    }

    public void setIsHighlight(Integer isHighlight) {
        this.isHighlight = isHighlight;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getIsAssigned() {
        return isAssigned;
    }

    public void setIsAssigned(Integer isAssigned) {
        this.isAssigned = isAssigned;
    }

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

    public Object getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Object createdAt) {
        this.createdAt = createdAt;
    }

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
    }
}
