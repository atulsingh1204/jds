package com.bpointer.rkofficial.Model;

public class CartStarLineModel {
    int user_id, company_id, game_id, time_id;
    String game_name, point, number, play_date, play_time;

    public CartStarLineModel(int user_id, int company_id, int game_id, int time_id, String game_name, String point, String number, String play_date, String play_time) {
        this.user_id = user_id;
        this.company_id = company_id;
        this.game_id = game_id;
        this.time_id = time_id;
        this.game_name = game_name;
        this.point = point;
        this.number = number;
        this.play_date = play_date;
        this.play_time = play_time;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getCompany_id() {
        return company_id;
    }

    public int getGame_id() {
        return game_id;
    }

    public int getTime_id() {
        return time_id;
    }

    public String getGame_name() {
        return game_name;
    }

    public String getPoint() {
        return point;
    }

    public String getNumber() {
        return number;
    }

    public String getPlay_date() {
        return play_date;
    }

    public String getPlay_time() {
        return play_time;
    }
}
