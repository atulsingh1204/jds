package com.bpointer.rkofficial.Model.Response.GameRatingResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GameRating {
    @SerializedName("game_rating_id")
    @Expose
    private Integer gameRatingId;
    @SerializedName("game_name")
    @Expose
    private String gameName;
    @SerializedName("game_rate")
    @Expose
    private String gameRate;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getGameRatingId() {
        return gameRatingId;
    }

    public void setGameRatingId(Integer gameRatingId) {
        this.gameRatingId = gameRatingId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameRate() {
        return gameRate;
    }

    public void setGameRate(String gameRate) {
        this.gameRate = gameRate;
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
