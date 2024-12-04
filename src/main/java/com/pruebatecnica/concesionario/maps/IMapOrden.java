package com.pruebatecnica.concesionario.maps;

import com.pruebatecnica.concesionario.dto.OrdenDTO;
import com.pruebatecnica.concesionario.entities.Orden;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IMapOrden {

    @Mappings({
            @Mapping(source = "id",target = "id"),
            @Mapping(source = "vehiculo.id", target = "idVehiculo"),
            @Mapping(source = "fechaOrden", target = "fechaOrden"),
            @Mapping(source = "tipo", target = "tipo"),
            @Mapping(source = "activa", target = "activa")
    })
    OrdenDTO mapOrden(Orden orden);

    List<OrdenDTO> mapOrdenList(List<Orden> ordenList);
}
