package com.bpointer.rkofficial.Model.Response.GameRatingResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GameRatingResponseModel {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("game_rating")
    @Expose
    private List<GameRating> gameRating = null;

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

    public List<GameRating> getGameRating() {
        return gameRating;
    }

    public void setGameRating(List<GameRating> gameRating) {
        this.gameRating = gameRating;
    }
}
