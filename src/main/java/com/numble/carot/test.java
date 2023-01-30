package com.numble.carot;

import com.numble.carot.exception.CustomException;
import com.numble.carot.exception.ErrorCode;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class test {

    @GetMapping("/test")
    public String hello(){
        throw new IllegalArgumentException();
    }
    @GetMapping("/test1")
    public String hello1(){
        throw new CustomException(ErrorCode.INVALID_TOKEN);
    }

    @GetMapping("/test3")
    public DTO hello4(){
        return new DTO("hi");
    }

    @Data
    class DTO{
        String result;
        DTO(String result){
            this.result = result;
        }
    }


}
