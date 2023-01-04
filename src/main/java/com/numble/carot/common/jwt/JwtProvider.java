package com.numble.carot.common.jwt;

import com.numble.carot.exception.InvalidTokenException;
import com.numble.carot.model.user.dto.SignUpDTO;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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


    public String createAccessToken(String payload){
        return createToken(payload, accessTokenValidityInMilliseconds);
    }

    public String createRefreshToken(){
        return createToken("refresh", refreshTokenValidityInMilliseconds);
    }


    private String createToken(String payload, long expireLength) {
        Claims claims = Jwts.claims().setSubject(payload); //"sub" : "data"
        Date now = new Date();
        Date validity = new Date(now.getTime() + expireLength);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }


    private String getPayload(String token){
        try{
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject(); //get("sub")
        }catch (ExpiredJwtException e){
            return e.getClaims().getSubject(); //만료된 경우 별도의 에러처리를 하지 않는다.
        }catch (JwtException e){
            throw new InvalidTokenException("유효하지 않은 토큰입니다.");
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
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);
            return claimsJws.getBody().getExpiration().before(new Date());
        }catch (JwtException | IllegalArgumentException exception){
            return false;
        }
    }

    public Authentication getAuthentication(String token){
        UserDetails userDetails = customJwtUserDetailsService.loadUserByUsername(getPayload(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "",userDetails.getAuthorities());
    }

    public String createEmailSignUpToken(SignUpDTO userData){
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
