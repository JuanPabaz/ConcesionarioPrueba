package com.pruebatecnica.concesionario.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "vehiculo")
@AllArgsConstructor
@NoArgsConstructor
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String placa;

    @Column(nullable = false)
    private String marca;

    @Column(nullable = false)
    private String modelo;

    @OneToMany(targetEntity = Orden.class,mappedBy = "vehiculo", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Orden> ordenes;

}
