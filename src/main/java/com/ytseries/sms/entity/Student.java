package com.ytseries.sms.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ytseries.sms.Enum.Gender;
import com.ytseries.sms.Enum.Status;
import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
public class Student {
    @Id
    private Integer studentid;
    @Column(name = "first_name")
    private String firstname;
    private String lastname;
    private String phoneno;
    private String email;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "courseid")
    private Course course;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private Status status;



}
