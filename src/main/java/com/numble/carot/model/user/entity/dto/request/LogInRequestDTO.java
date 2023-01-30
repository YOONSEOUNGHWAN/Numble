package com.numble.carot.model.user.entity.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Schema(description = "로그인 요청 DTO")
@Data
public class LogInRequestDTO {
    @Schema(description = "이메일", example = "seoung59@gmail.com")
    @NotBlank(message = "필수 입력값입니다.")
    private String email;

    @Schema(description = "비밀번호", example = "ab123456")
    @NotBlank(message = "필수 입력값입니다.")
    private String pw;
}
