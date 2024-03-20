package br.com.ibmec.cloud.cargallery.errorHandler;


import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class ErrorHandlingController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorResponse validationHandler(MethodArgumentNotValidException e) {
        ValidationErrorResponse errors = new ValidationErrorResponse();

        for (FieldError item : e.getBindingResult().getFieldErrors()) {
            Validation validation = new Validation();
            validation.setCampo(item.getField());
            validation.setMensagem(item.getDefaultMessage());

            errors.getValidationErrors().add(validation);
        }

        return errors;

    }

}
