package com.bpointer.rkofficial.Model.Response.HomeResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Company {
    @SerializedName("company_id")
    @Expose
    private Integer companyId;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("open_time")
    @Expose
    private String openTime;
    @SerializedName("close_time")
    @Expose
    private String closeTime;
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
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("all_company_status")
    @Expose
    private Integer allCompanyStatus;
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("answers")
    @Expose
    private Object answers;

    @SerializedName("chart_url")
    @Expose
    private String chart_url;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
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

    public Integer getAllCompanyStatus() {
        return allCompanyStatus;
    }

    public void setAllCompanyStatus(Integer allCompanyStatus) {
        this.allCompanyStatus = allCompanyStatus;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Object getAnswers() {
        return answers;
    }

    public void setAnswers(Object answers) {
        this.answers = answers;
    }

    public String getChart_url() {
        return chart_url;
    }

    public void setChart_url(String chart_url) {
        this.chart_url = chart_url;
    }
}
