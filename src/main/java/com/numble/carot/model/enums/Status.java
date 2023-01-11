package com.numble.carot.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum Status {
    ING("판매중"), DONE("거래완료"), RESERVED("예약중");

    private final String name;

    private static final Map<String, Status > BY_LABEL =
            Stream.of(values()).collect(Collectors.toMap(Status::getName, e -> e));

    public static Status valueOfName(String name) {
        return BY_LABEL.get(name);
    }
}
