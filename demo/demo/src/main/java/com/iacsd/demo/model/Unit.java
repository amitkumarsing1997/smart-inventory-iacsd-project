package com.iacsd.demo.model;

public enum Unit {
    QUINTAL("quintal"), KILOGRAM("kg"), GRAM("gram"), COUNT("count");

    private String val;

    Unit(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }
}
