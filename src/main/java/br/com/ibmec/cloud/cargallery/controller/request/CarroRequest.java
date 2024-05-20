package br.com.ibmec.cloud.cargallery.controller.request;

import lombok.Data;

import java.util.UUID;

@Data
public class CarroRequest {
    private UUID id;
    private String nome;
    private String descricao;
    private String imagemBase64;
}
