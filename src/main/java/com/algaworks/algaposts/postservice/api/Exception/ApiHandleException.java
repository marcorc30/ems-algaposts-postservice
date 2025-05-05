package com.algaworks.algaposts.postservice.api.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

@RestControllerAdvice
public class ApiHandleException {

    @ExceptionHandler(ModelNaoEncontradoException.class)
    public ProblemDetail handle(ModelNaoEncontradoException e){
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);

        pd.setTitle("Nao encontrado");
        pd.setType(URI.create("/erros/api"));
        pd.setDetail("Model não encontrado na consulta");

        return pd;

    }
}
