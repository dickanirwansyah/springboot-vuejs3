package com.app.student_service.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResourceConflictException extends RuntimeException{

    private Integer status;

    public ResourceConflictException(String message,Integer status){
        super(message);
        this.status = status;
    }
}
