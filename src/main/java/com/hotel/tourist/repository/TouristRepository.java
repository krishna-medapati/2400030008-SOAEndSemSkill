package com.hotel.tourist.repository;

import com.hotel.tourist.entity.Tourist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TouristRepository extends JpaRepository<Tourist, Long> {
}
