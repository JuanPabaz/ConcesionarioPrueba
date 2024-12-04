package com.pruebatecnica.concesionario.controllers;

import com.pruebatecnica.concesionario.dto.VehiculoDTO;

import com.pruebatecnica.concesionario.entities.Vehiculo;
import com.pruebatecnica.concesionario.service.IVehiculoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<?> crearVehiculo(@RequestBody Vehiculo vehiculo) {
        return ResponseEntity.ok(vehiculoService.crearVehiculo(vehiculo));
    }
}
