package com.anabeatriz.mepaga.dto;

import com.anabeatriz.mepaga.model.ContactSource;

public record RegisterDTO(String login, String password, String name, String phone, ContactSource source) {
}
