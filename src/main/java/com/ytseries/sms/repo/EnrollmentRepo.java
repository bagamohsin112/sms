package com.ytseries.sms.repo;

import com.ytseries.sms.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepo  extends JpaRepository<Enrollment, String > {

    boolean existsByCourse_CourseidAndStudent_Studentid(
            Integer courseid,
            Integer studentid
    );

}