package com.ravishka.megacitycab.Security;

import com.ravishka.megacitycab.Model.Account;
import com.ravishka.megacitycab.Repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> accountOptional = accountRepository.findByUsername(username);
        
        if (accountOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        
        Account account = accountOptional.get();
        
        // Check if account is active
        if (!account.isActive()) {
            throw new UsernameNotFoundException("User is not active: " + username);
        }
        
        // Create authorities based on role and permissions
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + account.getRole()));
        
        // Add permissions as authorities
        if (account.getPermissions() != null) {
            for (String permission : account.getPermissions()) {
                authorities.add(new SimpleGrantedAuthority(permission));
            }
        }
        
        return new User(account.getUsername(), account.getPassword(), authorities);
    }
}
