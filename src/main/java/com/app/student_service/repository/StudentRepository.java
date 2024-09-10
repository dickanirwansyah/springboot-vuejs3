package com.app.student_service.repository;

import com.app.student_service.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

    @Query(value = "select count(*) from student where email=:email", nativeQuery = true)
    Integer countByEmail(@Param("email")String email);

    @Query(value = "select count(*) from student where phone=:phoneNumber", nativeQuery = true)
    Integer countByPhoneNumber(@Param("phoneNumber")String phoneNumber);
}
