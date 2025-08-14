package com.gustavofarias.manageproductsbackend.controller;

import com.gustavofarias.manageproductsbackend.dto.*;
import com.gustavofarias.manageproductsbackend.entity.Usuario;
import com.gustavofarias.manageproductsbackend.repository.UsuarioRepository;
import com.gustavofarias.manageproductsbackend.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final UsuarioRepository userRepo;
    private final PasswordEncoder encoder;

    public AuthController(JwtUtil jwtUtil, UsuarioRepository userRepo, PasswordEncoder encoder) {
        this.jwtUtil = jwtUtil;
        this.userRepo = userRepo;
        this.encoder = encoder;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest req) {
        var userOpt = userRepo.findByUsername(req.username());
        if (userOpt.isEmpty() || !encoder.matches(req.password(), userOpt.get().getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = jwtUtil.generateToken(req.username());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest req) {
        if (userRepo.findByUsername(req.username()).isPresent()) {
            return ResponseEntity.badRequest().body("Usuário já existe");
        }
        Usuario u = new Usuario();
        u.setUsername(req.username());
        u.setPassword(encoder.encode(req.password()));
        u.setRole("ROLE_USER");
        userRepo.save(u);
        return ResponseEntity.ok().build();
    }
}
