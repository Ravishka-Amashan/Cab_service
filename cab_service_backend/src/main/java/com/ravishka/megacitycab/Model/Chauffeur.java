package com.ravishka.megacitycab.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import java.time.LocalDate;

@Document(collection = "chauffeurs")
@Data
public class Chauffeur {
    
    @Id
    private String id;
    
    private String firstName;
    
    private String lastName;
    
    @Indexed(unique = true)
    private String licenseNumber;
    
    private LocalDate dateOfBirth;
    
    private String phoneNumber;
    
    private String email;
    
    private String address;
    
    private LocalDate joiningDate;
    
    private double rating;
    
    private int totalTrips;
    
    private boolean available;
    
    private String status; // ACTIVE, ON_LEAVE, INACTIVE
}
