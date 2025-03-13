package com.ravishka.megacitycab.Service;

import com.ravishka.megacitycab.Model.Cab;
import com.ravishka.megacitycab.Repository.CabRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CabService {

    @Autowired
    private CabRepository cabRepository;

    public List<Cab> getAllCabs() {
        return cabRepository.findAll();
    }

    public Optional<Cab> getCabById(String id) {
        return cabRepository.findById(id);
    }

    public Optional<Cab> getCabByRegistrationNumber(String registrationNumber) {
        return cabRepository.findByRegistrationNumber(registrationNumber);
    }

    public List<Cab> getAvailableCabs() {
        return cabRepository.findByAvailable(true);
    }

    public List<Cab> getCabsByType(String type) {
        return cabRepository.findByType(type);
    }

    public List<Cab> getCabsByStatus(String status) {
        return cabRepository.findByStatus(status);
    }

    public List<Cab> getCabsByMinCapacity(int capacity) {
        return cabRepository.findBySeatingCapacityGreaterThanEqual(capacity);
    }

    public Cab createCab(Cab cab) {
        // Set default values if not provided
        if (cab.getStatus() == null || cab.getStatus().isEmpty()) {
            cab.setStatus("ACTIVE");
        }
        
        if (cab.getType() == null || cab.getType().isEmpty()) {
            cab.setType("SEDAN"); // Default type
        }
        
        return cabRepository.save(cab);
    }

    public Cab updateCab(String id, Cab cabDetails) {
        Optional<Cab> cabOptional = cabRepository.findById(id);
        
        if (cabOptional.isPresent()) {
            Cab cab = cabOptional.get();
            
            // Update fields
            if (cabDetails.getRegistrationNumber() != null && !cabDetails.getRegistrationNumber().isEmpty()) {
                cab.setRegistrationNumber(cabDetails.getRegistrationNumber());
            }
            if (cabDetails.getModel() != null && !cabDetails.getModel().isEmpty()) {
                cab.setModel(cabDetails.getModel());
            }
            if (cabDetails.getColor() != null && !cabDetails.getColor().isEmpty()) {
                cab.setColor(cabDetails.getColor());
            }
            if (cabDetails.getYear() > 0) {
                cab.setYear(cabDetails.getYear());
            }
            if (cabDetails.getType() != null && !cabDetails.getType().isEmpty()) {
                cab.setType(cabDetails.getType());
            }
            if (cabDetails.getSeatingCapacity() > 0) {
                cab.setSeatingCapacity(cabDetails.getSeatingCapacity());
            }
            if (cabDetails.getRatePerKm() > 0) {
                cab.setRatePerKm(cabDetails.getRatePerKm());
            }
            
            cab.setAvailable(cabDetails.isAvailable());
            
            if (cabDetails.getCurrentLocation() != null && !cabDetails.getCurrentLocation().isEmpty()) {
                cab.setCurrentLocation(cabDetails.getCurrentLocation());
            }
            if (cabDetails.getStatus() != null && !cabDetails.getStatus().isEmpty()) {
                cab.setStatus(cabDetails.getStatus());
            }
            
            return cabRepository.save(cab);
        }
        
        return null;
    }

    public boolean updateCabAvailability(String id, boolean available) {
        Optional<Cab> cabOptional = cabRepository.findById(id);
        
        if (cabOptional.isPresent()) {
            Cab cab = cabOptional.get();
            cab.setAvailable(available);
            cabRepository.save(cab);
            return true;
        }
        
        return false;
    }

    public boolean updateCabStatus(String id, String status) {
        Optional<Cab> cabOptional = cabRepository.findById(id);
        
        if (cabOptional.isPresent()) {
            Cab cab = cabOptional.get();
            cab.setStatus(status);
            
            // If cab is in maintenance or inactive, it's not available
            if (status.equals("MAINTENANCE") || status.equals("INACTIVE")) {
                cab.setAvailable(false);
            }
            
            cabRepository.save(cab);
            return true;
        }
        
        return false;
    }

    public boolean deleteCab(String id) {
        if (cabRepository.existsById(id)) {
            cabRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
