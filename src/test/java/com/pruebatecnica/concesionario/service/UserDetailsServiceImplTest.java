package com.pruebatecnica.concesionario.service;

import com.pruebatecnica.concesionario.entities.Usuario;
import com.pruebatecnica.concesionario.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserDetailsServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsername_debeRetornarUserDetailsSiUsuarioExiste() {
        // Arrange
        String username = "testuser";
        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword("password");

        when(usuarioRepository.findByUsername(username)).thenReturn(Optional.of(usuario));

        // Act
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Assert
        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
        verify(usuarioRepository).findByUsername(username);
    }

}