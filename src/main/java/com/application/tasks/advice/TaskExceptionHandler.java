package com.application.tasks.advice;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class TaskExceptionHandler {
    /**
     * Método: Captura los errores según las anotaciones de validaciones que se disparan con el @Valid del controller y las anotaciones del dto por atributo
     * @param exception de argumento not valid
     * @return una lista de errores
     */
        @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handlerUnexpectedArguments(MethodArgumentNotValidException exception) {
            Map<String, String> errors = new HashMap<>();
            exception.getBindingResult().getFieldErrors().forEach( error -> {
                errors.put(error.getField(), error.getDefaultMessage());
            });
            return errors;
    }
}
