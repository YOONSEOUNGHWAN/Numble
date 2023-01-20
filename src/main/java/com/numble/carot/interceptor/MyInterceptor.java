package com.numble.carot.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.numble.carot.common.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequiredArgsConstructor
@Component
public class MyInterceptor implements HandlerInterceptor {
    private final ObjectMapper objectMapper;

    //복사본 request, response 들어옴.
    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object object,
            Exception ex
    ) throws Exception {
        //wrapping 된 Response 가 넘어 올 것이다.
        final ContentCachingResponseWrapper cachingResponse = (ContentCachingResponseWrapper) response;
        //200번대 응답이 아닌 경우 Interceptor 를 거치지 않는다.
        if(!String.valueOf(response.getStatus()).startsWith("2")){
            return;
        }
        if (cachingResponse.getContentType() != null && (cachingResponse.getContentType().contains("application/json"))) {
            if (cachingResponse.getContentAsByteArray().length != 0) {
                //response catch
//                InputStream contentInputStream = cachingResponse.getContentInputStream();
//                byte[] contentAsByteArray = cachingResponse.getContentAsByteArray();
                //String 변환
                String body = new String(cachingResponse.getContentAsByteArray());
                //Object형식으로 변환 -> Response에 꽂아주기 위함.
                Object data = objectMapper.readValue(body, Object.class);
                //ResponseEntity 생성
                ResponseDto<Object> objectResponseDto = new ResponseDto<>(data);
                //String 변환.
                String wrappedBody = objectMapper.writeValueAsString(objectResponseDto);
                //비우고
                cachingResponse.resetBuffer();
                //응답값 교체.
                cachingResponse.getOutputStream().write(wrappedBody.getBytes(), 0, wrappedBody.getBytes().length);
                log.info("Response Body : {}", wrappedBody);
            }
        }
    }
}