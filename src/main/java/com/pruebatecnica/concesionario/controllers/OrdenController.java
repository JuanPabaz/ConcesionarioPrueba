package com.pruebatecnica.concesionario.controllers;

import com.pruebatecnica.concesionario.dto.OrdenDTO;
import com.pruebatecnica.concesionario.service.IOrdenService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orden")
public class OrdenController {

    private final IOrdenService ordenService;

    public OrdenController(IOrdenService ordenService) {
        this.ordenService = ordenService;
    }

    @GetMapping
    public List<OrdenDTO> listarOrdenes(){
        return ordenService.listarOrdenes();
    }
}
