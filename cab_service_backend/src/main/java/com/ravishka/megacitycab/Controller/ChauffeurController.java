package com.ravishka.megacitycab.Controller;

import com.ravishka.megacitycab.Model.Chauffeur;
import com.ravishka.megacitycab.Service.ChauffeurService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/chauffeurs")
public class ChauffeurController {

    private final ChauffeurService chauffeurService;

    public ChauffeurController(ChauffeurService chauffeurService) {
        this.chauffeurService = chauffeurService;
    }

    @GetMapping
    public ResponseEntity<List<Chauffeur>> getAllChauffeurs() {
        List<Chauffeur> chauffeurs = chauffeurService.getAllChauffeurs();
        return ResponseEntity.ok(chauffeurs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getChauffeurById(@PathVariable String id) {
        Optional<Chauffeur> chauffeurOptional = chauffeurService.getChauffeurById(id);
        if (chauffeurOptional.isPresent()) {
            return ResponseEntity.ok(chauffeurOptional.get());
        }
        Map<String, Object> error = new HashMap<>();
        error.put("message", "No chauffeur found with id: " + id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @GetMapping("/license/{licenseNumber}")
    public ResponseEntity<?> getChauffeurByLicenseNumber(@PathVariable String licenseNumber) {
        Optional<Chauffeur> chauffeurOptional = chauffeurService.getChauffeurByLicenseNumber(licenseNumber);
        if (chauffeurOptional.isPresent()) {
            return ResponseEntity.ok(chauffeurOptional.get());
        }
        Map<String, Object> error = new HashMap<>();
        error.put("message", "No chauffeur found with license number: " + licenseNumber);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @GetMapping("/available")
    public ResponseEntity<List<Chauffeur>> getAvailableChauffeurs() {
        List<Chauffeur> chauffeurs = chauffeurService.getAvailableChauffeurs();
        return ResponseEntity.ok(chauffeurs);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Chauffeur>> getChauffeursByStatus(@PathVariable String status) {
        List<Chauffeur> chauffeurs = chauffeurService.getChauffeursByStatus(status);
        return ResponseEntity.ok(chauffeurs);
    }

    @GetMapping("/rating/{rating}")
    public ResponseEntity<List<Chauffeur>> getChauffeursByMinRating(@PathVariable double rating) {
        List<Chauffeur> chauffeurs = chauffeurService.getChauffeursByMinRating(rating);
        return ResponseEntity.ok(chauffeurs);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Chauffeur>> searchChauffeursByName(@RequestParam String name) {
        List<Chauffeur> chauffeurs = chauffeurService.searchChauffeursByName(name);
        return ResponseEntity.ok(chauffeurs);
    }

    @PostMapping
    public ResponseEntity<?> addChauffeur(@RequestBody Chauffeur chauffeur) {
        try {
            Chauffeur createdChauffeur = chauffeurService.createChauffeur(chauffeur);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Chauffeur created successfully!");
            response.put("chauffeur", createdChauffeur);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("message", "Failed to create chauffeur: " + e.getMessage() + "!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateChauffeur(@PathVariable String id, @RequestBody Chauffeur chauffeurDetails) {
        try {
            Chauffeur updatedChauffeur = chauffeurService.updateChauffeur(id, chauffeurDetails);
            if (updatedChauffeur != null) {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Chauffeur updated successfully!");
                response.put("chauffeur", updatedChauffeur);
                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> error = new HashMap<>();
                error.put("message", "No chauffeur found with id: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("message", "Failed to update chauffeur: " + e.getMessage() + "!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @PatchMapping("/{id}/availability")
    public ResponseEntity<?> updateChauffeurAvailability(@PathVariable String id, @RequestParam boolean available) {
        boolean updated = chauffeurService.updateChauffeurAvailability(id, available);
        if (updated) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Chauffeur availability updated successfully!");
            return ResponseEntity.ok(response);
        } else {
            Map<String, Object> error = new HashMap<>();
            error.put("message", "No chauffeur found with id: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateChauffeurStatus(@PathVariable String id, @RequestParam String status) {
        boolean updated = chauffeurService.updateChauffeurStatus(id, status);
        if (updated) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Chauffeur status updated successfully!");
            return ResponseEntity.ok(response);
        } else {
            Map<String, Object> error = new HashMap<>();
            error.put("message", "No chauffeur found with id: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @PatchMapping("/{id}/rating")
    public ResponseEntity<?> updateChauffeurRating(@PathVariable String id, @RequestParam double rating) {
        if (rating < 1 || rating > 5) {
            Map<String, Object> error = new HashMap<>();
            error.put("message", "Rating must be between 1 and 5!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
        
        boolean updated = chauffeurService.updateChauffeurRating(id, rating);
        if (updated) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Chauffeur rating updated successfully!");
            return ResponseEntity.ok(response);
        } else {
            Map<String, Object> error = new HashMap<>();
            error.put("message", "No chauffeur found with id: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteChauffeur(@PathVariable String id) {
        boolean deleted = chauffeurService.deleteChauffeur(id);
        if (deleted) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Chauffeur deleted successfully!");
            return ResponseEntity.ok(response);
        } else {
            Map<String, Object> error = new HashMap<>();
            error.put("message", "No chauffeur found with id: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
}
