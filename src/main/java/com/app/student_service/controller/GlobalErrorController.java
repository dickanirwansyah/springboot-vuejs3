package com.app.student_service.controller;

import com.app.student_service.exception.ResourceConflictException;
import com.app.student_service.exception.ResourceNotfoundException;
import com.app.student_service.model.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalErrorController {

    @ExceptionHandler(ResourceConflictException.class)
    public ResponseEntity<ApiResponse> handleResourceConflict(ResourceConflictException e){
        return ResponseEntity.status(HttpStatus.CONFLICT.value())
                .body(ApiResponse.builder()
                        .message(e.getMessage())
                        .status(e.getStatus())
                        .build());
    }

    @ExceptionHandler(ResourceNotfoundException.class)
    public ResponseEntity<ApiResponse> handleResourceConflict(ResourceNotfoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value())
                .body(ApiResponse.builder()
                        .message(e.getMessage())
                        .status(e.getStatus())
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleResourceBadRequest(MethodArgumentNotValidException e){

        List<Map<String,String>> errors = new ArrayList<>();
        Map<String,String> errorMap = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errorMap.put(fieldName, errorMessage);
        });
        errors.add(errorMap);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .errors(errors)
                        .build());
    }
}
