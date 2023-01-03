package com.numble.carot;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class test {

    @GetMapping("/test")
    public DTO hello(){
        return new DTO("hello");
    }

    @Data
    class DTO{
        String result;
        DTO(String result){
            this.result = result;
        }
    }


}
