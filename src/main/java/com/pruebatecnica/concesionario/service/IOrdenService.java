package com.pruebatecnica.concesionario.service;

import com.pruebatecnica.concesionario.dto.OrdenDTO;
import com.pruebatecnica.concesionario.entities.Orden;

import java.util.List;

public interface IOrdenService {

    List<OrdenDTO> listarOrdenes();

    OrdenDTO crearOrden(Orden orden) throws Exception;
}
