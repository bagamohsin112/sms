package com.ytseries.sms.dao;


import com.ytseries.sms.entity.Enrollment;
import com.ytseries.sms.repo.EnrollmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class CourseEnrollmentDao {
    @Autowired
    private EnrollmentRepo enrollmentRepo;

    public Enrollment save(Enrollment enrollment)
    {
        return enrollmentRepo.save(enrollment);
    }
    public boolean existsByCourseIdAndStudentId(Integer courseid, Integer studentid) {
        return enrollmentRepo.existsByCourse_CourseidAndStudent_Studentid(
                courseid,
                studentid
        );

}
    public Page<Enrollment> findAll(Pageable pageable){
        return enrollmentRepo.findAll(pageable);
    }
    public Enrollment getEnrollment(String id){
        return enrollmentRepo.findById(id).orElse(null);
    }
    public void deleteEnrollment(Enrollment enrollment) {
        enrollmentRepo.delete(enrollment);
    }
}
