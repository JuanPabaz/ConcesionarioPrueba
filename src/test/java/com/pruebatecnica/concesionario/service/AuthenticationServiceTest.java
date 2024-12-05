package com.pruebatecnica.concesionario.service;

import com.pruebatecnica.concesionario.dto.UsuarioResponseDTO;
import com.pruebatecnica.concesionario.entities.Usuario;
import com.pruebatecnica.concesionario.enums.Role;
import com.pruebatecnica.concesionario.exceptions.BadUserCredentialsException;
import com.pruebatecnica.concesionario.exceptions.ObjectNotFoundException;
import com.pruebatecnica.concesionario.maps.IMapUsuario;
import com.pruebatecnica.concesionario.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthenticationServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtServiceImpl jwtService;

    @Mock
    private IMapUsuario mapUsuario;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveUser_debeLanzarExcepcionSiUsuarioYaExiste() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setUsername("test@example.com");

        when(usuarioRepository.findByUsername("test@example.com")).thenReturn(Optional.of(usuario));

        // Act & Assert
        BadUserCredentialsException exception = assertThrows(BadUserCredentialsException.class, () -> authenticationService.saveUser(usuario));
        assertEquals("Ya existe un usuario con este correo: test@example.com.", exception.getMessage());
    }

    @Test
    void saveUser_debeLanzarExcepcionSiPasswordNoCumpleRegex() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setUsername("test@example.com");
        usuario.setPassword("123");

        when(usuarioRepository.findByUsername("test@example.com")).thenReturn(Optional.empty());

        // Act & Assert
        BadUserCredentialsException exception = assertThrows(BadUserCredentialsException.class, () -> authenticationService.saveUser(usuario));
        assertEquals("La contraseña debe tener al menos 6 caracteres y contener al menos una letra y un número.", exception.getMessage());
    }

    @Test
    void saveUser_debeLanzarExcepcionSiCorreoNoCumpleRegex() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setUsername("invalid_email");
        usuario.setPassword("Password1");

        when(usuarioRepository.findByUsername("invalid_email")).thenReturn(Optional.empty());

        // Act & Assert
        BadUserCredentialsException exception = assertThrows(BadUserCredentialsException.class, () -> authenticationService.saveUser(usuario));
        assertEquals("El correo no es valido.", exception.getMessage());
    }

    @Test
    void saveUser_debeGuardarUsuarioCorrectamente() throws BadUserCredentialsException {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setUsername("test@example.com");
        usuario.setPassword("Password1");

        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO();

        when(usuarioRepository.findByUsername("test@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("Password1")).thenReturn("encodedPassword");
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(mapUsuario.mapUsuario(any(Usuario.class))).thenReturn(usuarioResponseDTO);

        // Act
        UsuarioResponseDTO resultado = authenticationService.saveUser(usuario);

        // Assert
        assertNotNull(resultado);
        assertEquals("encodedPassword", usuario.getPassword());
        assertEquals(Role.ADMIN, usuario.getRole());
        verify(usuarioRepository).save(usuario);
    }
}