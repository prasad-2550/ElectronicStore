package com.BikkadIT.ElectronicStroe.exception;

import lombok.Builder;

@Builder
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(){
        super("!!Resource Not Found!!");
    }
    public ResourceNotFoundException(String Message){
        super( Message );
    }

}
