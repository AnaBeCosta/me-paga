package com.anabeatriz.mepaga.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private LocalDate dueDate;
    private Double amount;
    private Boolean isPaid;
    private Integer installments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Bill(String description, LocalDate localDate, Double amount, Boolean paid, Integer installments) {
        this.setDescription(description);
        this.setDueDate(localDate);
        this.setAmount(amount);
        this.setIsPaid(paid);
        this.setInstallments(installments);
    }
}
