package com.BikkadIT.ElectronicStroe.exception;

public class BadApiRequest extends RuntimeException{
    public BadApiRequest(String message){
        super(message);
    }

    private BadApiRequest(){
        super("Bad Request");
    }
}
