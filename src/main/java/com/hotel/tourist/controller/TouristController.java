package com.hotel.tourist.controller;

import com.hotel.tourist.entity.Tourist;
import com.hotel.tourist.security.TokenValidator;
import com.hotel.tourist.service.TouristService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tourists")
public class TouristController {

    private final TouristService service;
    private final TokenValidator tokenValidator;

    public TouristController(TouristService service, TokenValidator tokenValidator) {
        this.service = service;
        this.tokenValidator = tokenValidator;
    }

    @Operation(summary = "List all tourists (helper endpoint to verify updates)")
    @GetMapping
    public List<Tourist> getAll() {
        return service.findAll();
    }

    @Operation(summary = "Get a tourist by id")
    @GetMapping("/{id}")
    public Tourist getOne(@PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(summary = "Update tourist (visited destination / contact details) - requires Token header")
    @PutMapping("/{id}")
    public ResponseEntity<String> update(
            @PathVariable Long id,
            @Parameter(description = "Pre-generated request token", example = "HOTEL-CHECKIN-TOKEN-12345")
            @RequestHeader(value = "Token", required = false) String token,
            @RequestBody Tourist tourist) {

        // Step 1: token is verified BEFORE any database read/write happens
        tokenValidator.validate(token);

        // Step 2: fetch -> modify -> save
        service.update(id, tourist);
        return ResponseEntity.ok("Tourist Updated Successfully");
    }
}
