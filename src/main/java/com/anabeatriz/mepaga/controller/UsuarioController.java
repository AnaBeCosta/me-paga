package com.anabeatriz.mepaga.controller;

import com.anabeatriz.mepaga.dto.LoginRequestDto;
import com.anabeatriz.mepaga.dto.LoginResponseDto;
import com.anabeatriz.mepaga.dto.UsuarioRequestDto;
import com.anabeatriz.mepaga.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@RequestBody UsuarioRequestDto dto){
        usuarioService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto){
        return usuarioService.login(loginRequestDto);
    }

    @DeleteMapping("/deletar/{id}")
    public void deletarUsuario(@PathVariable Long id) {
        usuarioService.deletar(id);
    }
}
