package br.com.ibmec.cloud.cargallery.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AzureSearchIndex {
    private String id;
    private String fabricante;

    @JsonProperty(value = "nome_carros")
    private String nomeCarro;
}
