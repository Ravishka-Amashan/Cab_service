package com.ravishka.megacitycab.Service;

import com.ravishka.megacitycab.Model.Account;
import com.ravishka.megacitycab.Repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Optional<Account> getAccountById(String id) {
        return accountRepository.findById(id);
    }

    public Optional<Account> getAccountByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    public Optional<Account> getAccountByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    public List<Account> getAccountsByRole(String role) {
        return accountRepository.findByRole(role);
    }

    public List<Account> getActiveAccounts() {
        return accountRepository.findByActive(true);
    }

    public Account createAccount(Account account) {
        // Check if username or email already exists
        if (accountRepository.existsByUsername(account.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        
        if (accountRepository.existsByEmail(account.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        
        // Encode password
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        
        // Set default values
        account.setActive(true);
        account.setCreatedAt(LocalDateTime.now());
        
        if (account.getRole() == null || account.getRole().isEmpty()) {
            account.setRole("CUSTOMER"); // Default role
        }
        
        if (account.getPermissions() == null) {
            account.setPermissions(new ArrayList<>());
        }
        
        return accountRepository.save(account);
    }

    public Account updateAccount(String id, Account accountDetails) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            
            // Update fields
            if (accountDetails.getFirstName() != null && !accountDetails.getFirstName().isEmpty()) {
                account.setFirstName(accountDetails.getFirstName());
            }
            if (accountDetails.getLastName() != null && !accountDetails.getLastName().isEmpty()) {
                account.setLastName(accountDetails.getLastName());
            }
            if (accountDetails.getPhoneNumber() != null && !accountDetails.getPhoneNumber().isEmpty()) {
                account.setPhoneNumber(accountDetails.getPhoneNumber());
            }
            
            // Only admin can update role and permissions
            if (accountDetails.getRole() != null && !accountDetails.getRole().isEmpty()) {
                account.setRole(accountDetails.getRole());
            }
            if (accountDetails.getPermissions() != null) {
                account.setPermissions(accountDetails.getPermissions());
            }
            
            // Don't update username, email, and password here
            
            return accountRepository.save(account);
        }
        
        return null;
    }

    public boolean updatePassword(String id, String oldPassword, String newPassword) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            
            // Verify old password
            if (passwordEncoder.matches(oldPassword, account.getPassword())) {
                // Update password
                account.setPassword(passwordEncoder.encode(newPassword));
                accountRepository.save(account);
                return true;
            }
        }
        
        return false;
    }

    public boolean resetPassword(String id, String newPassword) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            
            // Reset password
            account.setPassword(passwordEncoder.encode(newPassword));
            accountRepository.save(account);
            return true;
        }
        
        return false;
    }

    public boolean updateAccountStatus(String id, boolean active) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            account.setActive(active);
            accountRepository.save(account);
            return true;
        }
        
        return false;
    }

    public boolean updateLastLogin(String username) {
        Optional<Account> accountOptional = accountRepository.findByUsername(username);
        
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            account.setLastLogin(LocalDateTime.now());
            accountRepository.save(account);
            return true;
        }
        
        return false;
    }

    public boolean deleteAccount(String id) {
        if (accountRepository.existsById(id)) {
            accountRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
