package com.pruebatecnica.concesionario.service;

import com.pruebatecnica.concesionario.dto.OrdenDTO;
import com.pruebatecnica.concesionario.entities.Orden;
import com.pruebatecnica.concesionario.entities.Vehiculo;
import com.pruebatecnica.concesionario.exceptions.BadCreateRequest;
import com.pruebatecnica.concesionario.exceptions.ObjectNotFoundException;
import com.pruebatecnica.concesionario.maps.IMapOrden;
import com.pruebatecnica.concesionario.repositories.OrdenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrdenServiceImplTest {

    @Mock
    private OrdenRepository ordenRepository;

    @Mock
    private IMapOrden mapOrden;

    @Mock
    private IVehiculoService vehiculoService;

    @InjectMocks
    private OrdenServiceImpl ordenService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listarOrdenes_debeRetornarListaDeOrdenesDTO() {
        // Arrange
        List<Orden> ordenes = List.of(new Orden(), new Orden());
        List<OrdenDTO> ordenesDTO = List.of(new OrdenDTO(), new OrdenDTO());
        when(ordenRepository.findAll()).thenReturn(ordenes);
        when(mapOrden.mapOrdenList(ordenes)).thenReturn(ordenesDTO);

        // Act
        List<OrdenDTO> resultado = ordenService.listarOrdenes();

        // Assert
        assertNotNull(resultado);
        assertEquals(ordenesDTO, resultado);
        verify(ordenRepository).findAll();
        verify(mapOrden).mapOrdenList(ordenes);
    }

    @Test
    void crearOrden_debeLanzarExcepcionSiVehiculoNoExiste() {
        // Arrange
        Orden orden = new Orden();
        orden.setVehiculo(new Vehiculo());
        when(vehiculoService.buscarVehiculoPorId(anyLong())).thenReturn(null);

        // Act & Assert
        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> ordenService.crearOrden(orden));
        assertEquals("El vehiculo no existe", exception.getMessage());
    }

}