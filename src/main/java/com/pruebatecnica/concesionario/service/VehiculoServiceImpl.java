package com.pruebatecnica.concesionario.service;

import com.pruebatecnica.concesionario.dto.VehiculoDTO;
import com.pruebatecnica.concesionario.entities.Vehiculo;
import com.pruebatecnica.concesionario.exceptions.BadCreateRequest;
import com.pruebatecnica.concesionario.exceptions.ObjectNotFoundException;
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

    @Override
    public VehiculoDTO crearVehiculo(Vehiculo vehiculo) {
        Optional<Vehiculo> vehiculoPorPlacaOptional = vehiculoRepository.findByPlaca(vehiculo.getPlaca());
        if (vehiculoPorPlacaOptional.isPresent()) {
            throw new BadCreateRequest("Ya existe un vehiculo con esta placa");
        }
        return mapVehiculo.mapVehiculo(vehiculoRepository.save(vehiculo));
    }

    @Override
    public VehiculoDTO actualizarVehiculo(Long id,Vehiculo vehiculo) throws ObjectNotFoundException {
        Optional<Vehiculo> vehiculoExistente = vehiculoRepository.findById(id);
        if (vehiculoExistente.isEmpty()) {
            throw new ObjectNotFoundException("Vehículo no encontrado");
        }
        vehiculo.setId(id);
        return mapVehiculo.mapVehiculo(vehiculoRepository.save(vehiculo));
    }

    @Override
    public boolean eliminarVehiculo(Long id) throws ObjectNotFoundException {
        Optional<Vehiculo> vehiculoOptional = vehiculoRepository.findById(id);
        if (vehiculoOptional.isEmpty()) {
            throw new ObjectNotFoundException("Vehículo no encontrado");
        }

        Vehiculo vehiculo = vehiculoOptional.get();
        Optional<Vehiculo> vehiculoPorPlacaOptional = vehiculoRepository.findByPlaca(vehiculo.getPlaca());
        if (vehiculoPorPlacaOptional.isPresent()) {
            throw new BadCreateRequest("No se puede eliminar un vehiculo con una orden activa");
        }

        vehiculoRepository.deleteById(id);
        return true;
    }

}
