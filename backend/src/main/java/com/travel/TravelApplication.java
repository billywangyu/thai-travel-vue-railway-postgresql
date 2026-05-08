package com.travel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.travel.entity")
@EnableJpaRepositories("com.travel.repository")
public class TravelApplication {
    public static void main(String[] args) { SpringApplication.run(TravelApplication.class, args); }
}
