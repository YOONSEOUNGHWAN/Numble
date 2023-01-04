package com.numble.carot.common;

import com.numble.carot.exception.CustomException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Builder
@Getter
public class ExceptionDto {
    private final Boolean success;
    private final HttpStatus status;
    private final String code;
    private final String message;

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
