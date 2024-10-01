package com.bpointer.rkofficial.Model.Response.StarLineMarketTimeResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StarLineCompany {
    @SerializedName("star_line_time_id")
    @Expose
    private Integer starLineTimeId;
    @SerializedName("star_line_time")
    @Expose
    private String starLineTime;
    @SerializedName("is_active")
    @Expose
    private Integer isActive;
    @SerializedName("star_line_bazar_id")
    @Expose
    private Integer starLineBazarId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("answers")
    @Expose
    private Object answers;

    public Integer getStarLineTimeId() {
        return starLineTimeId;
    }

    public void setStarLineTimeId(Integer starLineTimeId) {
        this.starLineTimeId = starLineTimeId;
    }

    public String getStarLineTime() {
        return starLineTime;
    }

    public void setStarLineTime(String starLineTime) {
        this.starLineTime = starLineTime;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Integer getStarLineBazarId() {
        return starLineBazarId;
    }

    public void setStarLineBazarId(Integer starLineBazarId) {
        this.starLineBazarId = starLineBazarId;
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
}
