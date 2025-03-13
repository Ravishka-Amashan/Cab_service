package com.ravishka.megacitycab.Service;

import com.ravishka.megacitycab.Model.Bill;
import com.ravishka.megacitycab.Model.Reservation;
import com.ravishka.megacitycab.Repository.BillRepository;
import com.ravishka.megacitycab.Repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;
    
    @Autowired
    private ReservationRepository reservationRepository;

    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    public Optional<Bill> getBillById(String id) {
        return billRepository.findById(id);
    }

    public Optional<Bill> getBillByBillNumber(String billNumber) {
        return billRepository.findByBillNumber(billNumber);
    }

    public Optional<Bill> getBillByBookingId(String bookingId) {
        return billRepository.findByBookingId(bookingId);
    }

    public List<Bill> getBillsByCustomerId(String customerId) {
        return billRepository.findByCustomerId(customerId);
    }

    public List<Bill> getBillsByPaymentStatus(String paymentStatus) {
        return billRepository.findByPaymentStatus(paymentStatus);
    }

    public List<Bill> getBillsByPaymentMethod(String paymentMethod) {
        return billRepository.findByPaymentMethod(paymentMethod);
    }

    public List<Bill> getBillsByBillDateRange(LocalDateTime start, LocalDateTime end) {
        return billRepository.findByBillDateBetween(start, end);
    }

    public List<Bill> getBillsByPaymentDateRange(LocalDateTime start, LocalDateTime end) {
        return billRepository.findByPaymentDateBetween(start, end);
    }

    public Bill createBill(Bill bill) {
        // Generate a unique bill number if not provided
        if (bill.getBillNumber() == null || bill.getBillNumber().isEmpty()) {
            bill.setBillNumber("BILL-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        }
        
        // Set default values if not provided
        if (bill.getBillDate() == null) {
            bill.setBillDate(LocalDateTime.now());
        }
        
        if (bill.getPaymentStatus() == null || bill.getPaymentStatus().isEmpty()) {
            bill.setPaymentStatus("PENDING");
        }
        
        // Calculate total amount if not provided
        if (bill.getTotalAmount() <= 0) {
            double totalAmount = bill.getBaseFare() + bill.getDistanceCharge() + bill.getWaitingCharge();
            
            // Apply discount if provided
            if (bill.getDiscount() > 0) {
                totalAmount -= bill.getDiscount();
            }
            
            // Apply tax if provided
            if (bill.getTax() > 0) {
                totalAmount += bill.getTax();
            } else {
                // Default tax (10%)
                double tax = totalAmount * 0.1;
                bill.setTax(tax);
                totalAmount += tax;
            }
            
            bill.setTotalAmount(totalAmount);
        }
        
        // Update booking payment status if booking ID is provided
        if (bill.getBookingId() != null && !bill.getBookingId().isEmpty()) {
            Optional<Reservation> bookingOptional = reservationRepository.findById(bill.getBookingId());
            if (bookingOptional.isPresent()) {
                Reservation booking = bookingOptional.get();
                booking.setPaymentState(bill.getPaymentStatus());
                reservationRepository.save(booking);
            }
        }
        
        return billRepository.save(bill);
    }

    public Bill generateBillFromBooking(String bookingId) {
        Optional<Reservation> bookingOptional = reservationRepository.findById(bookingId);
        
        if (bookingOptional.isPresent()) {
            Reservation booking = bookingOptional.get();
            
            // Check if bill already exists for this booking
            Optional<Bill> existingBill = billRepository.findByBookingId(bookingId);
            if (existingBill.isPresent()) {
                return existingBill.get();
            }
            
            // Create new bill
            Bill bill = new Bill();
            bill.setBillNumber("BILL-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
            bill.setBookingId(bookingId);
            bill.setCustomerId(booking.getClientId());
            bill.setBillDate(LocalDateTime.now());
            
            // Set fare details
            bill.setBaseFare(booking.getBasePrice() * 0.7); // 70% of fare as base fare
            bill.setDistanceCharge(booking.getTripDistance() * 0.3); // 30% of fare as distance charge
            bill.setWaitingCharge(0); // No waiting charge by default
            bill.setDiscount(booking.getPromotionAmount());
            bill.setTax(booking.getServiceFee());
            bill.setTotalAmount(booking.getFinalCharge());
            
            bill.setPaymentStatus("PENDING");
            
            // Update booking payment status
            booking.setPaymentState("PENDING");
            reservationRepository.save(booking);
            
            return billRepository.save(bill);
        }
        
        return null;
    }

    public Bill updateBill(String id, Bill billDetails) {
        Optional<Bill> billOptional = billRepository.findById(id);
        
        if (billOptional.isPresent()) {
            Bill bill = billOptional.get();
            
            // Update fields
            if (billDetails.getBaseFare() >= 0) {
                bill.setBaseFare(billDetails.getBaseFare());
            }
            if (billDetails.getDistanceCharge() >= 0) {
                bill.setDistanceCharge(billDetails.getDistanceCharge());
            }
            if (billDetails.getWaitingCharge() >= 0) {
                bill.setWaitingCharge(billDetails.getWaitingCharge());
            }
            if (billDetails.getDiscount() >= 0) {
                bill.setDiscount(billDetails.getDiscount());
            }
            if (billDetails.getTax() >= 0) {
                bill.setTax(billDetails.getTax());
            }
            if (billDetails.getTotalAmount() > 0) {
                bill.setTotalAmount(billDetails.getTotalAmount());
            }
            if (billDetails.getPaymentMethod() != null) {
                bill.setPaymentMethod(billDetails.getPaymentMethod());
            }
            if (billDetails.getPaymentStatus() != null) {
                bill.setPaymentStatus(billDetails.getPaymentStatus());
                
                // Update booking payment status if payment status changes
                if (bill.getBookingId() != null && !bill.getBookingId().isEmpty()) {
                    Optional<Reservation> bookingOptional = reservationRepository.findById(bill.getBookingId());
                    if (bookingOptional.isPresent()) {
                        Reservation booking = bookingOptional.get();
                        booking.setPaymentState(billDetails.getPaymentStatus());
                        reservationRepository.save(booking);
                    }
                }
            }
            if (billDetails.getPaymentDate() != null) {
                bill.setPaymentDate(billDetails.getPaymentDate());
            }
            
            // Don't update bill number, booking ID, and customer ID as they are identifiers
            
            return billRepository.save(bill);
        }
        
        return null;
    }

    public boolean updatePaymentStatus(String id, String paymentStatus, String paymentMethod) {
        Optional<Bill> billOptional = billRepository.findById(id);
        
        if (billOptional.isPresent()) {
            Bill bill = billOptional.get();
            bill.setPaymentStatus(paymentStatus);
            
            if (paymentMethod != null && !paymentMethod.isEmpty()) {
                bill.setPaymentMethod(paymentMethod);
            }
            
            if (paymentStatus.equals("PAID")) {
                bill.setPaymentDate(LocalDateTime.now());
            }
            
            billRepository.save(bill);
            
            // Update booking payment status
            if (bill.getBookingId() != null && !bill.getBookingId().isEmpty()) {
                Optional<Reservation> bookingOptional = reservationRepository.findById(bill.getBookingId());
                if (bookingOptional.isPresent()) {
                    Reservation booking = bookingOptional.get();
                    booking.setPaymentState(paymentStatus);
                    reservationRepository.save(booking);
                }
            }
            
            return true;
        }
        
        return false;
    }

    public boolean deleteBill(String id) {
        if (billRepository.existsById(id)) {
            billRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
