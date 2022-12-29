package com.numble.carot.model.enums;

public enum Category {
    DIGITAL("디지털기기"), LIFE_APPLIANCE("생활가전"), FURNITURE_INTERIOR("가구/인테리어"),
    CHILD_GOODS("유아용품"), LIFE_FOOD("생활/가공식품"), CHILD_BOOK("유아도서"), WOMEN_DRESS("여성의류"),
    MEN_DRESS_ETC("남성패션/잡화"), GAME_HOBBY("게임/취미"), BEAUTY("뷰티/미용"), ANIMAL("반려동물용품"),
    BOOK_TICKET_MUSIC("도서/티켓/음반"), ETC("기타중고물품"), CAR("중고차");

    Category(String name){
        this.name = name;
    }

    private final String name;

    public String getName() {
        return name;
    }
}
