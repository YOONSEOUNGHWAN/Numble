package com.numble.carot.model.user.entity.dto.response;

import com.numble.carot.model.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;


@Schema(description = "유저 정보 DTO")
@Data
@AllArgsConstructor
public class UserInfo {
    @Schema(description = "닉네임", example = "seoung59")
    private String nickName;

    @Schema(description = "프로필 사진 URL", example = "https://111.222.333.444/p1.png")
    private String thumbnail;

    public UserInfo(User user){
        this.nickName = user.getNickName();
        this.thumbnail = user.getThumbnail();
    }

}
