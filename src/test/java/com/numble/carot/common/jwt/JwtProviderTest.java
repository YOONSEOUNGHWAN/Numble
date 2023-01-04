package com.numble.carot.common.jwt;

import com.numble.carot.model.user.dto.SignUpDto;
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
        SignUpDto signUpDTO = new SignUpDto();
        signUpDTO.setEmail("seoung59");

        String emailSignInToken = jwtProvider.createEmailSignUpToken(signUpDTO);
        System.out.println("emailSignInToken = " + emailSignInToken);
    }

}