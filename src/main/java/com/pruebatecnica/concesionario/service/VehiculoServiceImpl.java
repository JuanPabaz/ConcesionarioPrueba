package com.pruebatecnica.concesionario.service;

import com.pruebatecnica.concesionario.dto.VehiculoDTO;
import com.pruebatecnica.concesionario.maps.IMapVehiculo;
import com.pruebatecnica.concesionario.repositories.VehiculoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehiculoServiceImpl implements IVehiculoService {

    private final VehiculoRepository vehiculoRepository;

    private final IMapVehiculo mapVehiculo;

    public VehiculoServiceImpl(VehiculoRepository vehiculoRepository, IMapVehiculo mapVehiculo) {
        this.vehiculoRepository = vehiculoRepository;
        this.mapVehiculo = mapVehiculo;
    }


    @Override
    public List<VehiculoDTO> listarVehiculos() {
        return mapVehiculo.mapVehiculoList(vehiculoRepository.findAll());
    }
}