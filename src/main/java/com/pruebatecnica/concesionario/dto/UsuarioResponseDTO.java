package com.pruebatecnica.concesionario.dto;

import com.pruebatecnica.concesionario.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioResponseDTO {

    private Integer idUsuario;
    private String nombreCompleto;
    private Role role;
    private String username;

}
