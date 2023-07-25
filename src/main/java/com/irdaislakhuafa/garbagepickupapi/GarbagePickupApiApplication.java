package com.irdaislakhuafa.garbagepickupapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GarbagePickupApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(GarbagePickupApiApplication.class, args);
    }

}