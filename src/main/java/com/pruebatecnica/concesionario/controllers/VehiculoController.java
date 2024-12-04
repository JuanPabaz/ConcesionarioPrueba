package com.pruebatecnica.concesionario.controllers;

import com.pruebatecnica.concesionario.dto.VehiculoDTO;

import com.pruebatecnica.concesionario.service.IVehiculoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/vehiculo")
public class VehiculoController {

    private final IVehiculoService vehiculoService;

    public VehiculoController(IVehiculoService vehiculoService) {
        this.vehiculoService = vehiculoService;
    }

    @GetMapping("")
    public List<VehiculoDTO> ListarVehiculos() {
        return vehiculoService.listarVehiculos();
    }
}
