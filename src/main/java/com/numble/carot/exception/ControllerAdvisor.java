package com.numble.carot.exception;

import com.numble.carot.common.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvisor {
    @ExceptionHandler(value = {CustomException.class})
    protected ResponseEntity<ExceptionDto> exceptionHandler(CustomException e) {
        return ResponseEntity.status(e.errorCode.getStatus()).body(new ExceptionDto(e));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ExceptionDto> methodValidException(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        FieldError fieldError = bindingResult.getFieldError();
        if (fieldError != null) {
            ErrorCode code = ErrorCode.ILLEGAL_ARGUMENT;
            ExceptionDto dto = new ExceptionDto(code.getStatus(), code.name(), fieldError.getDefaultMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dto);
        }
        return exception(e);
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<ExceptionDto> exception(Exception e) {
        return ResponseEntity.internalServerError().body(new ExceptionDto(e));
    }
}
