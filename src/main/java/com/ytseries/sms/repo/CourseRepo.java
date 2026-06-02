package com.ytseries.sms.repo;


import com.ytseries.sms.Enum.Category;
import com.ytseries.sms.entity.Course;
import com.ytseries.sms.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepo extends JpaRepository<Course,Integer> {

    List<Course> findByCourseName(String courseName);

    @Query("SELECT c FROM Course c")
    List<Course> getAllCoursesNative();

    Page<Course>findAllByCategory(Category category, Pageable pageable);

}
