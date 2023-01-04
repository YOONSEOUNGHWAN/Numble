package com.numble.carot.model.user.dto.request;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;

@Data
public class SignUpReq {
    @NotBlank(message = "필수 입력값입니다.")
    private String email;
    @NotBlank(message = "필수 입력값입니다.")
    private String pw;
    @NotBlank(message = "필수 입력값입니다.")
    private String name;
    @NotBlank(message = "필수 입력값입니다.")
    private String phone;
    @NotBlank(message = "필수 입력값입니다.")
    private String nickName;
}
