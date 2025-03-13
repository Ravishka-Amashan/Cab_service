package com.ravishka.megacitycab.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import java.time.LocalDateTime;

@Document(collection = "reservations")
@Data
public class Reservation {
    
    @Id
    private String id;
    
    @Indexed(unique = true)
    private String reservationCode;
    
    private String clientId;
    
    private String cabId;
    
    private String chauffeurId;
    
    private String pickupLocation;
    
    private String dropoffLocation;
    
    private double tripDistance; 
    
    private LocalDateTime reservationTime;
    
    private LocalDateTime scheduledTime;
    
    private LocalDateTime finishTime;
    
    private String tripStatus; 
    
    private double basePrice;
    
    private double promotionAmount;
    
    private double serviceFee;
    
    private double finalCharge;
    
    private String paymentState; 

}
