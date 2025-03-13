package com.ravishka.megacitycab.Controller;

import com.ravishka.megacitycab.Model.Account;
import com.ravishka.megacitycab.Security.TokenManager;
import com.ravishka.megacitycab.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private TokenManager tokenManager;
    
    @Autowired
    private AccountService accountService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");
        
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
            );
            
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = tokenManager.generateToken(userDetails);
            
            // Update last login time
            accountService.updateLastLogin(username);
            
            // Get account details
            Optional<Account> accountOptional = accountService.getAccountByUsername(username);
            
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("tokenType", "Bearer");
            response.put("expiresIn", tokenManager.getExpirationTime());
            
            if (accountOptional.isPresent()) {
                Account account = accountOptional.get();
                Map<String, Object> user = new HashMap<>();
                user.put("id", account.getId());
                user.put("username", account.getUsername());
                user.put("email", account.getEmail());
                user.put("firstName", account.getFirstName());
                user.put("lastName", account.getLastName());
                user.put("role", account.getRole());
                user.put("permissions", account.getPermissions());
                
                response.put("user", user);
            }
            
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("message", "Invalid username or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Account account) {
        try {
            Account createdAccount = accountService.createAccount(account);
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Account created successfully");
            
            // Don't return password in response
            createdAccount.setPassword(null);
            response.put("account", createdAccount);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("message", "Failed to create account: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody Map<String, String> passwordData) {
        String id = passwordData.get("id");
        String oldPassword = passwordData.get("oldPassword");
        String newPassword = passwordData.get("newPassword");
        
        if (id == null || oldPassword == null || newPassword == null) {
            Map<String, Object> error = new HashMap<>();
            error.put("message", "Missing required fields");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
        
        boolean updated = accountService.updatePassword(id, oldPassword, newPassword);
        
        if (updated) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Password updated successfully");
            return ResponseEntity.ok(response);
        } else {
            Map<String, Object> error = new HashMap<>();
            error.put("message", "Invalid old password or account not found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> resetData) {
        // This would typically involve sending a reset link to the user's email
        // For simplicity, we'll just reset the password directly
        
        String email = resetData.get("email");
        
        if (email == null) {
            Map<String, Object> error = new HashMap<>();
            error.put("message", "Email is required");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
        
        Optional<Account> accountOptional = accountService.getAccountByEmail(email);
        
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            
            // Generate a random password
            String newPassword = generateRandomPassword();
            
            // Reset password
            accountService.resetPassword(account.getId(), newPassword);
            
            // In a real application, send the new password to the user's email
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Password reset successfully. Check your email for the new password.");
            return ResponseEntity.ok(response);
        } else {
            Map<String, Object> error = new HashMap<>();
            error.put("message", "Account not found with the provided email");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @GetMapping("/validate-token")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7); // Remove "Bearer " prefix
        
        if (tokenManager.validateToken(token)) {
            String username = tokenManager.getUsernameFromToken(token);
            Optional<Account> accountOptional = accountService.getAccountByUsername(username);
            
            if (accountOptional.isPresent()) {
                Account account = accountOptional.get();
                
                Map<String, Object> response = new HashMap<>();
                response.put("valid", true);
                
                Map<String, Object> user = new HashMap<>();
                user.put("id", account.getId());
                user.put("username", account.getUsername());
                user.put("email", account.getEmail());
                user.put("firstName", account.getFirstName());
                user.put("lastName", account.getLastName());
                user.put("role", account.getRole());
                user.put("permissions", account.getPermissions());
                
                response.put("user", user);
                
                return ResponseEntity.ok(response);
            }
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("valid", false);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    private String generateRandomPassword() {
        // Generate a random password
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int index = (int) (Math.random() * chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }
}
