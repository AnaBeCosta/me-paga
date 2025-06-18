package com.anabeatriz.mepaga.controller;


import com.anabeatriz.mepaga.model.Conta;
import com.anabeatriz.mepaga.model.Usuario;
import com.anabeatriz.mepaga.service.ContaService;
import com.anabeatriz.mepaga.service.UsuarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contas")
public class ContaController {

    private final ContaService contaService;
    private final UsuarioService usuarioService;

    public ContaController(ContaService contaService, UsuarioService usuarioService){
        this.contaService = contaService;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/salvar")
    public Conta salvar(@RequestBody Conta conta) {
        Usuario usuarioLogado = usuarioService.getUsuarioLogado(); // Pega pelo token
        conta.setUsuario(usuarioLogado); // Associa à conta
        return contaService.salvar(conta);
    }

    @GetMapping("/listar/{idUser}")
    public List<Conta> listarContas(@PathVariable Long idUser) {
        return contaService.listarContas(idUser);
    }


    @DeleteMapping("/deletar/{id}")
    public void deletarConta(@PathVariable Long id) {
        contaService.deletar(id);
    }
}
