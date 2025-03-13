package com.ravishka.megacitycab.Controller;

import com.ravishka.megacitycab.Model.Cab;
import com.ravishka.megacitycab.Service.CabService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/cabs")
public class CabController {

    private final CabService cabService;

    public CabController(CabService cabService) {
        this.cabService = cabService;
    }

    @GetMapping
    public ResponseEntity<List<Cab>> getAllCabs() {
        List<Cab> cabs = cabService.getAllCabs();
        return ResponseEntity.ok(cabs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCabById(@PathVariable String id) {
        Optional<Cab> cabOptional = cabService.getCabById(id);
        if (cabOptional.isPresent()) {
            return ResponseEntity.ok(cabOptional.get());
        }
        Map<String, Object> error = new HashMap<>();
        error.put("message", "No cab found with id: " + id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @GetMapping("/available")
    public ResponseEntity<List<Cab>> getAvailableCabs() {
        List<Cab> cabs = cabService.getAvailableCabs();
        return ResponseEntity.ok(cabs);
    }

    @PostMapping
    public ResponseEntity<?> addCab(@RequestBody Cab cab) {
        try {
            Cab createdCab = cabService.createCab(cab);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Cab created successfully!");
            response.put("cab", createdCab);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("message", "Failed to create cab: " + e.getMessage() + "!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCab(@PathVariable String id, @RequestBody Cab cabDetails) {
        try {
            Cab updatedCab = cabService.updateCab(id, cabDetails);
            if (updatedCab != null) {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Cab updated successfully!");
                response.put("cab", updatedCab);
                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> error = new HashMap<>();
                error.put("message", "No cab found with id: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("message", "Failed to update cab: " + e.getMessage() + "!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCab(@PathVariable String id) {
        boolean deleted = cabService.deleteCab(id);
        if (deleted) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Cab deleted successfully!");
            return ResponseEntity.ok(response);
        } else {
            Map<String, Object> error = new HashMap<>();
            error.put("message", "No cab found with id: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
}
