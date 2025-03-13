package com.ravishka.megacitycab.Service;

import com.ravishka.megacitycab.Model.Chauffeur;
import com.ravishka.megacitycab.Repository.ChauffeurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ChauffeurService {

    @Autowired
    private ChauffeurRepository chauffeurRepository;

    public List<Chauffeur> getAllChauffeurs() {
        return chauffeurRepository.findAll();
    }

    public Optional<Chauffeur> getChauffeurById(String id) {
        return chauffeurRepository.findById(id);
    }

    public Optional<Chauffeur> getChauffeurByLicenseNumber(String licenseNumber) {
        return chauffeurRepository.findByLicenseNumber(licenseNumber);
    }

    public List<Chauffeur> getAvailableChauffeurs() {
        return chauffeurRepository.findByAvailable(true);
    }

    public List<Chauffeur> getChauffeursByStatus(String status) {
        return chauffeurRepository.findByStatus(status);
    }

    public List<Chauffeur> getChauffeursByMinRating(double rating) {
        return chauffeurRepository.findByRatingGreaterThanEqual(rating);
    }

    public List<Chauffeur> searchChauffeursByName(String name) {
        return chauffeurRepository.findByFirstNameContainingOrLastNameContaining(name, name);
    }

    public Chauffeur createChauffeur(Chauffeur chauffeur) {
        // Set default values if not provided
        if (chauffeur.getStatus() == null || chauffeur.getStatus().isEmpty()) {
            chauffeur.setStatus("ACTIVE");
        }
        
        if (chauffeur.getJoiningDate() == null) {
            chauffeur.setJoiningDate(LocalDate.now());
        }
        
        if (chauffeur.getRating() <= 0) {
            chauffeur.setRating(5.0); // Default rating for new chauffeurs
        }
        
        return chauffeurRepository.save(chauffeur);
    }

    public Chauffeur updateChauffeur(String id, Chauffeur chauffeurDetails) {
        Optional<Chauffeur> chauffeurOptional = chauffeurRepository.findById(id);
        
        if (chauffeurOptional.isPresent()) {
            Chauffeur chauffeur = chauffeurOptional.get();
            
            // Update fields
            if (chauffeurDetails.getFirstName() != null && !chauffeurDetails.getFirstName().isEmpty()) {
                chauffeur.setFirstName(chauffeurDetails.getFirstName());
            }
            if (chauffeurDetails.getLastName() != null && !chauffeurDetails.getLastName().isEmpty()) {
                chauffeur.setLastName(chauffeurDetails.getLastName());
            }
            if (chauffeurDetails.getLicenseNumber() != null && !chauffeurDetails.getLicenseNumber().isEmpty()) {
                chauffeur.setLicenseNumber(chauffeurDetails.getLicenseNumber());
            }
            if (chauffeurDetails.getDateOfBirth() != null) {
                chauffeur.setDateOfBirth(chauffeurDetails.getDateOfBirth());
            }
            if (chauffeurDetails.getPhoneNumber() != null && !chauffeurDetails.getPhoneNumber().isEmpty()) {
                chauffeur.setPhoneNumber(chauffeurDetails.getPhoneNumber());
            }
            if (chauffeurDetails.getEmail() != null && !chauffeurDetails.getEmail().isEmpty()) {
                chauffeur.setEmail(chauffeurDetails.getEmail());
            }
            if (chauffeurDetails.getAddress() != null && !chauffeurDetails.getAddress().isEmpty()) {
                chauffeur.setAddress(chauffeurDetails.getAddress());
            }
            if (chauffeurDetails.getJoiningDate() != null) {
                chauffeur.setJoiningDate(chauffeurDetails.getJoiningDate());
            }
            if (chauffeurDetails.getRating() > 0) {
                chauffeur.setRating(chauffeurDetails.getRating());
            }
            if (chauffeurDetails.getTotalTrips() > 0) {
                chauffeur.setTotalTrips(chauffeurDetails.getTotalTrips());
            }
            
            chauffeur.setAvailable(chauffeurDetails.isAvailable());
            
            if (chauffeurDetails.getStatus() != null && !chauffeurDetails.getStatus().isEmpty()) {
                chauffeur.setStatus(chauffeurDetails.getStatus());
            }
            
            return chauffeurRepository.save(chauffeur);
        }
        
        return null;
    }

    public boolean updateChauffeurAvailability(String id, boolean available) {
        Optional<Chauffeur> chauffeurOptional = chauffeurRepository.findById(id);
        
        if (chauffeurOptional.isPresent()) {
            Chauffeur chauffeur = chauffeurOptional.get();
            chauffeur.setAvailable(available);
            chauffeurRepository.save(chauffeur);
            return true;
        }
        
        return false;
    }

    public boolean updateChauffeurStatus(String id, String status) {
        Optional<Chauffeur> chauffeurOptional = chauffeurRepository.findById(id);
        
        if (chauffeurOptional.isPresent()) {
            Chauffeur chauffeur = chauffeurOptional.get();
            chauffeur.setStatus(status);
            
            // If chauffeur is on leave or inactive, they're not available
            if (status.equals("ON_LEAVE") || status.equals("INACTIVE")) {
                chauffeur.setAvailable(false);
            }
            
            chauffeurRepository.save(chauffeur);
            return true;
        }
        
        return false;
    }

    public boolean updateChauffeurRating(String id, double rating) {
        Optional<Chauffeur> chauffeurOptional = chauffeurRepository.findById(id);
        
        if (chauffeurOptional.isPresent()) {
            Chauffeur chauffeur = chauffeurOptional.get();
            
            // Calculate new average rating
            double currentRating = chauffeur.getRating();
            int totalTrips = chauffeur.getTotalTrips();
            
            double newRating = ((currentRating * totalTrips) + rating) / (totalTrips + 1);
            chauffeur.setRating(newRating);
            chauffeur.setTotalTrips(totalTrips + 1);
            
            chauffeurRepository.save(chauffeur);
            return true;
        }
        
        return false;
    }

    public boolean deleteChauffeur(String id) {
        if (chauffeurRepository.existsById(id)) {
            chauffeurRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
