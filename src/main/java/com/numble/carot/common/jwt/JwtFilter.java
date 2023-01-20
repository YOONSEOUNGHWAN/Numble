package com.numble.carot.common.jwt;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//SpringSecurity JWT filter 적용
// redirect logic 이 들어갈 경우 GenericFilter -> OncePerRequestFilter
@RequiredArgsConstructor
public class JwtFilter extends GenericFilter {
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
        //wrapping(copy) -> response 덮어씌우기.
        wrappingResponse.copyBodyToResponse();
    }

//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        System.out.println("한번만 실행 되는거 맞지 않아..?");
//        ContentCachingRequestWrapper wrappingRequest = new ContentCachingRequestWrapper(request);
//        ContentCachingResponseWrapper wrappingResponse = new ContentCachingResponseWrapper(response);
//        //response 사라짐. header는 안 사라짐.
//        String token = jwtProvider.resolveToken(request);
//        //증명 끝. 유효성 검사만 들어감.
//        if(token != null && jwtProvider.validateToken(token)){
//            Authentication authentication = jwtProvider.getAuthentication(token);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
//        filterChain.doFilter(wrappingRequest, wrappingResponse);
//        //response 는 읽으면 없어진다..
//        //wrapping(copy) -> response 덮어씌우기.
//        wrappingResponse.copyBodyToResponse();
//    }

}
