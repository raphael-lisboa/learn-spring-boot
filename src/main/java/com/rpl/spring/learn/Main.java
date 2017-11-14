package com.rpl.spring.learn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EnableAutoConfiguration
public class Main {

    public static void main(String[] args) throws Exception {
        final ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
        context.registerShutdownHook();
    }

}