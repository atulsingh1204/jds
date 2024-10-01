package com.bpointer.rkofficial.Model;

public class NumbersModel {
    String spn_id,num,msg;

    public NumbersModel(String spn_id, String num, String msg) {
        this.spn_id = spn_id;
        this.num = num;
        this.msg = msg;
    }

    public String getSpn_id() {
        return spn_id;
    }

    public String getNum() {
        return num;
    }

    public String getMsg() {
        return msg;
    }
}
