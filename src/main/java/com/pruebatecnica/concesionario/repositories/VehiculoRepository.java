package com.pruebatecnica.concesionario.repositories;

import com.pruebatecnica.concesionario.entities.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {
}
