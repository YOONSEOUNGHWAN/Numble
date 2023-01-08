package com.numble.carot.model.user.controller;

import com.numble.carot.model.user.entity.User;
import com.numble.carot.model.user.entity.dto.request.LogInReq;
import com.numble.carot.model.user.entity.dto.request.ProfileUpdateReq;
import com.numble.carot.model.user.entity.dto.request.SignUpReq;
import com.numble.carot.model.user.entity.dto.response.LogInInfo;
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
    public LogInInfo signUp(@Valid @RequestBody SignUpReq request){
        LogInInfo result = userService.signUp(request);
        return result;
    }

    @PostMapping("/login")
    public LogInInfo login(@Valid @RequestBody LogInReq loginReq){
        LogInInfo result = userService.logIn(loginReq);
        return result;
    }

    @PostMapping("/profile")
    public UserInfo updateProfile(Authentication authentication, @Valid @RequestBody ProfileUpdateReq req){
        Object principal = authentication.getPrincipal();
        UserInfo result = userService.updateProfile((User) principal, req);
        return result;
    }

    @DeleteMapping("/profile")
    public UserInfo deleteProfile(Authentication authentication){
        Object principal = authentication.getPrincipal();
        return userService.deleteProfile((User) principal);
    }

}
