package com.app.student_service.service;

import com.app.student_service.entity.Student;
import com.app.student_service.exception.ResourceConflictException;
import com.app.student_service.exception.ResourceNotfoundException;
import com.app.student_service.model.StudentRequest;
import com.app.student_service.model.StudentResponse;
import com.app.student_service.repository.StudentRepository;
import com.app.student_service.util.ConvertDate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService{

    private final StudentRepository studentRepository;

    @Override
    @Transactional
    public StudentResponse save(StudentRequest student) {
        var newStudent = Student.builder()
                .name(student.getName())
                .email(student.getEmail())
                .course(student.getCourse())
                .phone(student.getPhone())
                .dob(convertStringDateToDate(ConvertDate
                        .convertDateUTCtoTimeZone(student.getDob())))
                .build();
        newStudent.setCreatedAt(LocalDateTime.now());

        //find by email
        if (studentRepository.countByEmail(student.getEmail()) > 0){
            throw new ResourceConflictException("email is already exist !",HttpStatus.CONFLICT.value());
        }
        //find by phone number
        if (studentRepository.countByPhoneNumber(student.getPhone()) > 0){
            throw new ResourceConflictException("phone number is already exist !",HttpStatus.CONFLICT.value());
        }

        var responseStudent = studentRepository.save(newStudent);
        return StudentResponse.builder()
                .id(responseStudent.getId())
                .name(responseStudent.getName())
                .email(responseStudent.getEmail())
                .course(responseStudent.getCourse())
                .phone(responseStudent.getPhone())
                .dob(convertDateToString(responseStudent.getDob()))
                .build();
    }

    @Override
    @Transactional
    public StudentResponse update(Long id, StudentRequest student) {
        //find by id
        var currentStudent = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotfoundException("student id is not found !", HttpStatus.NOT_FOUND.value()));
        //find by email
        if (studentRepository.countByEmail(student.getEmail()) > 0){
            throw new ResourceConflictException("email is already exist !",HttpStatus.CONFLICT.value());
        }
        //find by phone number
        if (studentRepository.countByPhoneNumber(student.getPhone()) > 0){
            throw new ResourceConflictException("phone number is already exist !",HttpStatus.CONFLICT.value());
        }

        currentStudent.setName(student.getName());
        currentStudent.setEmail(student.getEmail());
        currentStudent.setCourse(student.getCourse());
        currentStudent.setPhone(student.getPhone());
        currentStudent.setDob(convertStringDateToDate(ConvertDate.convertDateUTCtoTimeZone(student.getDob())));
        var responseStudent = studentRepository.save(currentStudent);

        return StudentResponse.builder()
                .id(responseStudent.getId()).phone(responseStudent.getPhone())
                .email(responseStudent.getEmail()).name(responseStudent.getName())
                .course(responseStudent.getCourse()).dob(convertDateToString(responseStudent.getDob()))
                .build();
    }

    @Override
    public void delete(Long id) {
      var currentStudent = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotfoundException("student id is not found !",HttpStatus.NOT_FOUND.value()));
      studentRepository.delete(currentStudent);
    }

    @Override
    public StudentResponse getStudentById(Long id) {
        var currentStudent = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotfoundException("student id is not found !",HttpStatus.NOT_FOUND.value()));
        return StudentResponse.builder()
                .id(currentStudent.getId())
                .email(currentStudent.getEmail())
                .dob(ConvertDate.convertTimeZoneToUTC(currentStudent.getDob()))
                .phone(currentStudent.getPhone())
                .course(currentStudent.getCourse())
                .name(currentStudent.getName())
                .build();
    }

    @Override
    public Page<StudentResponse> studentPage(Pageable pageable) {
        return studentRepository.findAll(PageRequest
                .of(pageable.getPageNumber(),pageable.getPageSize()))
                .map(student -> StudentResponse.builder()
                        .id(student.getId())
                        .name(student.getName())
                        .email(student.getEmail())
                        .course(student.getCourse())
                        .phone(student.getPhone())
                        .dob(convertDateToString(student.getDob()))
                        .build());
    }

    private Date convertStringDateToDate(String dateString){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private String convertDateToString(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }
}
