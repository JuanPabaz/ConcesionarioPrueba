package com.pruebatecnica.concesionario.service;

import com.pruebatecnica.concesionario.dto.OrdenDTO;
import com.pruebatecnica.concesionario.entities.Orden;
import com.pruebatecnica.concesionario.exceptions.BadCreateRequest;
import com.pruebatecnica.concesionario.exceptions.ObjectNotFoundException;

import java.util.List;

public interface IOrdenService {

    List<OrdenDTO> listarOrdenes();

    OrdenDTO crearOrden(Orden orden) throws BadCreateRequest, ObjectNotFoundException;

    OrdenDTO actualizarOrden(Long id, Orden orden) throws ObjectNotFoundException;

    boolean eliminarOrden(Long id) throws ObjectNotFoundException;

}
