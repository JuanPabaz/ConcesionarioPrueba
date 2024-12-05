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

}