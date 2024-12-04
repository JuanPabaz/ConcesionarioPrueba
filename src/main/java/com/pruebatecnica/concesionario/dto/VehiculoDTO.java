package com.pruebatecnica.concesionario.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class VehiculoDTO {

    private Long id;

    private String placa;

    private String marca;

    private String modelo;
}
