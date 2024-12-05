package com.pruebatecnica.concesionario.service;

import com.pruebatecnica.concesionario.entities.RefreshToken;
import com.pruebatecnica.concesionario.entities.Usuario;
import com.pruebatecnica.concesionario.exceptions.ExpiredRefreshTokenException;
import com.pruebatecnica.concesionario.repositories.RefreshTokenRepository;
import com.pruebatecnica.concesionario.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RefreshTokenServiceTest {

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private RefreshTokenService refreshTokenService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createRefreshToken_debeCrearYGuardarToken() {
        // Arrange
        String username = "testuser";
        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        Instant beforeCreation = Instant.now();

        when(usuarioRepository.findByUsername(username)).thenReturn(Optional.of(usuario));
        when(refreshTokenRepository.save(any(RefreshToken.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(username);

        // Assert
        assertNotNull(refreshToken);
        assertEquals(usuario, refreshToken.getUsuario());
        assertNotNull(refreshToken.getToken());
        assertTrue(refreshToken.getExpiryDate().isAfter(
                beforeCreation),
                "El tiempo de expiración debe ser posterior al tiempo antes de la creación.");
        verify(usuarioRepository).findByUsername(username);
        verify(refreshTokenRepository).save(any(RefreshToken.class));
    }

    @Test
    void findByToken_debeRetornarRefreshTokenSiExiste() {
        // Arrange
        String token = UUID.randomUUID().toString();
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(token);

        when(refreshTokenRepository.findByToken(token)).thenReturn(Optional.of(refreshToken));

        // Act
        Optional<RefreshToken> result = refreshTokenService.findByToken(token);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(token, result.get().getToken());
        verify(refreshTokenRepository).findByToken(token);
    }

    @Test
    void findByToken_debeRetornarOptionalVacioSiNoExiste() {
        // Arrange
        String token = UUID.randomUUID().toString();
        when(refreshTokenRepository.findByToken(token)).thenReturn(Optional.empty());

        // Act
        Optional<RefreshToken> result = refreshTokenService.findByToken(token);

        // Assert
        assertTrue(result.isEmpty());
        verify(refreshTokenRepository).findByToken(token);
    }

}