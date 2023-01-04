package com.numble.carot.model.user.controller;

import com.numble.carot.model.user.dto.request.LogInReq;
import com.numble.carot.model.user.dto.request.SignUpReq;
import com.numble.carot.model.user.dto.response.LogInInfo;
import com.numble.carot.model.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public LogInInfo login(@RequestBody LogInReq loginReq){
        LogInInfo result = userService.logIn(loginReq);
        return result;
    }

}
