package com.anabeatriz.mepaga.dto;

import java.time.LocalDate;

public record BillRepositoryDTO(
        String description,
        LocalDate dueDate,
        Double amount,
        Boolean isPaid,
        Integer installments,
        String userLogin,
        String userPhone
) {}