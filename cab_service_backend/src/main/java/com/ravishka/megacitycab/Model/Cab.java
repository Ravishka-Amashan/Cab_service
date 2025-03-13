package com.ravishka.megacitycab.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Document(collection = "cabs")
@Data
public class Cab {
    
    @Id
    private String id;
    
    @Indexed(unique = true)
    private String registrationNumber;
    
    private String model;
    
    private String color;
    
    private int year;
    
    private String type; // SEDAN, SUV, LUXURY
    
    private int seatingCapacity;
    
    private double ratePerKm;
    
    private boolean available;
    
    private String currentLocation;
    
    private String status; // ACTIVE, MAINTENANCE, INACTIVE
}
