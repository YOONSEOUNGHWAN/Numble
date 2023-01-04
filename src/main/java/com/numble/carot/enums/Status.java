package com.numble.carot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    ING("판매중"), DONE("거래완료"), RESERVED("예약중");

    private final String name;
}
