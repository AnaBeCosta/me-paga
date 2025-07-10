package com.anabeatriz.mepaga.controller;

import com.anabeatriz.mepaga.dto.BillDTO;
import com.anabeatriz.mepaga.model.Bill;
import com.anabeatriz.mepaga.model.User;
import com.anabeatriz.mepaga.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("bill")
public class BillController {

    @Autowired
    BillRepository billRepository;

    @PostMapping("/create")
    public ResponseEntity createBill(@RequestBody BillDTO dto){
        Bill newBill = new Bill(dto.description(), dto.dueDate(), dto.amount(), dto.isPaid(), dto.installments());

        var currentUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (currentUser instanceof User user) {
            newBill.setUser(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        billRepository.save(newBill);

        return ResponseEntity.ok().build();
    }
}
