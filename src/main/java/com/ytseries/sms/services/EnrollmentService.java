package com.ytseries.sms.services;

import com.ytseries.sms.ResponseModel.ResponseModel;
import com.ytseries.sms.dao.CourseDao;
import com.ytseries.sms.dao.CourseEnrollmentDao;
import com.ytseries.sms.dao.StudentDao;
import com.ytseries.sms.dto.CourseDTO;
import com.ytseries.sms.dto.EnrollmentDTO;
import com.ytseries.sms.entity.Course;
import com.ytseries.sms.entity.Enrollment;
import com.ytseries.sms.entity.Student;
import com.ytseries.sms.exception.DuplicateExceptionResource;
import com.ytseries.sms.exception.MaxLimitExceptionResource;
import com.ytseries.sms.exception.NotFoundExceptionResourse;
import com.ytseries.sms.utill.APIMessage;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.naming.ldap.PagedResultsControl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class EnrollmentService {
@Autowired
    private CourseDao courseDao;
@Autowired
    private StudentDao studentDao;
@Autowired
    private CourseEnrollmentDao courseEnrollmentDao;

    @Transactional(rollbackOn = Exception.class)
public ResponseModel save(EnrollmentDTO enrollment1) {
    Course course = courseDao.getCourse(
            Integer.parseInt(enrollment1.getCourseid())
    );
    if (course == null) {
        throw new NotFoundExceptionResourse(
                APIMessage.COURSE_NOT_FOUND
        );
    }
    Student student = studentDao.getStudent(
            Integer.parseInt(enrollment1.getStudentid())
    );

    if (student == null) {
        throw new NotFoundExceptionResourse(
                APIMessage.Student_Not_Found
        );
    }
    boolean alreadyEnrolled =
            courseEnrollmentDao.existsByCourseIdAndStudentId(
                    course.getCourseid(),
                    student.getStudentid()
            );
    if (alreadyEnrolled) {
        throw new DuplicateExceptionResource(
                APIMessage.STUDENT_ALREADY_ENROLLED
        );
    }
    Enrollment enrollment = new Enrollment();
    enrollment.setEnrollmentId("ENR" + System.currentTimeMillis());
    enrollment.setCourse(course);
    enrollment.setStudent(student);

    courseEnrollmentDao.save(enrollment);

    return  ResponseModel.created(
            APIMessage.ENROLLMENT_SUCCESS,EnrollmentDTO.fromEnrollment(enrollment));

}
    public ResponseModel getAllEnrollment(int pageNo,int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Enrollment> enrollments = courseEnrollmentDao.findAll(pageable);
        List<EnrollmentDTO> dtoList = new ArrayList<>();
        Map<String, Object> PageResult = new HashMap<>();
        PageResult.put("PageSize", pageSize);
        PageResult.put("PageNo", pageNo);
        PageResult.put("totalRecords", enrollments.getTotalElements());
        PageResult.put("pageCount", enrollments.getTotalPages());

        Map<String, Object> results = new HashMap<>();
        results.put("data", dtoList);
        results .put("totalRecords", PageResult);

        for (Enrollment E:enrollments)
        {
            EnrollmentDTO dto= new EnrollmentDTO();

            dto.setEnrollmentId(E.getEnrollmentId());
            dto.setCourseName(E.getCourse().getCourseName());
            dto.setStudentName(E.getStudent().getFirstname());
            dto.setCourseid(String.valueOf(E.getCourse().getCourseid()));
            dto.setStudentid(String.valueOf(E.getStudent().getStudentid()));

            dtoList.add(dto);
        }

        return ResponseModel.success(APIMessage.ENROLLMENT_FATCH_SUCCESS, results);
}

    public ResponseModel getEnrollmentById(String id) {
        Enrollment enrollment = courseEnrollmentDao.getEnrollment(id);
        if (enrollment == null) {
            throw new NotFoundExceptionResourse((APIMessage.ENROLLMENT_NOT_FOUND));
        }

        EnrollmentDTO dto = EnrollmentDTO.fromEnrollment(enrollment);

        return ResponseModel.success(
                APIMessage.ENROLLMENT_FOUND,
                dto
        );
}
    public ResponseModel deleteEnrollmentById(String id)
    {

        Enrollment enrollment = courseEnrollmentDao.getEnrollment(id);
        if (enrollment == null) {
            throw new NotFoundExceptionResourse(APIMessage.ENROLLMENT_NOT_FOUND);
        }
        Course course = enrollment.getCourse();
        course.setCurrentEnrollment(course.getCurrentEnrollment()-1);
        courseDao.save(course);

        courseEnrollmentDao.deleteEnrollment(enrollment);
        return ResponseModel.success(
                APIMessage.ENROLLMENT_DELETE_SUCCESS,
                null
        );

    }
}

