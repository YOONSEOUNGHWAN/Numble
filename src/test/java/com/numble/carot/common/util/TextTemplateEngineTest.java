package com.numble.carot.common.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TextTemplateEngineTest {

    @Test
    void TextTemplateTest(){
        String authLinkUrl = new TextTemplateEngine.Builder()
                .argument("authLinkUrl", "http://www.naver.com")
                .build()
                .readHtmlFromResource("auth_email_content.html");
        Assertions.assertThat(authLinkUrl.trim()).isEqualTo("<h1 style=\"color: #5e9ca0;\">Numble 당근마켓</h1>\n" +
                "<h2 style=\"color: #2e6c80;\">회원가입을 축하드립니다.</h2>\n" +
                "<p>아래의 링크를 눌러 회원가입을 완료해주세요!!</p>\n" +
                "<p>Click the <a style=\"background-color: #2b2301; color: #fff; display: inline-block; padding: 3px 10px; font-weight: bold; border-radius: 5px;\" href=\"http://www.naver.com\">회원가입</a> button to sign in Numble 당근마켓.</p>\n" +
                "<p><strong>문의 : seoung59@gmail.com</strong></p>".trim());
    }

}