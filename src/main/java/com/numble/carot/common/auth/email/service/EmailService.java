package com.numble.carot.common.auth.email.service;

import com.numble.carot.common.auth.email.message.EmailMessage;
import com.numble.carot.common.jwt.JwtProvider;
import com.numble.carot.common.util.TextTemplateEngine;
import com.numble.carot.exception.CustomException;
import com.numble.carot.exception.ErrorCode;
import com.numble.carot.model.user.entity.dto.request.SignUpRequestDTO;
import com.numble.carot.model.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.regex.Pattern;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;

    private final Pattern ALLOWED_EMAIL_PATTER = Pattern.compile("^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$");

    private void checkDuplicateSignUp(String email){
        if(!ALLOWED_EMAIL_PATTER.matcher(email).matches()){
            throw new CustomException(ErrorCode.INVALID_EMAIL);
        }
    }

    private String makeEmailTemplate(SignUpRequestDTO userData){
        String signUpToken = jwtProvider.createEmailSignUpToken(userData);
        String url = String.format("http://www.numble.site?token=%s", signUpToken);
        String text = new TextTemplateEngine.Builder()
                .argument("authLinkUrl", url)
                .build()
                .readHtmlFromResource("auth_email_content.html");
        return text;
    }

    private void send(EmailMessage message) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
        mimeMessageHelper.setTo(message.getTo());
        mimeMessageHelper.setSubject(message.getSubject());
        mimeMessageHelper.setText(message.getMessage(), true);
        javaMailSender.send(mimeMessage);
        log.info("sent Email : {}", message.getTo());
    }

}
