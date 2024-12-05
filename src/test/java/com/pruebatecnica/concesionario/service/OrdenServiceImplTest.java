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

    @Test
    void crearOrden_debeLanzarExcepcionSiVehiculoTieneOrdenActiva() {
        // Arrange
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(1L);

        Orden orden = new Orden();
        orden.setVehiculo(vehiculo);

        when(vehiculoService.buscarVehiculoPorId(1L)).thenReturn(vehiculo);
        when(ordenRepository.encontrarPorIdVehiculoYOrdenActiva(vehiculo.getId())).thenReturn(Optional.of(List.of(new Orden())));

        // Act & Assert
        BadCreateRequest exception = assertThrows(BadCreateRequest.class, () -> ordenService.crearOrden(orden));
        assertEquals("El vehiculo ya tiene una orden activa", exception.getMessage());
    }

    @Test
    void crearOrden_debeLanzarExcepcionSiFechaOrdenEsFutura() {
        // Arrange
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(1L);

        Orden orden = new Orden();
        orden.setVehiculo(vehiculo);
        orden.setFechaOrden(LocalDate.now().plusDays(1));

        when(vehiculoService.buscarVehiculoPorId(1L)).thenReturn(vehiculo);

        // Act & Assert
        BadCreateRequest exception = assertThrows(BadCreateRequest.class, () -> ordenService.crearOrden(orden));
        assertEquals("No se puede crear una orden con fecha mayor a la actual:" + LocalDate.now(), exception.getMessage());
    }

    @Test
    void crearOrden_debeCrearOrdenCorrectamente() throws BadCreateRequest, ObjectNotFoundException {
        // Arrange
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(1L);

        Orden orden = new Orden();
        orden.setVehiculo(vehiculo);
        orden.setFechaOrden(LocalDate.now());

        OrdenDTO ordenDTO = new OrdenDTO();

        when(vehiculoService.buscarVehiculoPorId(1L)).thenReturn(vehiculo);
        when(ordenRepository.encontrarPorIdVehiculoYOrdenActiva(vehiculo.getId())).thenReturn(Optional.empty());
        when(ordenRepository.save(orden)).thenReturn(orden);
        when(mapOrden.mapOrden(orden)).thenReturn(ordenDTO);

        // Act
        OrdenDTO resultado = ordenService.crearOrden(orden);

        // Assert
        assertNotNull(resultado);
        assertEquals(ordenDTO, resultado);
        verify(ordenRepository).save(orden);
        verify(mapOrden).mapOrden(orden);
    }


    @Test
    void eliminarOrden_debeLanzarExcepcionSiOrdenNoExiste() {
        // Arrange
        when(ordenRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> ordenService.eliminarOrden(1L));
        assertEquals("Orden no encontrada", exception.getMessage());
    }

    @Test
    void eliminarOrden_debeLanzarExcepcionSiOrdenEstaActiva() {
        // Arrange
        Orden orden = new Orden();
        orden.setId(1L);
        orden.setActiva(true);

        when(ordenRepository.findById(1L)).thenReturn(Optional.of(orden));

        // Act & Assert
        BadCreateRequest exception = assertThrows(BadCreateRequest.class, () -> ordenService.eliminarOrden(1L));
        assertEquals("No se puede eliminar una orden activa", exception.getMessage());
    }

    @Test
    void eliminarOrden_debeEliminarOrdenCorrectamente() throws ObjectNotFoundException {
        // Arrange
        Orden orden = new Orden();
        orden.setId(1L);
        orden.setActiva(false);

        when(ordenRepository.findById(1L)).thenReturn(Optional.of(orden));

        // Act
        boolean resultado = ordenService.eliminarOrden(1L);

        // Assert
        assertTrue(resultado);
        verify(ordenRepository).deleteById(1L);
    }

}