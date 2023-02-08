package com.numble.carot.common;

import com.numble.carot.exception.CustomException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ExceptionDto {
    private final boolean success;
    private final HttpStatus status;
    private final String code;
    private final String message;

    public ExceptionDto(HttpStatus status, String code, String message) {
        this.success = false;
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public ExceptionDto(CustomException e){
        this.success = false;
        this.status = e.getErrorCode().getStatus();
        this.code = e.getErrorCode().name();
        this.message = e.getErrorCode().getMessage();
    }

    public ExceptionDto(Exception e){
        this.success = false;
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
        this.code = e.getClass().getName();
        this.message = e.getMessage();
    }
}
