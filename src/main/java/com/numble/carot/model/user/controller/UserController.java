package com.numble.carot.model.user.controller;

import com.numble.carot.model.user.entity.User;
import com.numble.carot.model.user.entity.dto.request.LogInRequestDTO;
import com.numble.carot.model.user.entity.dto.request.ProfileUpdateRequestDTO;
import com.numble.carot.model.user.entity.dto.request.SignUpRequestDTO;
import com.numble.carot.model.user.entity.dto.response.LogInResponseDTO;
import com.numble.carot.model.user.entity.dto.response.UserInfo;
import com.numble.carot.model.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public LogInResponseDTO signUp(@Valid @RequestBody SignUpRequestDTO request){
        LogInResponseDTO result = userService.signUp(request);
        return result;
    }

    @PostMapping("/login")
    public LogInResponseDTO login(@Valid @RequestBody LogInRequestDTO loginReq){
        LogInResponseDTO result = userService.logIn(loginReq);
        return result;
    }

    @PatchMapping("/profile")
    public UserInfo updateProfile(Authentication authentication, @Valid @ModelAttribute ProfileUpdateRequestDTO req){
        Object principal = authentication.getPrincipal();
        UserInfo result = userService.updateProfile((User)principal, req);
        return result;
    }

    @DeleteMapping("/profile")
    public UserInfo deleteProfile(Authentication authentication){
        Object principal = authentication.getPrincipal();
        return userService.deleteProfile((User) principal);
    }

}
