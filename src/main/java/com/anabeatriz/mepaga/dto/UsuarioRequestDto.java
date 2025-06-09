package com.anabeatriz.mepaga.dto;

import com.anabeatriz.mepaga.model.OrigemContato;

public record UsuarioRequestDto (
    String name,
    String email,
    String password,
    OrigemContato origemContato
){}
