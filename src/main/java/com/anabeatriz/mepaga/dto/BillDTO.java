package com.anabeatriz.mepaga.dto;

import com.anabeatriz.mepaga.model.User;

import java.time.LocalDate;

public record BillDTO(String description, LocalDate dueDate, Double amount, Boolean isPaid, Integer installments, User user) {
}
