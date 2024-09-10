package com.app.student_service.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentRequest {

    private Long id;
    @NotBlank(message = "name cannot be null")
    @Size(min = 10, max = 100, message = "min 10 maximal 100")
    private String name;

    @NotBlank(message = "email cannot be null")
    @Email(message = "email cannot valid")
    @Size(min = 10, max = 100, message = "min 10 maximal 100")
    private String email;

    @NotBlank(message = "phone number cannot be null")
    @Size(min = 10, max = 20, message = "min 10 maximal 100")
    private String phone;

    @NotBlank(message = "course cannot be null")
    @Size(min = 10, max = 100, message = "min 10 maximal 100")
    private String course;
    @NotBlank(message = "dob canot be null")
    private String dob;
}
