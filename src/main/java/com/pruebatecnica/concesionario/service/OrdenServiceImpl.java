package com.pruebatecnica.concesionario.service;

import com.pruebatecnica.concesionario.dto.OrdenDTO;
import com.pruebatecnica.concesionario.entities.Orden;
import com.pruebatecnica.concesionario.entities.Vehiculo;
import com.pruebatecnica.concesionario.exceptions.BadCreateRequest;
import com.pruebatecnica.concesionario.exceptions.ObjectNotFoundException;
import com.pruebatecnica.concesionario.maps.IMapOrden;
import com.pruebatecnica.concesionario.repositories.OrdenRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrdenServiceImpl implements IOrdenService{

    private final OrdenRepository ordenRepository;

    private final IMapOrden mapOrden;

    private final IVehiculoService vehiculoService;

    public OrdenServiceImpl(OrdenRepository ordenRepository, IMapOrden mapOrden, IVehiculoService vehiculoService) {
        this.ordenRepository = ordenRepository;
        this.mapOrden = mapOrden;
        this.vehiculoService = vehiculoService;
    }

    @Override
    public List<OrdenDTO> listarOrdenes() {
        return mapOrden.mapOrdenList(ordenRepository.findAll());
    }

    @Override
    public OrdenDTO crearOrden(Orden orden) throws BadCreateRequest, ObjectNotFoundException {
        Vehiculo vehiculo = vehiculoService.buscarVehiculoPorId(orden.getVehiculo().getId());
        if (vehiculo == null) {
            throw new ObjectNotFoundException("El vehiculo no existe");
        }

        Optional<List<Orden>> optionalOrdenActivaList = ordenRepository.encontrarPorIdVehiculoYOrdenActiva(vehiculo.getId());
        if (optionalOrdenActivaList.isPresent()) {
            throw new BadCreateRequest("El vehiculo ya tiene una orden activa");
        }

        LocalDate fechaActual = LocalDate.now();
        if (orden.getFechaOrden().isAfter(fechaActual)){
            throw new BadCreateRequest("No se puede crear una orden con fecha mayor a la actual:" + fechaActual);
        }

        return mapOrden.mapOrden(ordenRepository.save(orden));
    }

    @Override
    public OrdenDTO actualizarOrden(Long id,Orden orden) throws ObjectNotFoundException {
        Optional<Orden> ordenExistente = ordenRepository.findById(id);
        if (ordenExistente.isEmpty()) {
            throw new ObjectNotFoundException("Orden no encontrada");
        }

        orden.setId(id);
        return mapOrden.mapOrden(ordenRepository.save(orden));
    }

    @Override
    public boolean eliminarOrden(Long id) throws ObjectNotFoundException {
        Optional<Orden> ordenOptional = ordenRepository.findById(id);
        if (ordenOptional.isEmpty()) {
            throw new ObjectNotFoundException("Orden no encontrada");
        }

        Orden orden = ordenOptional.get();
        if (orden.getActiva()){
            throw new BadCreateRequest("No se puede eliminar una orden activa");
        }
        ordenRepository.deleteById(id);
        return true;
    }

}
