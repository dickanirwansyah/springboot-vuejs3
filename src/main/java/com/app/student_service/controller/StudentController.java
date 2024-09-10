package com.app.student_service.controller;

import com.app.student_service.model.ApiResponse;
import com.app.student_service.model.StudentRequest;
import com.app.student_service.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/student")
@RequiredArgsConstructor
@CrossOrigin(value = {"*"})
public class StudentController {

    private final StudentService studentService;

    @PostMapping(value = "/save")
    public ResponseEntity<ApiResponse> save(@RequestBody @Valid StudentRequest request){
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.builder()
                        .data(studentService.save(request))
                        .message(HttpStatus.OK.getReasonPhrase())
                        .status(HttpStatus.OK.value())
                        .build());
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable("id")Long id, @RequestBody @Valid StudentRequest request){
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.builder()
                        .data(studentService.update(id,request))
                        .message(HttpStatus.OK.getReasonPhrase())
                        .status(HttpStatus.OK.value())
                        .build());
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable("id")Long id){
        studentService.delete(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.builder()
                        .data(id)
                        .message(HttpStatus.OK.getReasonPhrase())
                        .status(HttpStatus.OK.value())
                        .build());
    }

    @GetMapping(value = "/find/{id}")
    public ResponseEntity<ApiResponse> find(@PathVariable("id")Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.builder()
                        .data(studentService.getStudentById(id))
                        .message(HttpStatus.OK.getReasonPhrase())
                        .status(HttpStatus.OK.value())
                        .build());
    }

    @GetMapping(value = "/search")
    public ResponseEntity<ApiResponse> find(Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.builder()
                        .data(studentService.studentPage(pageable))
                        .message(HttpStatus.OK.getReasonPhrase())
                        .status(HttpStatus.OK.value())
                        .build());
    }
}
