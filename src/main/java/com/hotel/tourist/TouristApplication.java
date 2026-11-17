package com.hotel.tourist;

import com.hotel.tourist.entity.Tourist;
import com.hotel.tourist.repository.TouristRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TouristApplication {

    public static void main(String[] args) {
        SpringApplication.run(TouristApplication.class, args);
    }

    /** Seed two tourists so the PUT endpoint can be tested immediately (ids 1 and 2). */
    @Bean
    CommandLineRunner seed(TouristRepository repo) {
        return args -> {
            repo.save(new Tourist(null, "Ravi Kumar", "Goa", "9876543210", "ravi@example.com"));
            repo.save(new Tourist(null, "Anita Sharma", "Jaipur", "9123456780", "anita@example.com"));
        };
    }
}
