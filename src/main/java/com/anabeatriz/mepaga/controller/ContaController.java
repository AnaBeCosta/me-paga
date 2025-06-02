package com.anabeatriz.mepaga.controller;


import com.anabeatriz.mepaga.model.Conta;
import com.anabeatriz.mepaga.service.ContaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contas")
public class ContaController {

    private final ContaService contaService;

    public ContaController(ContaService contaService){
        this.contaService = contaService;
    }

    @PostMapping
    public Conta salvar(@RequestBody Conta conta){
        return contaService.salvar(conta);
    }

    @GetMapping
    public List<Conta> listarContas() {
        return contaService.listarContas();
    }
}
