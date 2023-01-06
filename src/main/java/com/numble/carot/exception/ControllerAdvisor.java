package com.numble.carot.exception;

import com.numble.carot.common.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvisor {
    @ExceptionHandler(value = {CustomException.class})
    protected ResponseEntity exceptionHandler(CustomException e){
        return ResponseEntity.status(e.errorCode.getStatus()).body(new ExceptionDto(e));
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity exception(Exception e){
        return ResponseEntity.internalServerError().body(new ExceptionDto(e));
    }
}
