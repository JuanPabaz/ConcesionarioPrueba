package com.pruebatecnica.concesionario.repositories;

import com.pruebatecnica.concesionario.entities.Orden;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdenRepository extends JpaRepository<Orden, Long> {


}
