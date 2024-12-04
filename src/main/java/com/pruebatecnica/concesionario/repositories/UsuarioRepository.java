package com.pruebatecnica.concesionario.repositories;

import com.pruebatecnica.concesionario.entities.Usuario;
import com.pruebatecnica.concesionario.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);

    @Query("SELECT u.role FROM Usuario u WHERE u.username = :nombre")
    Role findRoleByUsername(String nombre);

}
