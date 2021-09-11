package com.konkuk.solvedac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SolvedAcApplication {

    public static void main(String[] args) {
        SpringApplication.run(SolvedAcApplication.class, args);
    }

//    @Bean
//    public TaskScheduler taskScheduler() {
//        return new ConcurrentTaskScheduler();
//    }
}
