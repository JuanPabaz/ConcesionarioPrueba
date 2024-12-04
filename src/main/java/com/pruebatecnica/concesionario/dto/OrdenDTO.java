package com.pruebatecnica.concesionario.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class OrdenDTO {


    private Long id;

    private Long idVehiculo;

    private LocalDate fechaOrden;

    private String tipo;

    private Boolean activa;

}
