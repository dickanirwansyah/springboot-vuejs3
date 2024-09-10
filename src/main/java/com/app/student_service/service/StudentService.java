package com.app.student_service.service;

import com.app.student_service.model.StudentRequest;
import com.app.student_service.model.StudentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentService {

    StudentResponse save(StudentRequest student);
    StudentResponse update(Long id,StudentRequest student);
    void delete(Long id);
    StudentResponse getStudentById(Long id);
    Page<StudentResponse> studentPage(Pageable pageable);

}
