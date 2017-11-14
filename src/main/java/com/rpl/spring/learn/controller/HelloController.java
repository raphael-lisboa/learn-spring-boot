package com.rpl.spring.learn.controller;

import com.rpl.spring.learn.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private final Service service;

    @Autowired
    public HelloController(Service service) {
        this.service = service;
    }

    @RequestMapping("/")
    @ResponseBody
    public String home() {
        return service.msg();
    }

}
