package com.anabeatriz.mepaga.service;

import com.anabeatriz.mepaga.model.Conta;
import com.anabeatriz.mepaga.repository.ContaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContaService {

    private final ContaRepository contaRepository;

    public ContaService(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    public Conta salvar(Conta conta) {
        return contaRepository.save(conta);
    }

    public List<Conta> listarContas() {
        return contaRepository.findAll();
    }

    public void deletar(Long id) {
        Conta conta = contaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conta não encontrado"));
        contaRepository.delete(conta);
    }
}
