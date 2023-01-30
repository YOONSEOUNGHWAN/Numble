package com.numble.carot.model.user.entity.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Schema(description = "회원가입 요청 DTO")
@Data
public class SignUpRequestDTO {
    @Schema(description = "이메일", example = "seoung59@gmail.com")
    @NotBlank(message = "필수 입력값입니다.")
    private String email;

    @Schema(description = "비밀번호", example = "ab123456")
    @NotBlank(message = "필수 입력값입니다.")
    private String pw;

    @Schema(description = "이름", example = "윤승환")
    @NotBlank(message = "필수 입력값입니다.")
    private String name;

    @Schema(description = "휴대폰 번호", example = "010-1234-5678")
    @NotBlank(message = "필수 입력값입니다.")
    private String phone;

    @Schema(description = "닉네임", example = "seoung59")
    @NotBlank(message = "필수 입력값입니다.")
    private String nickName;
}
