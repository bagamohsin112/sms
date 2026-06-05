package com.ytseries.sms.entity;


import com.ytseries.sms.Enum.Category;
import com.ytseries.sms.Enum.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer courseid;
    private String courseName;
    private Integer duration;
    @Enumerated(EnumType.STRING)
    private Category category;
    @Enumerated(EnumType.STRING)
    private Status status=Status.ACTIVE;
    @OneToMany(mappedBy = "course")
    private List<Student> students;

}
