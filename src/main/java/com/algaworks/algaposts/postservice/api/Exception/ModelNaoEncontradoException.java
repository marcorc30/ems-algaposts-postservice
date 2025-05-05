package com.algaworks.algaposts.postservice.api.Exception;

public class ModelNaoEncontradoException extends RuntimeException{

    public ModelNaoEncontradoException(String msg){
        super(msg);
    }
}
