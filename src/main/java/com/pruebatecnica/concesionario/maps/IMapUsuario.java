package com.pruebatecnica.concesionario.maps;

import com.pruebatecnica.concesionario.dto.UsuarioResponseDTO;
import com.pruebatecnica.concesionario.entities.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IMapUsuario {

    @Mappings({
            @Mapping(source = "idUsuario", target = "idUsuario"),
            @Mapping(source = "nombreCompleto",target = "nombreCompleto"),
            @Mapping(source = "role", target = "role"),
            @Mapping(source = "username", target = "username")
    })
    UsuarioResponseDTO mapUsuario(Usuario usuario);

    List<UsuarioResponseDTO> mapUsuarioList(List<Usuario> usuarioList);
}
