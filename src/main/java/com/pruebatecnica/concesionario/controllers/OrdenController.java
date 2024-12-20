package com.pruebatecnica.concesionario.controllers;

import com.pruebatecnica.concesionario.dto.OrdenDTO;
import com.pruebatecnica.concesionario.entities.Orden;
import com.pruebatecnica.concesionario.exceptions.BadCreateRequest;
import com.pruebatecnica.concesionario.exceptions.ObjectNotFoundException;
import com.pruebatecnica.concesionario.service.IOrdenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orden")
@CrossOrigin(origins = "*", allowedHeaders = {"Authorization", "Content-Type"})
public class OrdenController {

    private final IOrdenService ordenService;

    public OrdenController(IOrdenService ordenService) {
        this.ordenService = ordenService;
    }

    @GetMapping
    public List<OrdenDTO> listarOrdenes(){
        return ordenService.listarOrdenes();
    }

    @PostMapping
    public ResponseEntity<?> crearOrden(@RequestBody Orden orden) throws BadCreateRequest, ObjectNotFoundException {
        return ResponseEntity.ok(ordenService.crearOrden(orden));
    }

    @PutMapping("/{id_orden}")
    public ResponseEntity<?> actualizarOrden(@PathVariable Long id_orden, @RequestBody Orden orden) throws BadCreateRequest, ObjectNotFoundException {
        return ResponseEntity.ok(ordenService.actualizarOrden(id_orden, orden));
    }

    @DeleteMapping("/{id_orden}")
    public ResponseEntity<?> eliminarOrden(@PathVariable Long id_orden) throws ObjectNotFoundException {
        return ResponseEntity.ok(ordenService.eliminarOrden(id_orden));
    }
}
