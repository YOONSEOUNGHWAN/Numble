//package com.numble.carot.common.security;
//
//import com.numble.carot.common.ExceptionDto;
//import com.numble.carot.exception.CustomException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//public class ExceptionHandlerFilter extends OncePerRequestFilter {
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        try{
//            filterChain.doFilter(request, response);
//        }catch (CustomException e){
//            exceptionHandler(e);
//        }catch (Exception e){
//            exception(e);
//        }
//    }
//    protected ResponseEntity exceptionHandler(CustomException e){
//        return ResponseEntity.status(e.getErrorCode().getStatus()).body(new ExceptionDto(e));
//    }
//
//    protected ResponseEntity exception(Exception e){
//        return ResponseEntity.internalServerError().body(new ExceptionDto(e));
//    }
//}
