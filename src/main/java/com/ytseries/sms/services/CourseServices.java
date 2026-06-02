package com.ytseries.sms.services;

import com.ytseries.sms.Enum.Category;
import com.ytseries.sms.ResponseModel.ResponseModel;
import com.ytseries.sms.dao.CourseDao;
import com.ytseries.sms.dto.CourseDTO;
import com.ytseries.sms.entity.Course;
import com.ytseries.sms.utill.APIMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CourseServices {
    @Autowired
    private CourseDao courseDao;

    public ResponseModel save(Course course) {
        List<Course> CourseNameExists = courseDao.FindByCourseName(course.getCourseName());
        if (!CourseNameExists.isEmpty()) {
            return ResponseModel.Conflict(APIMessage.COURSE_ALREADY_EXISTS, null);
        }

        return ResponseModel.created(APIMessage.COURSE_CREATED,
                courseDao.save(course));

    }

    public ResponseModel FindAllCourse(int pageNo, int Pagesize) {
        Pageable pageable = PageRequest.of(pageNo, Pagesize);
        Page<Course> courses = courseDao.findAll(pageable);
        List<CourseDTO> dtoList = new ArrayList<>();

        Map<String, Object> PageResult = new HashMap<>();
        PageResult.put("PageSize", Pagesize);
        PageResult.put("PageNo", pageNo);
        PageResult.put("totalRecords", courses.getTotalElements());
        PageResult.put("pageCount", courses.getTotalPages());

        Map<String, Object> result = new HashMap<>();
        result.put("data", dtoList);
        result.put("totalRecords", PageResult);

        for (Course c : courses) {

            CourseDTO dto = new CourseDTO();

            dto.setCourseid(c.getCourseid());
            dto.setCourseName(c.getCourseName());
            dto.setDuration(c.getDuration());
            dto.setStatus(c.getStatus());

            dtoList.add(dto);

        }
        return ResponseModel.success(APIMessage.COURSE_FOUND, result);

    }

    public ResponseModel updateCourse(Integer id, Course course) {
        Course existing = courseDao.getCourse(id);
        List<Course> findByCourseName = courseDao.FindByCourseName(course.getCourseName());
        if (findByCourseName.isEmpty()) {
            if (existing != null) {
                if (course.getCourseName() != null) existing.setCourseName(course.getCourseName());
                if (course.getDuration() != null) existing.setDuration(course.getDuration());
                if (course.getStatus() != null) existing.setStatus(course.getStatus());
             courseDao.save(existing);
                return ResponseModel.success(APIMessage.COURSE_UPDATED, existing);
            }

        }
        return ResponseModel.Conflict(APIMessage.COURSE_ALREADY_EXISTS, null);
    }

    public List<Course> findAllCourse() {
        return courseDao.findAllCourse();
    }
    public ResponseModel delete(Integer id)
    {
        Course existing = courseDao.getbyId(id);

        if (existing==null)
        {
            return ResponseModel.NotFound(APIMessage.COURSE_NOT_FOUND,null);
        }
        courseDao.delete(id);
        return ResponseModel.success(APIMessage.COURSE_DELETED,existing);
    }
    public ResponseModel filterByCategory(String name,int pageSize,int pageNo)
    {
      Pageable pageable = PageRequest.of(pageNo-1,pageSize);
     try {
         Page<Course> courses = courseDao.findAllByCateory(Category.valueOf(name), pageable);
         List<CourseDTO> dtoList = new ArrayList<>();

         Map<String, Object> PageResult = new HashMap<>();
         PageResult.put("PageSize", pageSize);
         PageResult.put("PageNo", pageNo);
         PageResult.put("totalRecords", courses.getTotalElements());
         PageResult.put("pageCount", courses.getTotalPages());

         Map<String, Object> result = new HashMap<>();
         result.put("data", dtoList);
         result.put("totalRecords", PageResult);

         for (Course c : courses) {

             CourseDTO dto = new CourseDTO();

             dto.setCourseid(c.getCourseid());
             dto.setCourseName(c.getCourseName());
             dto.setDuration(c.getDuration());
             dto.setStatus(c.getStatus());

             dtoList.add(dto);

         }
         return ResponseModel.success(APIMessage.COURSE_FOUND, result);

     } catch (Exception e) {
        throw e;
     }
    }

}
