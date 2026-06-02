package com.ytseries.sms.dto;

import com.ytseries.sms.Enum.Status;
import com.ytseries.sms.entity.Course;
import lombok.Data;
@Data
public class CourseDTO {
    private int Courseid;
    private String CourseName;
    private Status status;
    private Integer duration;

public Course Entity(){
    Course course = new Course();
    course.setCourseid(this.Courseid);
    course.setCourseName(this.CourseName);
    course.setStatus(this.status);
    course.setDuration(this.duration);

    return course;

}
}

