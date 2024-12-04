package com.pruebatecnica.concesionario.maps;

import com.pruebatecnica.concesionario.dto.OrdenDTO;
import com.pruebatecnica.concesionario.dto.VehiculoDTO;
import com.pruebatecnica.concesionario.entities.Orden;
import com.pruebatecnica.concesionario.entities.Vehiculo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IMapVehiculo {

    @Mappings({
            @Mapping(source = "id",target = "id"),
            @Mapping(source = "placa", target = "placa"),
            @Mapping(source = "marca", target = "marca"),
            @Mapping(source = "modelo", target = "modelo")
    })
    VehiculoDTO mapVehiculo(Vehiculo vehiculo);

    List<VehiculoDTO> mapVehiculoList(List<Vehiculo> vehiculoList);
}
