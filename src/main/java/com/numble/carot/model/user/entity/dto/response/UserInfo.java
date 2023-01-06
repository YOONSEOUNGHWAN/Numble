package com.numble.carot.model.user.entity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class UserInfo {
    private String nickName;
    private String thumbnail;
}
