package com.pruebatecnica.concesionario.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pruebatecnica.concesionario.enums.TipoOrden;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "orden")
@AllArgsConstructor
@NoArgsConstructor
public class Orden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vehiculo_id", nullable = false)
    @JsonBackReference
    private Vehiculo vehiculo;

    @Column(nullable = false)
    private LocalDate fechaOrden;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoOrden tipo;

    @Column(nullable = false)
    private Boolean activa;
}
