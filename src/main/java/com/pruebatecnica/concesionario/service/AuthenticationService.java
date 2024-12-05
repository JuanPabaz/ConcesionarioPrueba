package com.pruebatecnica.concesionario.service;

import com.pruebatecnica.concesionario.dto.UsuarioResponseDTO;
import com.pruebatecnica.concesionario.entities.Usuario;
import com.pruebatecnica.concesionario.enums.Role;
import com.pruebatecnica.concesionario.exceptions.BadUserCredentialsException;
import com.pruebatecnica.concesionario.exceptions.ObjectNotFoundException;
import com.pruebatecnica.concesionario.maps.IMapUsuario;
import com.pruebatecnica.concesionario.repositories.UsuarioRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthenticationService {

    private final UsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtServiceImpl jwtService;

    private final IMapUsuario mapUsuario;

    public AuthenticationService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, JwtServiceImpl jwtService, IMapUsuario mapUsuario) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.mapUsuario = mapUsuario;
    }


    public UsuarioResponseDTO saveUser(Usuario usuario) throws BadUserCredentialsException {
        if (usuarioRepository.findByUsername(usuario.getUsername()).isPresent()){
            throw new BadUserCredentialsException("Ya existe un usuario con este correo: "+ usuario.getUsername() + ".");
        }

        String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d/*\\-_.º!?¿'¡#!$%&]{6,}$";
        if (!usuario.getPassword().matches(passwordRegex)){
            throw new BadUserCredentialsException("La contraseña debe tener al menos 6 caracteres y contener al menos una letra y un número.");
        }
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (!usuario.getUsername().matches(emailRegex)){
            throw new BadUserCredentialsException("El correo no es valido.");
        }

        usuario.setRole(Role.ADMIN);
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        return mapUsuario.mapUsuario(usuarioRepository.save(usuario));
    }

    public String generateToken(String username) throws ObjectNotFoundException {
        return jwtService.generateToken(username);
    }

    public Map<String, Object> validateToken(String token){
        return jwtService.validateToken(token);
    }
}
