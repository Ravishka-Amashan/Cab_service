package com.ravishka.megacitycab.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import java.time.LocalDateTime;

@Document(collection = "bills")
@Data
public class Bill {
    
    @Id
    private String id;
    
    @Indexed(unique = true)
    private String billNumber;
    
    private String bookingId;
    
    private String customerId;
    
    private LocalDateTime billDate;
    
    private double baseFare;
    
    private double distanceCharge;
    
    private double waitingCharge;
    
    private double discount;
    
    private double tax;
    
    private double totalAmount;
    
    private String paymentMethod; // CASH, CARD, ONLINE
    
    private String paymentStatus; // PENDING, PAID
    
    private LocalDateTime paymentDate;
}
