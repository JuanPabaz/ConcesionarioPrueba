package com.pruebatecnica.concesionario.service;

import com.pruebatecnica.concesionario.dto.VehiculoDTO;
import com.pruebatecnica.concesionario.entities.Vehiculo;
import com.pruebatecnica.concesionario.maps.IMapVehiculo;
import com.pruebatecnica.concesionario.repositories.VehiculoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Vehiculo buscarVehiculoPorId(Long id) {
        Optional<Vehiculo> vehiculoOptional = vehiculoRepository.findById(id);
        return vehiculoOptional.orElse(null);
    }
}
