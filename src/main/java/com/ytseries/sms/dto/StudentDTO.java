package com.ytseries.sms.dto;

import com.ytseries.sms.Enum.Gender;
import com.ytseries.sms.Enum.Status;
import com.ytseries.sms.entity.Course;
import com.ytseries.sms.entity.Student;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentDTO {
    private Integer studentid;

    @NotBlank(message = "first name is required")
    @Size(min = 3,message = "Atleast 3 char is required")
    private String firstname;

    @NotBlank(message = "last name is reqvired")
    @Size(min = 3,message = "Atleast 3 char is required")
    private String lastname;

    @NotBlank(message = "Phone no is Required")
    @Pattern(regexp = "[0-9]{10}",message = "invalid Phoneno")
    private String phoneno;

    @NotBlank(message = "Email is Required")
    @Email(message = "invalid email")
    private String Email;

    @NotNull(message = "Gender is Required")
    private Gender gender;

    private Status status;
    private Course course;

    public static Student toEntity(StudentDTO dto) {
        return Student.builder()
                .studentid(dto.getStudentid())
                .firstname(dto.getFirstname())
                .lastname(dto.getLastname())
                .phoneno(dto.getPhoneno())
                .email(dto.getEmail())
                .gender(dto.getGender())
                .status(dto.getStatus())
                .course(dto.getCourse())
                .build();
    }
    public static StudentDTO toDTO(Student student) {
        return StudentDTO.builder()
                .studentid(student.getStudentid())
                .firstname(student.getFirstname())
                .lastname(student.getLastname())
                .phoneno(student.getPhoneno())
                .Email(student.getEmail())
                .gender(student.getGender())
                .status(student.getStatus())
                .course(student.getCourse())
                .build();
    }


}
