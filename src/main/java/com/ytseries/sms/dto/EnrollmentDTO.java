package com.ytseries.sms.dto;

import com.ytseries.sms.entity.Enrollment;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data

public class EnrollmentDTO {

    private String enrollmentId;

    @NotBlank(message = "studentId cannot be blank")
    private String studentid;

    @NotBlank(message = "courseId cannot be blank")
    private String courseid;

    private String courseName;
    private String studentName;

    public static EnrollmentDTO fromEnrollment(Enrollment enrollment) {
        EnrollmentDTO enrollmentDTO = new EnrollmentDTO();
        enrollmentDTO.setStudentid(String.valueOf(enrollment.getStudent().getStudentid()));
        enrollmentDTO.setCourseid(String.valueOf(enrollment.getCourse().getCourseid()));
        enrollmentDTO.setEnrollmentId(enrollment.getEnrollmentId());

        // Set course name and student name
        enrollmentDTO.setCourseName(enrollment.getCourse().getCourseName());
        enrollmentDTO
                .setStudentName(enrollment.getStudent().getFirstname() + " " + enrollment.getStudent().getLastname());

        return enrollmentDTO;
}}
