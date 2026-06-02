package com.ytseries.sms.dao;

import com.ytseries.sms.Enum.Category;
import com.ytseries.sms.entity.Course;
import com.ytseries.sms.repo.CourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CourseDao {
@Autowired
    private CourseRepo courseRepo;

  public Course save(Course course)
  {
      return courseRepo.save(course);
  }
  public List<Course>FindByCourseName(String CourseName)
  {
      return courseRepo.findByCourseName(CourseName);
  }
  public Page<Course>findAll(Pageable pageable)
  {
      return courseRepo.findAll(pageable);
  }
  public Course getCourse(Integer id)
  {
      return courseRepo.findById(id).orElse(null);
  }
    public List<Course> findAllCourse() {
        return courseRepo.getAllCoursesNative();
    }
    public void delete(Integer id)
    {
        courseRepo.deleteById(id);
    }
    public Course getbyId(Integer id ){
      return courseRepo.findById(id).orElse(null);
    }
    public Page<Course> findAllByCateory(Category category,Pageable pageable)
    {
        return courseRepo.findAllByCategory(category,pageable);
    }
}
