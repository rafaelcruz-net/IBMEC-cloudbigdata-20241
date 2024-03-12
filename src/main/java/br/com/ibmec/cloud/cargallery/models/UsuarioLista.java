package br.com.ibmec.cloud.cargallery.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity
public class UsuarioLista {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String nome;

    @ManyToOne
    private Usuario usuario;

    @OneToMany
    private List<Carro> carros;
}
