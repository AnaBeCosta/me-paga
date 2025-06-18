package com.anabeatriz.mepaga.service;

import com.anabeatriz.mepaga.dto.LoginRequestDto;
import com.anabeatriz.mepaga.dto.LoginResponseDto;
import com.anabeatriz.mepaga.dto.UsuarioRequestDto;
import com.anabeatriz.mepaga.model.Usuario;
import com.anabeatriz.mepaga.repository.ContaRepository;
import com.anabeatriz.mepaga.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ContaRepository contaRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository,
                          ContaRepository contaRepository,
                          PasswordEncoder passwordEncoder,
                          TokenService tokenService,
                          AuthenticationManager authenticationManager) {
        this.usuarioRepository = usuarioRepository;
        this.contaRepository = contaRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    public void cadastrar(UsuarioRequestDto dto) {
        if (usuarioRepository.findByEmail(dto.email()).isPresent()) {
            throw new RuntimeException("Email já cadastrado!");
        }

        Usuario user = new Usuario();
        user.setEmail(dto.email());
        user.setPassword(passwordEncoder.encode(dto.password())); // Garante que a senha seja salva corretamente
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

    public Usuario getUsuarioLogado() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Usuário não autenticado");
        }

        var email = authentication.getName();
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    @Transactional
    public void deletar(Long id){
        if (contaRepository.findByUsuarioId(id).isEmpty()) {
            // Exclui diretamente se não houver contas associadas
            usuarioRepository.deleteById(id);
        } else {
            throw new RuntimeException("Usuário possui contas associadas e não pode ser excluído.");
        }
    }
}
