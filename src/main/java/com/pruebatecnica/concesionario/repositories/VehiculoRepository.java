package com.pruebatecnica.concesionario.repositories;

import com.pruebatecnica.concesionario.entities.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {

    @Query("SELECT v FROM Vehiculo AS v WHERE v.placa = :placa")
    Optional<Vehiculo> findByPlaca(String placa);
}
