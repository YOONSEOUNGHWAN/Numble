package com.numble.carot.model.user.dto;

import lombok.Data;

@Data
public class SignUpDto {
    private String email;
    private String pw;
    private String name;
    private String phone;
    private String nickName;
}
