package com.alibou.alibou.security.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/myUser")
@RequiredArgsConstructor
public class MyUserController {

    @GetMapping("/testStudent")
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Hi Student");
    }
}
