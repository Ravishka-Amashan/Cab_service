package com.ravishka.megacitycab.Repository;

import com.ravishka.megacitycab.Model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface AccountRepository extends MongoRepository<Account, String> {
    Optional<Account> findByUsername(String username);
    Optional<Account> findByEmail(String email);
    List<Account> findByRole(String role);
    List<Account> findByActive(boolean active);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
