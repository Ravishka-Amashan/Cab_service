package com.ravishka.megacitycab.Repository;

import com.ravishka.megacitycab.Model.Reservation;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends MongoRepository<Reservation, String> {
    Optional<Reservation> findByReservationCode(String reservationCode);
    
    List<Reservation> findByClientId(String clientId);
    
    List<Reservation> findByCabId(String cabId);
    
    List<Reservation> findByChauffeurId(String chauffeurId);
    
    List<Reservation> findByTripStatus(String tripStatus);
    
    List<Reservation> findByScheduledTimeBetween(LocalDateTime start, LocalDateTime end);
    
    List<Reservation> findByClientIdAndTripStatus(String clientId, String tripStatus);
    
    List<Reservation> findByPaymentState(String paymentState);
}
