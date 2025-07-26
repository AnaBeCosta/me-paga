package com.anabeatriz.mepaga.controller;

import com.anabeatriz.mepaga.dto.BillDTO;
import com.anabeatriz.mepaga.model.Bill;
import com.anabeatriz.mepaga.model.User;
import com.anabeatriz.mepaga.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("bill")
public class BillController {

    @Autowired
    BillRepository billRepository;

    @PostMapping("/create")
    public ResponseEntity createBill(@RequestBody BillDTO dto){
        Bill newBill = new Bill(dto.description(), dto.dueDate(), dto.amount(), dto.isPaid(), dto.installments(), dto.user());

        var currentUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (currentUser instanceof User user) {
            newBill.setUser(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        billRepository.save(newBill);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBill(@PathVariable("id") Long id){
        Optional<Bill> existingBill = this.billRepository.findById(id);

        if (existingBill.isPresent()) {
            this.billRepository.delete(existingBill.get());
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<List<Bill>> listBillsByUser(@PathVariable("id") Long userId) {
        List<Bill> bills = billRepository.findByUserId(userId);

        if (bills.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(bills);
    }


}
