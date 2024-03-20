package br.com.ibmec.cloud.cargallery.errorHandler;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ValidationErrorResponse {
    public String mensagem = "Existem erros na sua requisição";
    public List<Validation> validationErrors = new ArrayList<>();
}
