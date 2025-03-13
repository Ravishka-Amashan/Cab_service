package com.ravishka.megacitycab.Service;

import com.ravishka.megacitycab.Model.Cab;
import com.ravishka.megacitycab.Model.Chauffeur;
import com.ravishka.megacitycab.Model.Reservation;
import com.ravishka.megacitycab.Repository.CabRepository;
import com.ravishka.megacitycab.Repository.ChauffeurRepository;
import com.ravishka.megacitycab.Repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;
    
    @Autowired
    private CabRepository cabRepository;
    
    @Autowired
    private ChauffeurRepository chauffeurRepository;

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Optional<Reservation> getReservationById(String id) {
        return reservationRepository.findById(id);
    }

    public Optional<Reservation> getReservationByCode(String reservationCode) {
        return reservationRepository.findByReservationCode(reservationCode);
    }

    public List<Reservation> getReservationsByClientId(String clientId) {
        return reservationRepository.findByClientId(clientId);
    }

    public List<Reservation> getReservationsByCabId(String cabId) {
        return reservationRepository.findByCabId(cabId);
    }

    public List<Reservation> getReservationsByChauffeurId(String chauffeurId) {
        return reservationRepository.findByChauffeurId(chauffeurId);
    }

    public List<Reservation> getReservationsByStatus(String status) {
        return reservationRepository.findByTripStatus(status);
    }

    public List<Reservation> getReservationsByScheduledTimeRange(LocalDateTime start, LocalDateTime end) {
        return reservationRepository.findByScheduledTimeBetween(start, end);
    }
    
    public List<Reservation> getReservationsByClientIdAndStatus(String clientId, String status) {
        return reservationRepository.findByClientIdAndTripStatus(clientId, status);
    }
    
    public List<Reservation> getReservationsByPaymentState(String paymentState) {
        return reservationRepository.findByPaymentState(paymentState);
    }

    public Reservation createReservation(Reservation reservation) {
        // Generate a unique reservation code if not provided
        if (reservation.getReservationCode() == null || reservation.getReservationCode().isEmpty()) {
            reservation.setReservationCode("RES-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        }

        
        // Set default values if not provided
        if (reservation.getReservationTime() == null) {
            reservation.setReservationTime(LocalDateTime.now());
        }
        
        if (reservation.getTripStatus() == null || reservation.getTripStatus().isEmpty()) {
            reservation.setTripStatus("PENDING");
        }
        
        if (reservation.getPaymentState() == null || reservation.getPaymentState().isEmpty()) {
            reservation.setPaymentState("PENDING");
        }
        
        // Calculate fare if not provided
        // if (reservation.getBasePrice() <= 0) {
        //     // Get cab details to calculate fare
        //     Optional<Cab> cabOptional = cabRepository.findById(reservation.getCabId());
        //     if (cabOptional.isPresent()) {
        //         Cab cab = cabOptional.get();
        //         double basePrice = cab.getRatePerKm() * reservation.getTripDistance();
        //         reservation.setBasePrice(basePrice);
                
        //         // Apply service fee (10%)
        //         double serviceFee = basePrice * 0.1;
        //         reservation.setServiceFee(serviceFee);
                
        //         // Apply promotion if any
        //         if (reservation.getPromotionAmount() <= 0) {
        //             reservation.setPromotionAmount(0);
        //         }
                
        //         // Calculate final charge
        //         double finalCharge = basePrice + serviceFee - reservation.getPromotionAmount();
        //         reservation.setFinalCharge(finalCharge);
        //     }
        // }
        

        // Update cab and chauffeur availability
        if (reservation.getCabId() != null && !reservation.getCabId().isEmpty()) {
            Optional<Cab> cabOptional = cabRepository.findById(reservation.getCabId());
            if (cabOptional.isPresent()) {
                Cab cab = cabOptional.get();
                cab.setAvailable(false);
                cabRepository.save(cab);
            }
        }
        
        if (reservation.getChauffeurId() != null && !reservation.getChauffeurId().isEmpty()) {
            Optional<Chauffeur> chauffeurOptional = chauffeurRepository.findById(reservation.getChauffeurId());
            if (chauffeurOptional.isPresent()) {
                Chauffeur chauffeur = chauffeurOptional.get();
                chauffeur.setAvailable(false);
                chauffeurRepository.save(chauffeur);
            }
        }

        
        return reservationRepository.save(reservation);
    }

    public Reservation updateReservation(String id, Reservation reservationDetails) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(id);
        
        if (reservationOptional.isPresent()) {
            Reservation reservation = reservationOptional.get();
            
            // Update fields
            if (reservationDetails.getPickupLocation() != null && !reservationDetails.getPickupLocation().isEmpty()) {
                reservation.setPickupLocation(reservationDetails.getPickupLocation());
            }
            if (reservationDetails.getDropoffLocation() != null && !reservationDetails.getDropoffLocation().isEmpty()) {
                reservation.setDropoffLocation(reservationDetails.getDropoffLocation());
            }
            if (reservationDetails.getTripDistance() > 0) {
                reservation.setTripDistance(reservationDetails.getTripDistance());
            }
            if (reservationDetails.getScheduledTime() != null) {
                reservation.setScheduledTime(reservationDetails.getScheduledTime());
            }
            
            // Don't update reservation code, client ID, and reservation time as they are identifiers
            
            return reservationRepository.save(reservation);
        }
        
        return null;
    }

    public boolean updateReservationStatus(String id, String status) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(id);
        
        if (reservationOptional.isPresent()) {
            Reservation reservation = reservationOptional.get();
            String oldStatus = reservation.getTripStatus();
            reservation.setTripStatus(status);
            
            // If trip is completed, set finish time
            if (status.equals("COMPLETED")) {
                reservation.setFinishTime(LocalDateTime.now());
                
                // Update cab and chauffeur availability
                if (reservation.getCabId() != null && !reservation.getCabId().isEmpty()) {
                    Optional<Cab> cabOptional = cabRepository.findById(reservation.getCabId());
                    if (cabOptional.isPresent()) {
                        Cab cab = cabOptional.get();
                        cab.setAvailable(true);
                        cabRepository.save(cab);
                    }
                }
                
                if (reservation.getChauffeurId() != null && !reservation.getChauffeurId().isEmpty()) {
                    Optional<Chauffeur> chauffeurOptional = chauffeurRepository.findById(reservation.getChauffeurId());
                    if (chauffeurOptional.isPresent()) {
                        Chauffeur chauffeur = chauffeurOptional.get();
                        chauffeur.setAvailable(true);
                        chauffeurRepository.save(chauffeur);
                    }
                }
            }
            
            // If trip is cancelled, update cab and chauffeur availability
            if (status.equals("CANCELLED") && !oldStatus.equals("COMPLETED")) {
                if (reservation.getCabId() != null && !reservation.getCabId().isEmpty()) {
                    Optional<Cab> cabOptional = cabRepository.findById(reservation.getCabId());
                    if (cabOptional.isPresent()) {
                        Cab cab = cabOptional.get();
                        cab.setAvailable(true);
                        cabRepository.save(cab);
                    }
                }
                
                if (reservation.getChauffeurId() != null && !reservation.getChauffeurId().isEmpty()) {
                    Optional<Chauffeur> chauffeurOptional = chauffeurRepository.findById(reservation.getChauffeurId());
                    if (chauffeurOptional.isPresent()) {
                        Chauffeur chauffeur = chauffeurOptional.get();
                        chauffeur.setAvailable(true);
                        chauffeurRepository.save(chauffeur);
                    }
                }
            }
            
            reservationRepository.save(reservation);
            return true;
        }
        
        return false;
    }

    public boolean updatePaymentState(String id, String paymentState) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(id);
        
        if (reservationOptional.isPresent()) {
            Reservation reservation = reservationOptional.get();
            reservation.setPaymentState(paymentState);
            reservationRepository.save(reservation);
            return true;
        }
        
        return false;
    }

    public boolean deleteReservation(String id) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(id);
        
        if (reservationOptional.isPresent()) {
            Reservation reservation = reservationOptional.get();
            
            // Update cab and chauffeur availability if reservation is not completed
            if (!reservation.getTripStatus().equals("COMPLETED")) {
                if (reservation.getCabId() != null && !reservation.getCabId().isEmpty()) {
                    Optional<Cab> cabOptional = cabRepository.findById(reservation.getCabId());
                    if (cabOptional.isPresent()) {
                        Cab cab = cabOptional.get();
                        cab.setAvailable(true);
                        cabRepository.save(cab);
                    }
                }
                
                if (reservation.getChauffeurId() != null && !reservation.getChauffeurId().isEmpty()) {
                    Optional<Chauffeur> chauffeurOptional = chauffeurRepository.findById(reservation.getChauffeurId());
                    if (chauffeurOptional.isPresent()) {
                        Chauffeur chauffeur = chauffeurOptional.get();
                        chauffeur.setAvailable(true);
                        chauffeurRepository.save(chauffeur);
                    }
                }
            }
            
            reservationRepository.deleteById(id);
            return true;
        }
        
        return false;
    }
}
