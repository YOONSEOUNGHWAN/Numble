package com.numble.carot.model.user.entity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LogInResponseDTO {
    private Long userId;
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private String nickName;

}
