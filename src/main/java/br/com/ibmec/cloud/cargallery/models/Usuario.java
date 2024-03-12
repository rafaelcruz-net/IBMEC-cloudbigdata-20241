package br.com.ibmec.cloud.cargallery.models;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String nome;

    @Column
    private String email;

    @Column
    private String senha;

    @OneToMany
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private List<UsuarioLista> listaDesejo;



}
