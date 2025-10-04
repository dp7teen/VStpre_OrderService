package com.dp.vstore_orderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class VStoreOrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(VStoreOrderServiceApplication.class, args);
    }

}
