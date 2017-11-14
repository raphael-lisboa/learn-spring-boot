package com.rpl.spring.learn.config;

import com.rpl.spring.learn.service.Service;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    Service service() {
        return new Service();
    }

}
