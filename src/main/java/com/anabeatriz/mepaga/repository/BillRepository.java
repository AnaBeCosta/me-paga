package com.anabeatriz.mepaga.repository;

import com.anabeatriz.mepaga.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Long> {

    List<Bill> findByUserId(Long userId);
}
