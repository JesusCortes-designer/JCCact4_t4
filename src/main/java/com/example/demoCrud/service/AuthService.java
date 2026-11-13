package com.example.demoCrud.service;

import com.example.demoCrud.dto.AuthResponse;
import com.example.demoCrud.dto.LoginRequest;
import com.example.demoCrud.dto.RegisterRequest;
import com.example.demoCrud.dto.UserResponse;
import com.example.demoCrud.exception.ApiException;
import com.example.demoCrud.models.Usuario;
import com.example.demoCrud.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(UsuarioRepository usuarioRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Transactional
    public UserResponse registrar(RegisterRequest req) {
        if (usuarioRepository.existsByUsername(req.getUsername())) {
            throw new ApiException(HttpStatus.CONFLICT, "El username ya está en uso");
        }
        if (usuarioRepository.existsByEmail(req.getEmail())) {
            throw new ApiException(HttpStatus.CONFLICT, "El email ya está registrado");
        }
        Usuario u = new Usuario();
        u.setUsername(req.getUsername());
        u.setEmail(req.getEmail());
        u.setPassword(passwordEncoder.encode(req.getPassword()));
        return UserResponse.from(usuarioRepository.save(u));
    }

    public AuthResponse login(LoginRequest req) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
        } catch (AuthenticationException e) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "Usuario o contraseña incorrectos");
        }
        return new AuthResponse(jwtService.generarToken(req.getUsername()), "Bearer", req.getUsername());
    }
}
