package com.rpl.spring.learn.config;

import com.rpl.spring.learn.service.FileSystemStorageService;
import com.rpl.spring.learn.service.Service;
import com.rpl.spring.learn.service.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Configuration
public class ApplicationConfig {

    @Bean
    Service service() {
        return new Service();
    }

    @Bean
    StorageService fileSystemStorageService() throws IOException {
        Path path = Files.createTempDirectory("imagesScan");
        return new FileSystemStorageService(path.toString());
    }

    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            storageService.deleteAll();
            storageService.init();
        };
    }
}
