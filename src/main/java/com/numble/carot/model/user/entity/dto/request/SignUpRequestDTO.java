package com.numble.carot.model.user.entity.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SignUpRequestDTO {
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
