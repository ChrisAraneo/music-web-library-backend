package com.chrisaraneo.mwl.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chrisaraneo.mwl.model.extended.EmptyJson;
import com.chrisaraneo.mwl.security.RequiresCaptcha;

@RestController
@RequestMapping("/")
public class IndexController {

    @GetMapping
    public String getIndex() {
        return "Index";
    }
    
    @PostMapping
    @RequiresCaptcha
    public Object postIndex(){
        return new EmptyJson();
    }
    
}