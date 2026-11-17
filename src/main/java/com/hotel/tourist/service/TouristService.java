package com.hotel.tourist.service;

import com.hotel.tourist.entity.Tourist;
import com.hotel.tourist.exception.TouristNotFoundException;
import com.hotel.tourist.repository.TouristRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TouristService {

    private final TouristRepository repository;

    public TouristService(TouristRepository repository) {
        this.repository = repository;
    }

    public List<Tourist> findAll() {
        return repository.findAll();
    }

    public Tourist findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new TouristNotFoundException(id));
    }

    /** Fetch existing record -> modify fields -> save back. */
    public Tourist update(Long id, Tourist updated) {
        Tourist existing = findById(id);
        if (updated.getName() != null)               existing.setName(updated.getName());
        if (updated.getVisitedDestination() != null) existing.setVisitedDestination(updated.getVisitedDestination());
        if (updated.getContactNumber() != null)      existing.setContactNumber(updated.getContactNumber());
        if (updated.getEmail() != null)              existing.setEmail(updated.getEmail());
        return repository.save(existing);
    }
}
