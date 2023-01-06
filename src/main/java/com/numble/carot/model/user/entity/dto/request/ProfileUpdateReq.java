package com.numble.carot.model.user.entity.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Data
public class ProfileUpdateReq {
    @NotBlank(message = "필수 입력 값 입니다.")
    private String nickName;
    private MultipartFile thumbnail;
}
