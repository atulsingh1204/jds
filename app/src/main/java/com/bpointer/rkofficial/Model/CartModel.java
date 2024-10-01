package com.bpointer.rkofficial.Model;

public class CartModel {
    int user_id, company_id, game_id;
    String game_name, game_type, point, number, play_date, play_time;

    public CartModel(int user_id, int company_id, int game_id, String game_name, String game_type, String point, String number, String play_date, String play_time) {
        this.user_id = user_id;
        this.company_id = company_id;
        this.game_id = game_id;
        this.game_name = game_name;
        this.game_type = game_type;
        this.point = point;
        this.number = number;
        this.play_date = play_date;
        this.play_time = play_time;
    }

    public void setPlay_time(String play_time) {
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

    public String getGame_name() {
        return game_name;
    }

    public String getGame_type() {
        return game_type;
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
