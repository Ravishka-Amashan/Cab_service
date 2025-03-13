package com.ravishka.megacitycab.Controller;

import com.ravishka.megacitycab.Model.Reservation;
import com.ravishka.megacitycab.Service.ReservationService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservations = reservationService.getAllReservations();
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReservationById(@PathVariable String id) {
        Optional<Reservation> reservationOptional = reservationService.getReservationById(id);
        if (reservationOptional.isPresent()) {
            return ResponseEntity.ok(reservationOptional.get());
        }
        Map<String, Object> error = new HashMap<>();
        error.put("message", "No reservation found with id: " + id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @GetMapping("/code/{reservationCode}")
    public ResponseEntity<?> getReservationByCode(@PathVariable String reservationCode) {
        Optional<Reservation> reservationOptional = reservationService.getReservationByCode(reservationCode);
        if (reservationOptional.isPresent()) {
            return ResponseEntity.ok(reservationOptional.get());
        }
        Map<String, Object> error = new HashMap<>();
        error.put("message", "No reservation found with code: " + reservationCode);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Reservation>> getReservationsByClientId(@PathVariable String clientId) {
        List<Reservation> reservations = reservationService.getReservationsByClientId(clientId);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/cab/{cabId}")
    public ResponseEntity<List<Reservation>> getReservationsByCabId(@PathVariable String cabId) {
        List<Reservation> reservations = reservationService.getReservationsByCabId(cabId);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/chauffeur/{chauffeurId}")
    public ResponseEntity<List<Reservation>> getReservationsByChauffeurId(@PathVariable String chauffeurId) {
        List<Reservation> reservations = reservationService.getReservationsByChauffeurId(chauffeurId);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Reservation>> getReservationsByStatus(@PathVariable String status) {
        List<Reservation> reservations = reservationService.getReservationsByStatus(status);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/scheduled")
    public ResponseEntity<List<Reservation>> getReservationsByScheduledTimeRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        List<Reservation> reservations = reservationService.getReservationsByScheduledTimeRange(start, end);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/client/{clientId}/status/{status}")
    public ResponseEntity<List<Reservation>> getReservationsByClientIdAndStatus(
            @PathVariable String clientId, @PathVariable String status) {
        List<Reservation> reservations = reservationService.getReservationsByClientIdAndStatus(clientId, status);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/payment/{paymentState}")
    public ResponseEntity<List<Reservation>> getReservationsByPaymentState(@PathVariable String paymentState) {
        List<Reservation> reservations = reservationService.getReservationsByPaymentState(paymentState);
        return ResponseEntity.ok(reservations);
    }

    @PostMapping
    public ResponseEntity<?> createReservation(@RequestBody Reservation reservation) {
        
        try {
            Reservation createdReservation = reservationService.createReservation(reservation);
            System.out.println(createdReservation);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Reservation created successfully!");
            response.put("reservation", createdReservation);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("message", "Failed to create reservation: " + e.getMessage() + "!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReservation(@PathVariable String id, @RequestBody Reservation reservationDetails) {
        try {
            Reservation updatedReservation = reservationService.updateReservation(id, reservationDetails);
            if (updatedReservation != null) {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Reservation updated successfully!");
                response.put("reservation", updatedReservation);
                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> error = new HashMap<>();
                error.put("message", "No reservation found with id: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("message", "Failed to update reservation: " + e.getMessage() + "!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateReservationStatus(@PathVariable String id, @RequestParam String status) {
        boolean updated = reservationService.updateReservationStatus(id, status);
        if (updated) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Reservation status updated successfully!");
            return ResponseEntity.ok(response);
        } else {
            Map<String, Object> error = new HashMap<>();
            error.put("message", "No reservation found with id: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @PatchMapping("/{id}/payment")
    public ResponseEntity<?> updatePaymentState(@PathVariable String id, @RequestParam String paymentState) {
        boolean updated = reservationService.updatePaymentState(id, paymentState);
        if (updated) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Payment state updated successfully!");
            return ResponseEntity.ok(response);
        } else {
            Map<String, Object> error = new HashMap<>();
            error.put("message", "No reservation found with id: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable String id) {
        boolean deleted = reservationService.deleteReservation(id);
        if (deleted) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Reservation deleted successfully!");
            return ResponseEntity.ok(response);
        } else {
            Map<String, Object> error = new HashMap<>();
            error.put("message", "No reservation found with id: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
}
