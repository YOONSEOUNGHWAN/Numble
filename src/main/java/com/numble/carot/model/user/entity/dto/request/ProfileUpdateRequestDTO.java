package com.numble.carot.model.user.entity.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Schema(description = "프로필 수정 요청 DTO")
@Data
public class ProfileUpdateRequestDTO {
    @Schema(description = "닉네임", example = "seoung59")
    @NotBlank(message = "필수 입력 값 입니다.")
    private String nickName;

    @Schema(description = "프로필 사진")
    private MultipartFile thumbnail;
}
