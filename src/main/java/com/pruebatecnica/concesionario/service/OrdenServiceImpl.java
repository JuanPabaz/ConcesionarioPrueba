package com.pruebatecnica.concesionario.service;

import com.pruebatecnica.concesionario.dto.OrdenDTO;
import com.pruebatecnica.concesionario.maps.IMapOrden;
import com.pruebatecnica.concesionario.repositories.OrdenRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdenServiceImpl implements IOrdenService{

    private final OrdenRepository ordenRepository;

    private final IMapOrden mapOrden;

    public OrdenServiceImpl(OrdenRepository ordenRepository, IMapOrden mapOrden) {
        this.ordenRepository = ordenRepository;
        this.mapOrden = mapOrden;
    }

    @Override
    public List<OrdenDTO> listarOrdenes() {
        return mapOrden.mapOrdenList(ordenRepository.findAll());
    }
}
