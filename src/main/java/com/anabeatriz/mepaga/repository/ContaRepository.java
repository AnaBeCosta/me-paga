package com.anabeatriz.mepaga.repository;

import com.anabeatriz.mepaga.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ContaRepository extends JpaRepository<Conta, Long> {

    List<Conta> findByUsuarioId(Long id);

    @Transactional
    void deleteByUsuarioId(Long id);
}
