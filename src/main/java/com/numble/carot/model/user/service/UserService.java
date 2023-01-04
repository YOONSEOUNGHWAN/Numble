package com.numble.carot.model.user.service;

import com.numble.carot.common.jwt.JwtProvider;
import com.numble.carot.enums.Role;
import com.numble.carot.exception.CustomException;
import com.numble.carot.exception.ErrorCode;
import com.numble.carot.model.user.dto.request.LogInReq;
import com.numble.carot.model.user.dto.request.SignUpReq;
import com.numble.carot.model.user.dto.response.LogInInfo;
import com.numble.carot.model.user.entity.User;
import com.numble.carot.model.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.numble.carot.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncrypt;
    private final JwtProvider jwtProvider;
    @Transactional
    public LogInInfo signUp(SignUpReq request) {
        //email 검증 -> phone 은 변동성
        checkDuplicateUser(request.getEmail());

        String encode = passwordEncrypt.encode(request.getPw());
        request.setPw(encode);

        User user = userRepository.save(User.builder()
                .email(request.getEmail())
                .password(request.getPw())
                .userRole(Role.USER)
                .name(request.getName())
                .nickName(request.getNickName())
                .phoneNumber(request.getPhone())
                .build());

        String accessToken = jwtProvider.createAccessToken(user.getId().toString());
        String refreshToken = jwtProvider.createRefreshToken();

        return new LogInInfo(user.getId(), accessToken, refreshToken, "Bearer");
    }

    private void checkDuplicateUser(String email) {
        if(userRepository.findByEmail(email).isPresent()){
            throw new CustomException(ALREADY_EXIST_USER);
        }
    }

    public LogInInfo logIn(LogInReq loginReq) {
        User user = userRepository.findByEmail(loginReq.getEmail()).orElseThrow(() -> new CustomException(INVALID_EMAIL));
        if(!passwordEncrypt.matches(loginReq.getPw(), user.getPassword())){
            throw new CustomException(NO_MATCH_PASSWORD);
        }
        String accessToken = jwtProvider.createAccessToken(user.getId().toString());
        String refreshToken = jwtProvider.createRefreshToken();

        return new LogInInfo(user.getId(), accessToken, refreshToken, "Bearer");
    }
}
