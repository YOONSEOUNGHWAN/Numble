package com.numble.carot.common.jwt;

import com.numble.carot.model.user.entity.dto.request.SignUpRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JwtProviderTest {
    private final JwtProvider jwtProvider;

    @Autowired
    JwtProviderTest(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Test
    void emailToken(){
        SignUpRequestDTO signUpRequestDTO = new SignUpRequestDTO();
        signUpRequestDTO.setEmail("seoung59");

        String emailSignInToken = jwtProvider.createEmailSignUpToken(signUpRequestDTO);
        System.out.println("emailSignInToken = " + emailSignInToken);
    }

}