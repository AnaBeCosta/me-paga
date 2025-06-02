package com.anabeatriz.mepaga.repository;

import com.anabeatriz.mepaga.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepository extends JpaRepository<Conta, Long> {
}
