package com.ravishka.megacitycab.Controller;

import com.ravishka.megacitycab.Model.Bill;
import com.ravishka.megacitycab.Service.BillService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/bills")
public class BillController {

    private final BillService billService;

    public BillController(BillService billService) {
        this.billService = billService;
    }

    @GetMapping
    public ResponseEntity<List<Bill>> fetchAllBills() {
        List<Bill> bills = billService.getAllBills();
        return ResponseEntity.ok(bills);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> fetchBillById(@PathVariable String id) {
        Optional<Bill> billOptional = billService.getBillById(id);
        if (billOptional.isPresent()) {
            return ResponseEntity.ok(billOptional.get());
        }
        Map<String, Object> error = new HashMap<>();
        error.put("message", "No bill found with id: " + id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @GetMapping("/number/{billNumber}")
    public ResponseEntity<?> fetchBillByBillNumber(@PathVariable String billNumber) {
        Optional<Bill> billOptional = billService.getBillByBillNumber(billNumber);
        if (billOptional.isPresent()) {
            return ResponseEntity.ok(billOptional.get());
        }
        Map<String, Object> error = new HashMap<>();
        error.put("message", "Couldn't find a bill with bill number: " + billNumber + "!");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Bill>> fetchBillsByCustomerId(@PathVariable String customerId) {
        List<Bill> bills = billService.getBillsByCustomerId(customerId);
        return ResponseEntity.ok(bills);
    }

    @PostMapping
    public ResponseEntity<?> addBill(@RequestBody Bill bill) {
        try {
            Bill createdBill = billService.createBill(bill);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Bill created successfully!");
            response.put("bill", createdBill);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("message", "Failed to create bill: " + e.getMessage() + "!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
}
