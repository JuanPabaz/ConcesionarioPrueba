package com.pruebatecnica.concesionario.repositories;

import com.pruebatecnica.concesionario.entities.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrdenRepository extends JpaRepository<Orden, Long> {

    @Query("SELECT o FROM Orden AS o WHERE o.vehiculo.id = :idVehiculo AND o.activa = true")
    Optional<List<Orden>> encontrarPorIdVehiculoYOrdenActiva(Long idVehiculo);
}
