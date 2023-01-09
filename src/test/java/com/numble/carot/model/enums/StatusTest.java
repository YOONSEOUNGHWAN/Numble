package com.numble.carot.model.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatusTest {

    @Test
    void test1(){
//        Status 판매중 = Status.valueOf("판매중");
//        System.out.println("판매중 = " + 판매중);
        Status 판매중1 = Status.valueOfName("판매중");
        System.out.println("판매중1 = " + 판매중1);
    }

}