package com.ravishka.megacitycab.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.ravishka.megacitycab.Model.Bill;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BillRepository extends MongoRepository<Bill, String> {
    Optional<Bill> findByBillNumber(String billNumber);
    Optional<Bill> findByBookingId(String bookingId);
    List<Bill> findByCustomerId(String customerId);
    List<Bill> findByPaymentStatus(String paymentStatus);
    List<Bill> findByPaymentMethod(String paymentMethod);
    List<Bill> findByBillDateBetween(LocalDateTime start, LocalDateTime end);
    List<Bill> findByPaymentDateBetween(LocalDateTime start, LocalDateTime end);
}
