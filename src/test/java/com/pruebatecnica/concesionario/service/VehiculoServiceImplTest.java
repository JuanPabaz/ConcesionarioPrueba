package com.pruebatecnica.concesionario.service;

import com.pruebatecnica.concesionario.dto.VehiculoDTO;
import com.pruebatecnica.concesionario.entities.Vehiculo;
import com.pruebatecnica.concesionario.exceptions.BadCreateRequest;
import com.pruebatecnica.concesionario.exceptions.ObjectNotFoundException;
import com.pruebatecnica.concesionario.maps.IMapVehiculo;
import com.pruebatecnica.concesionario.repositories.VehiculoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class VehiculoServiceImplTest {

    @Mock
    private VehiculoRepository vehiculoRepository;

    @Mock
    private IMapVehiculo mapVehiculo;

    @InjectMocks
    private VehiculoServiceImpl vehiculoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listarVehiculos_debeRetornarListaVehiculosDTO() {
        List<Vehiculo> vehiculos = Arrays.asList(new Vehiculo(), new Vehiculo());
        List<VehiculoDTO> vehiculoDTOs = Arrays.asList(new VehiculoDTO(), new VehiculoDTO());

        when(vehiculoRepository.findAll()).thenReturn(vehiculos);
        when(mapVehiculo.mapVehiculoList(vehiculos)).thenReturn(vehiculoDTOs);

        List<VehiculoDTO> result = vehiculoService.listarVehiculos();

        assertEquals(vehiculoDTOs, result);
        verify(vehiculoRepository).findAll();
        verify(mapVehiculo).mapVehiculoList(vehiculos);
    }

    @Test
    void buscarVehiculoPorId_debeRetornarVehiculoSiExiste() {
        Vehiculo vehiculo = new Vehiculo();
        when(vehiculoRepository.findById(1L)).thenReturn(Optional.of(vehiculo));

        Vehiculo result = vehiculoService.buscarVehiculoPorId(1L);

        assertEquals(vehiculo, result);
        verify(vehiculoRepository).findById(1L);
    }

    @Test
    void buscarVehiculoPorId_debeRetornarNullSiNoExiste() {
        when(vehiculoRepository.findById(1L)).thenReturn(Optional.empty());

        Vehiculo result = vehiculoService.buscarVehiculoPorId(1L);

        assertNull(result);
        verify(vehiculoRepository).findById(1L);
    }

    @Test
    void crearVehiculo_debeLanzarExcepcionSiPlacaYaExiste() {
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setPlaca("ABC123");

        when(vehiculoRepository.findByPlaca("ABC123")).thenReturn(Optional.of(new Vehiculo()));

        assertThrows(BadCreateRequest.class, () -> vehiculoService.crearVehiculo(vehiculo));
        verify(vehiculoRepository).findByPlaca("ABC123");
    }

    @Test
    void crearVehiculo_debeCrearVehiculoSiPlacaNoExiste() {
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setPlaca("ABC123");
        Vehiculo savedVehiculo = new Vehiculo();
        VehiculoDTO vehiculoDTO = new VehiculoDTO();

        when(vehiculoRepository.findByPlaca("ABC123")).thenReturn(Optional.empty());
        when(vehiculoRepository.save(vehiculo)).thenReturn(savedVehiculo);
        when(mapVehiculo.mapVehiculo(savedVehiculo)).thenReturn(vehiculoDTO);

        VehiculoDTO result = vehiculoService.crearVehiculo(vehiculo);

        assertEquals(vehiculoDTO, result);
        verify(vehiculoRepository).findByPlaca("ABC123");
        verify(vehiculoRepository).save(vehiculo);
        verify(mapVehiculo).mapVehiculo(savedVehiculo);
    }

    @Test
    void actualizarVehiculo_debeLanzarExcepcionSiVehiculoNoExiste() {
        Vehiculo vehiculo = new Vehiculo();

        when(vehiculoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> vehiculoService.actualizarVehiculo(1L, vehiculo));
        verify(vehiculoRepository).findById(1L);
    }

    @Test
    void actualizarVehiculo_debeActualizarVehiculoSiExiste() throws ObjectNotFoundException {
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setPlaca("ABC123");
        Vehiculo existingVehiculo = new Vehiculo();
        Vehiculo updatedVehiculo = new Vehiculo();
        VehiculoDTO vehiculoDTO = new VehiculoDTO();

        when(vehiculoRepository.findById(1L)).thenReturn(Optional.of(existingVehiculo));
        when(vehiculoRepository.save(vehiculo)).thenReturn(updatedVehiculo);
        when(mapVehiculo.mapVehiculo(updatedVehiculo)).thenReturn(vehiculoDTO);

        VehiculoDTO result = vehiculoService.actualizarVehiculo(1L, vehiculo);

        assertEquals(vehiculoDTO, result);
        verify(vehiculoRepository).findById(1L);
        verify(vehiculoRepository).save(vehiculo);
        verify(mapVehiculo).mapVehiculo(updatedVehiculo);
    }

    @Test
    void eliminarVehiculo_debeLanzarExcepcionSiVehiculoNoExiste() {
        when(vehiculoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> vehiculoService.eliminarVehiculo(1L));
        verify(vehiculoRepository).findById(1L);
    }

    @Test
    void eliminarVehiculo_debeEliminarVehiculoSiExiste() throws ObjectNotFoundException {
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setPlaca("ABC123");

        when(vehiculoRepository.findById(1L)).thenReturn(Optional.of(vehiculo));
        when(vehiculoRepository.findByPlaca("ABC123")).thenReturn(Optional.empty());

        boolean result = vehiculoService.eliminarVehiculo(1L);

        assertTrue(result);
        verify(vehiculoRepository).findById(1L);
        verify(vehiculoRepository).findByPlaca("ABC123");
        verify(vehiculoRepository).deleteById(1L);
    }
}