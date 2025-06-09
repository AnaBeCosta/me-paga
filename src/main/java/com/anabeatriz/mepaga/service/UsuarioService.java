package com.anabeatriz.mepaga.service;

import com.anabeatriz.mepaga.dto.LoginRequestDto;
import com.anabeatriz.mepaga.dto.LoginResponseDto;
import com.anabeatriz.mepaga.dto.UsuarioRequestDto;
import com.anabeatriz.mepaga.model.Usuario;
import com.anabeatriz.mepaga.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public void cadastrar(UsuarioRequestDto dto) {
        if (usuarioRepository.findByEmail(dto.email()).isPresent()) {
            throw new RuntimeException("Email já cadastrado!");
        }

        Usuario user = new Usuario();
        user.setEmail(dto.email());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setName(dto.name());
        user.setOrigemContato(dto.origemContato());
        usuarioRepository.save(user);
    }

    public LoginResponseDto login(LoginRequestDto dto) {
        var authToken = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
        authenticationManager.authenticate(authToken);

        var usuario = usuarioRepository.findByEmail(dto.email())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        var token = tokenService.generateToken(usuario);
        return new LoginResponseDto(token);
    }

    public void deletar(Long id){
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuarioRepository.delete(usuario);
    }
}
