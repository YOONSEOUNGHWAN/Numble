package com.numble.carot.model.user.controller;

import com.numble.carot.common.swagger.SwaggerConfig;
import com.numble.carot.model.user.entity.User;
import com.numble.carot.model.user.entity.dto.request.LogInRequestDTO;
import com.numble.carot.model.user.entity.dto.request.ProfileUpdateRequestDTO;
import com.numble.carot.model.user.entity.dto.request.SignUpRequestDTO;
import com.numble.carot.model.user.entity.dto.response.LogInResponseDTO;
import com.numble.carot.model.user.entity.dto.response.UserInfo;
import com.numble.carot.model.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "User", description = "사용자 API")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "회원가입", description = "회원가입 API")
    @PostMapping("/signup")
    public LogInResponseDTO signUp(@Valid @RequestBody SignUpRequestDTO request) {
        LogInResponseDTO result = userService.signUp(request);
        System.out.println(result);
        return result;
    }

    @Operation(summary = "로그인", description = "로그인 API")
    @PostMapping("/login")
    public LogInResponseDTO login(@Valid @RequestBody LogInRequestDTO loginReq) {
        LogInResponseDTO result = userService.logIn(loginReq);
        return result;
    }

    @Operation(summary = "프로필 수정", description = "프로필 수정 API")
    @SecurityRequirement(name = SwaggerConfig.AUTHENTICATION)
    @PatchMapping(value = "/profile", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public UserInfo updateProfile(Authentication authentication, @Valid @ModelAttribute ProfileUpdateRequestDTO req) {
        Object principal = authentication.getPrincipal();
        UserInfo result = userService.updateProfile((User) principal, req);
        return result;
    }

    @Operation(summary = "프로필 사진 삭제", description = "프로필 사진 삭제 API")
    @SecurityRequirement(name = SwaggerConfig.AUTHENTICATION)
    @DeleteMapping("/profile")
    public UserInfo deleteProfile(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        return userService.deleteProfile((User) principal);
    }

}
