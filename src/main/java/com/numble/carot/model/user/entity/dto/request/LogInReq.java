package com.numble.carot.model.user.entity.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LogInReq {
    @NotBlank(message = "필수 입력값입니다.")
    private String email;
    @NotBlank(message = "필수 입력값입니다.")
    private String pw;
}
