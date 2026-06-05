package com.ytseries.sms.dto;

import com.ytseries.sms.Enum.Status;
import com.ytseries.sms.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {
    private int Courseid;
    private String CourseName;
    private Status status;
    private Integer duration;

    public static Course toEntity(CourseDTO dto) {
        return Course.builder()
                .courseid(dto.Courseid)
                .courseName(dto.CourseName)
                .status(dto.status)
                .duration(dto.duration)
                .build();
    }

    public static CourseDTO toDTO(Course course) {
        return CourseDTO.builder()
                .Courseid(course.getCourseid())
                .CourseName(course.getCourseName())
                .status(course.getStatus())
                .duration(course.getDuration())
                .build();
    }


}

