package com.numble.carot.model.user.service;

import com.numble.carot.common.jwt.JwtProvider;
import com.numble.carot.enums.Role;
import com.numble.carot.exception.CustomException;
import com.numble.carot.model.user.entity.dto.request.LogInReq;
import com.numble.carot.model.user.entity.dto.request.ProfileUpdateReq;
import com.numble.carot.model.user.entity.dto.request.SignUpReq;
import com.numble.carot.model.user.entity.dto.response.LogInInfo;
import com.numble.carot.model.user.entity.dto.response.UserInfo;
import com.numble.carot.model.user.entity.User;
import com.numble.carot.model.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

import static com.numble.carot.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
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

        return new LogInInfo(user.getId(), accessToken, refreshToken, "Bearer", user.getNickName());
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

        return new LogInInfo(user.getId(), accessToken, refreshToken, "Bearer", user.getNickName());
    }

    @Transactional
    public UserInfo updateProfile(HttpServletRequest request, ProfileUpdateReq req) {
        //여기 까지 들어왔다면 User 가 존재하는 것임. -> Spring Security
        User user = jwtProvider.getUser(request);
        user.updateNickName(req.getNickName());
        if(!req.getThumbnail().isEmpty()){
            /**
             * todo : fileUploader생성
             */
            user.updateThumbnail("url");
        }
        return new UserInfo(user.getNickName(), user.getThumbnail());
    }
}
