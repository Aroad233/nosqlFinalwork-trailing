package com.example.nosqlfinalhomework;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hello")
public class HellowController {

    @GetMapping("/say")
    public String say(){
        return "hellow world";
    }
}
