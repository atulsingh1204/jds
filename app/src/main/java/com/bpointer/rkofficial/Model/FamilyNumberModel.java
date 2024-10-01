package com.bpointer.rkofficial.Model;

public class FamilyNumberModel {
    String number,digitName;

    public FamilyNumberModel(String number, String digitName) {
        this.number = number;
        this.digitName = digitName;
    }

    public String getNumber() {
        return number;
    }

    public String getDigitName() {
        return digitName;
    }
}
