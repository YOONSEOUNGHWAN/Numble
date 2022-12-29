package com.numble.carot.enums;

public enum Status {
    ING("판매중"), DONE("거래완료"), RESERVED("예약중");

    private final String name;

    Status(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
