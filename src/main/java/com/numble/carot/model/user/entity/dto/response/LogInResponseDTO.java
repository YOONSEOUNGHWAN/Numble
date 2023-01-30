package com.numble.carot.model.user.entity.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(description = "인증정보 검증 응답 DTO")
@Data
@AllArgsConstructor
public class LogInResponseDTO {
    @Schema(description = "유저 ID", example = "1")
    private Long userId;

    @Schema(description = "access token", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjc0OTg4NTE0LCJleHAiOjE2NzUwODg1MTR9.l7f9G9qchBx5HxKai6Yo_U7G8bqg0PZNFaT7-yuO9eg")
    private String accessToken;

    @Schema(description = "refresh token", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyZWZyZXNoIiwiaWF0IjoxNjc0OTg4NTE0LCJleHAiOjE2NzUwODg1MTR9.r1iWBeKI8FuOi-jObu1djoSEcu-UzUvl-9LYE6Yu4AA")
    private String refreshToken;

    @Schema(description = "토큰 타입", example = "Bearer")
    private String tokenType;

    @Schema(description = "닉네임", example = "seoung59")
    private String nickName;

}
