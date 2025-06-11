package com.sw2parcial2.biblioerpms.controller;

import com.sw2parcial2.biblioerpms.dto.request.LoginRequest;
import com.sw2parcial2.biblioerpms.dto.request.SignupRequest;
import com.sw2parcial2.biblioerpms.dto.response.JwtResponse;
import com.sw2parcial2.biblioerpms.dto.response.MessageResponse;
import com.sw2parcial2.biblioerpms.entity.Rol;
import com.sw2parcial2.biblioerpms.entity.Usuario;
import com.sw2parcial2.biblioerpms.repository.RolRepository;
import com.sw2parcial2.biblioerpms.repository.UsuarioRepository;
import com.sw2parcial2.biblioerpms.security.JwtUtils;
import com.sw2parcial2.biblioerpms.security.UserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
//@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        System.out.println("METODO SIGN IN");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getName(),
                userDetails.getEmail(),
                userDetails.getAuthorities()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (usuarioRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: Email ya está en uso!"));
        }

        // Crear nueva cuenta de usuario
        Usuario usuario = Usuario.builder()
                .name(signUpRequest.getName())
                .email(signUpRequest.getEmail())
                .password(encoder.encode(signUpRequest.getPassword()))
                .build();

        String strRole = signUpRequest.getRole();
        Rol rol = null;

        if (strRole == null) {
            rol = rolRepository.findByName("USER")
                    .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
        } else {
            switch (strRole) {
                case "admin":
                    rol = rolRepository.findByName("ADMIN")
                            .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
                    break;
                case "librarian":
                    rol = rolRepository.findByName("LIBRARIAN")
                            .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
                    break;
                default:
                    rol = rolRepository.findByName("USER")
                            .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
            }
        }

        usuario.setRol(rol);
        usuarioRepository.save(usuario);

        return ResponseEntity.ok(new MessageResponse("Usuario registrado exitosamente!"));
    }
}
