package com.ravishka.megacitycab.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "accounts")
@Data
public class Account {
    
    @Id
    private String id;
    
    @Indexed(unique = true)
    private String username;
    
    private String password;
    
    @Indexed(unique = true)
    private String email;
    
    private String firstName;
    
    private String lastName;
    
    private String phoneNumber;
    
    private String role; // ADMIN, CUSTOMER, DRIVER
    
    private boolean active;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime lastLogin;
    
    private List<String> permissions;
}
