package br.com.ibmec.cloud.cargallery.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UsuarioListaRequest {
    @NotBlank(message = "Nome da lista é obrigatório")
    private String nome;
}
