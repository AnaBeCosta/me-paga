package com.anabeatriz.mepaga.dto;

import com.anabeatriz.mepaga.model.ContactSource;

public record UserRequestDTO(
    String name,
    String email,
    String password,
    ContactSource source
){}
