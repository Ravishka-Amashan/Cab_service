package com.ravishka.megacitycab.Repository;

import com.ravishka.megacitycab.Model.Cab;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface CabRepository extends MongoRepository<Cab, String> {
    Optional<Cab> findByRegistrationNumber(String registrationNumber);
    List<Cab> findByAvailable(boolean available);
    List<Cab> findByType(String type);
    List<Cab> findByStatus(String status);
    List<Cab> findBySeatingCapacityGreaterThanEqual(int capacity);
}
