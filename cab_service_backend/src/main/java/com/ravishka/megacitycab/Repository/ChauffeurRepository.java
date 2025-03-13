package com.ravishka.megacitycab.Repository;

import com.ravishka.megacitycab.Model.Chauffeur;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface ChauffeurRepository extends MongoRepository<Chauffeur, String> {
    Optional<Chauffeur> findByLicenseNumber(String licenseNumber);
    List<Chauffeur> findByAvailable(boolean available);
    List<Chauffeur> findByStatus(String status);
    List<Chauffeur> findByRatingGreaterThanEqual(double rating);
    List<Chauffeur> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName);
}
