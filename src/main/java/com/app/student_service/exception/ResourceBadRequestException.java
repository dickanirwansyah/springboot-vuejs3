package com.app.student_service.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResourceBadRequestException extends RuntimeException{

    private Integer status;

    public ResourceBadRequestException(String message,Integer status){
        super(message);
        this.status = status;
    }
}
