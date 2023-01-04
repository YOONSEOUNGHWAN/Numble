package com.numble.carot.model.user.dto.response;

import com.numble.carot.model.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LogInInfo {
    private Long userId;
    private String accessToken;
    private String refreshToken;
    private String tokenType;

}
