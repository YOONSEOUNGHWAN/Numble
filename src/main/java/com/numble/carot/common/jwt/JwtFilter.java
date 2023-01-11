package com.numble.carot.common.jwt;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//SpringSecurity JWT filter 적용
@RequiredArgsConstructor
public class JwtFilter extends GenericFilter{
    private final JwtProvider jwtProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ContentCachingRequestWrapper wrappingRequest = new ContentCachingRequestWrapper((HttpServletRequest) request);
        ContentCachingResponseWrapper wrappingResponse = new ContentCachingResponseWrapper((HttpServletResponse) response);
        //response 사라짐. header는 안 사라짐.
        String token = jwtProvider.resolveToken((HttpServletRequest) request);
        //증명 끝. 유효성 검사만 들어감.
        if(token != null && jwtProvider.validateToken(token)){
            Authentication authentication = jwtProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(wrappingRequest, wrappingResponse);
        //response 는 읽으면 없어진다..
        //wrapping(copy) -> response 덮어씌우기.
        wrappingResponse.copyBodyToResponse();
    }

}
