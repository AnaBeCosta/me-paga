package com.anabeatriz.mepaga.repository;

import com.anabeatriz.mepaga.dto.BillRepositoryDTO;
import com.anabeatriz.mepaga.model.Bill;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Long> {

    List<Bill> findByUserId(Long userId);

    @Query("""
    SELECT new com.anabeatriz.mepaga.dto.BillRepositoryDTO(
        b.description,
        b.dueDate,
        b.amount,
        b.isPaid,
        b.installments,
        u.login,
        u.phone
    )
    FROM Bill b
    JOIN b.user u
    WHERE b.dueDate BETWEEN :today AND :upcoming
""")
    List<BillRepositoryDTO> findByDueDateBetween(LocalDate today, LocalDate upcoming);
}
