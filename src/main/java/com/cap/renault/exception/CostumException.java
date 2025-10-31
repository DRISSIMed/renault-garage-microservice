package com.cap.renault.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INSUFFICIENT_STORAGE)
public class CostumException extends  Exception{
    public CostumException(String message){
        super(message);
    }
}
