package com.hotel.tourist.exception;

public class TouristNotFoundException extends RuntimeException {
    public TouristNotFoundException(Long id) {
        super("Tourist not found with id: " + id);
    }
}
