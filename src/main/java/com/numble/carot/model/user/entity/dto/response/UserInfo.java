package com.numble.carot.model.user.entity.dto.response;

import com.numble.carot.model.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.lang.Nullable;


@Data
@AllArgsConstructor
public class UserInfo {
    private String nickName;
    private String thumbnail;

    public UserInfo(User user){
        this.nickName = user.getNickName();
        this.thumbnail = user.getThumbnail();
    }

}
