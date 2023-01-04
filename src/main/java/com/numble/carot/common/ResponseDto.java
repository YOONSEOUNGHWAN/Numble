package com.numble.carot.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;

@AllArgsConstructor
@Getter
public class ResponseDto<T> {
    private final Boolean success;
    private final T data;

    public ResponseDto(@Nullable T data) {
        this.success = true;
        this.data = data;
    }
}