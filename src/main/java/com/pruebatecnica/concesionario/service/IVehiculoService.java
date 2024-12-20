package com.pruebatecnica.concesionario.service;

import com.pruebatecnica.concesionario.dto.VehiculoDTO;
import com.pruebatecnica.concesionario.entities.Vehiculo;
import com.pruebatecnica.concesionario.exceptions.ObjectNotFoundException;

import java.util.List;

public interface IVehiculoService {

    List<VehiculoDTO> listarVehiculos();

    Vehiculo buscarVehiculoPorId(Long id);

    VehiculoDTO crearVehiculo(Vehiculo vehiculo);

    VehiculoDTO actualizarVehiculo(Long id,Vehiculo vehiculo) throws ObjectNotFoundException;

    boolean eliminarVehiculo(Long id) throws ObjectNotFoundException;


}
