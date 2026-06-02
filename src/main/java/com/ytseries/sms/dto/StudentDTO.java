package com.ytseries.sms.dto;

import com.ytseries.sms.Enum.Gender;
import com.ytseries.sms.Enum.Status;
import com.ytseries.sms.entity.Course;
import com.ytseries.sms.entity.Student;
import lombok.Data;

@Data
public class StudentDTO {
    private Integer studentid;
    private String firstname;
    private String lastname;
    private String phoneno;
    private String Email;
    private Gender gender;
    private Status status;
    private Course course;


    public Student Entity() {
        Student student = new Student();
        student.setStudentid(this.studentid);
        student.setFirstname(this.firstname);
        student.setLastname(this.lastname);
        student.setPhoneno(this.phoneno);
        student.setEmail(this.Email);
        student.setGender(this.gender);
        student.setStatus(this.status);

        return student;

    }

}
