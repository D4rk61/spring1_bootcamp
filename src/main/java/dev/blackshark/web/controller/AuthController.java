package dev.blackshark.web.controller;

import dev.blackshark.domain.dto.MensajeDTO;
import dev.blackshark.domain.dto.NuevoUsuarioDTO;
import dev.blackshark.domain.service.RolService;
import dev.blackshark.domain.service.UsuarioService;
import dev.blackshark.persistance.entity.Rol;
import dev.blackshark.persistance.entity.RolNombre;
import dev.blackshark.persistance.entity.Usuario;
import dev.blackshark.security.domain.dto.JwtDTO;
import dev.blackshark.security.domain.dto.LoginUsuarioDTO;
import dev.blackshark.security.jwt.JwtProvider;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    RolService rolService;
    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("")
    public ResponseEntity<MensajeDTO> nuevo(@Valid @RequestBody NuevoUsuarioDTO nuevoUsuarioDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new MensajeDTO("Error al crear usuario"));
        }

        if(usuarioService.existsByUsername(nuevoUsuarioDTO.getUsername())) {
            return ResponseEntity.badRequest().body(new MensajeDTO("El usuario ya existe"));
        }

        if(usuarioService.existsByEmail(nuevoUsuarioDTO.getEmail())) {
            return ResponseEntity.badRequest().body(new MensajeDTO("El email ya existe"));
        }

        Usuario
                usuario = new Usuario(nuevoUsuarioDTO.getNombre(), nuevoUsuarioDTO.getUsername(), nuevoUsuarioDTO.getEmail(),
                passwordEncoder.encode(nuevoUsuarioDTO.getPassword()));

        Set<Rol> roles = new HashSet<>();
        roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());
        if(nuevoUsuarioDTO.getRoles().contains("admin")) {
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
        }

        usuario.setRoles(roles);
        usuarioService.save(usuario);

        return ResponseEntity.created(null).body(new MensajeDTO("Usuario creado"));

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginUsuarioDTO loginUsuarioDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new MensajeDTO("Error al crear usuario"));
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUsuarioDTO.getUsername(), loginUsuarioDTO.getPassword()));

        String token = jwtProvider.generateToken(authentication);
        JwtDTO jwtDTO = new JwtDTO(token);

        // return new ResponseEntity<>(jwtDTO, HttpStatus.ACCEPTED);
        return ResponseEntity.accepted().body(jwtDTO);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtDTO> refresh(@RequestBody JwtDTO jwtDTO) throws ParseException {
        String token = jwtProvider.refreshToken(jwtDTO);
        JwtDTO jwtDTO1 = new JwtDTO(token);
        return ResponseEntity.accepted().body(jwtDTO1);
    }
}
