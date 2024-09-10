package com.app.student_service.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResourceNotfoundException extends RuntimeException{

    private Integer status;

    public ResourceNotfoundException(String message,Integer status){
        super(message);
        this.status = status;
    }
}
