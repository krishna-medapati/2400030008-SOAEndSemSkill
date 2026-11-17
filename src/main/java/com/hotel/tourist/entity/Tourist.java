package com.hotel.tourist.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tourists")
public class Tourist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String visitedDestination;
    private String contactNumber;
    private String email;

    public Tourist() {}

    public Tourist(Long id, String name, String visitedDestination, String contactNumber, String email) {
        this.id = id;
        this.name = name;
        this.visitedDestination = visitedDestination;
        this.contactNumber = contactNumber;
        this.email = email;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getVisitedDestination() { return visitedDestination; }
    public void setVisitedDestination(String visitedDestination) { this.visitedDestination = visitedDestination; }
    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
