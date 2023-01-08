package com.numble.carot.model.user.service;

import com.numble.carot.common.aws.service.S3Service;
import com.numble.carot.common.jwt.JwtProvider;
import com.numble.carot.enums.Role;
import com.numble.carot.exception.CustomException;
import com.numble.carot.model.user.entity.dto.request.LogInRequestDTO;
import com.numble.carot.model.user.entity.dto.request.ProfileUpdateRequestDTO;
import com.numble.carot.model.user.entity.dto.request.SignUpRequestDTO;
import com.numble.carot.model.user.entity.dto.response.LogInResponseDTO;
import com.numble.carot.model.user.entity.dto.response.UserInfo;
import com.numble.carot.model.user.entity.User;
import com.numble.carot.model.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.numble.carot.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncrypt;
    private final JwtProvider jwtProvider;
    private final S3Service s3Service;
    @Transactional
    public LogInResponseDTO signUp(SignUpRequestDTO request) {
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

        return new LogInResponseDTO(user.getId(), accessToken, refreshToken, "Bearer", user.getNickName());
    }

    private void checkDuplicateUser(String email) {
        if(userRepository.findByEmail(email).isPresent()){
            throw new CustomException(ALREADY_EXIST_USER);
        }
    }

    public LogInResponseDTO logIn(LogInRequestDTO loginReq) {
        User user = userRepository.findByEmail(loginReq.getEmail()).orElseThrow(() -> new CustomException(NOT_FOUND_USER));
        if(!passwordEncrypt.matches(loginReq.getPw(), user.getPassword())){
            throw new CustomException(NO_MATCH_PASSWORD);
        }
        String accessToken = jwtProvider.createAccessToken(user.getId().toString());
        String refreshToken = jwtProvider.createRefreshToken();

        return new LogInResponseDTO(user.getId(), accessToken, refreshToken, "Bearer", user.getNickName());
    }

    @Transactional
    public UserInfo updateProfile(User user, ProfileUpdateRequestDTO req) {
        user.updateNickName(req.getNickName());
        if(!req.getThumbnail().isEmpty()){
            s3Service.uploadUserProfile(req.getThumbnail(), user);
        }
        return new UserInfo(user.getNickName(), user.getThumbnail());
    }

    public UserInfo deleteProfile(User user) {
        user.deleteThumbnail();
        return new UserInfo(user.getNickName(), user.getThumbnail());
    }
}
