package com.numble.carot.common.jwt;

import com.numble.carot.exception.CustomException;
import com.numble.carot.exception.ErrorCode;
import com.numble.carot.model.user.entity.User;
import com.numble.carot.model.user.entity.dto.request.SignUpRequestDTO;
import com.numble.carot.model.user.repository.UserRepository;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component // component scan 대상 bean등록
@Slf4j
@RequiredArgsConstructor
public class JwtProvider {
    private final CustomJwtUserDetailsService customJwtUserDetailsService;

    @Value("${jwt.access-token.expire-length}")
    private long accessTokenValidityInMilliseconds;
    @Value("${jwt.refresh-token.expire-length}")
    private long refreshTokenValidityInMilliseconds;
    @Value("${jwt.token.secret-key}")
    private String secretKey;

    private final UserRepository userRepository;


    public String createAccessToken(String payload){
        return createToken(payload, accessTokenValidityInMilliseconds);
    }

    public String createRefreshToken(){
        return createToken("refresh", refreshTokenValidityInMilliseconds);
    }


    private String createToken(String payload, long expireLength) {
        Claims claims = Jwts.claims().setSubject(payload); //"sub" : "data"
        Date now = new Date();
        System.out.println("now = " + now);
        Date validity = new Date(now.getTime() + expireLength);
        System.out.println("validity = " + validity);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .compact();
    }


    private String getPayload(String token){
        try{
            return Jwts.parser()
                    .setSigningKey(secretKey.getBytes())
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject(); //get("sub")
        }catch (ExpiredJwtException e){
            return e.getClaims().getSubject(); //만료된 경우 별도의 에러처리를 하지 않는다.
        }catch (JwtException e){
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
    }

    public String resolveToken(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        if(StringUtils.hasText(header) && header.startsWith("Bearer ")){
            return header.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token){
        try{
            Claims body = Jwts.parser()
                    .setSigningKey(secretKey.getBytes())
                    .parseClaimsJws(token).getBody();
            return !body.getExpiration().before(new Date());
        }catch (JwtException | IllegalArgumentException exception){
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
    }

    public Authentication getAuthentication(String token){
        CustomJwtUserDetails userDetails = customJwtUserDetailsService.loadUserByUsername(getPayload(token));
        return new UsernamePasswordAuthenticationToken(userDetails.getUser(), "",userDetails.getAuthorities());
    }



    public String createEmailSignUpToken(SignUpRequestDTO userData){
        Claims claims = Jwts.claims();
        claims.put("userData", userData);

        Date now = new Date();
        Date validity = new Date(now.getTime() + 3600);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }


}
