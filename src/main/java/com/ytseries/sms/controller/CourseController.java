package com.ytseries.sms.controller;

import com.ytseries.sms.ResponseModel.ResponseModel;
import com.ytseries.sms.entity.Course;
import com.ytseries.sms.services.CourseServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class CourseController {
    @Autowired
    CourseServices courseServices;


 @PostMapping ("/insertCourse")
    public ResponseModel insertCourse(@RequestBody Course course)
 {
     return courseServices.save(course);
 }
    @GetMapping("/getAllCourses")
    public ResponseModel getAllCourses(
            @RequestParam(required = false,defaultValue = "1") int pageNo,
            @RequestParam(required = false,defaultValue = "10")int pageSize
    ) {
        return courseServices.FindAllCourse(pageNo, pageSize);
    }
    @GetMapping("/FindAllCourse")
    public List<Course> findAllCourse()
    {
        return courseServices.findAllCourse();
    }
    @PutMapping("/updatecourse/{id}")
    public ResponseModel updatecourse(@PathVariable Integer id ,
                                      @RequestBody Course course){
     return courseServices.updateCourse(id, course);
    }
    @DeleteMapping("/deleteCourse/{id}")
    public ResponseModel delete (@PathVariable Integer id)
    {
        return courseServices.delete(id);
    }
    @GetMapping("/category/{name}")
    public ResponseModel getCourseByCategory
            (@PathVariable String name,
             @RequestParam(required = false,defaultValue = "1") int pageNo,
             @RequestParam(required = false,defaultValue = "10")int pageSize){

      return  courseServices.filterByCategory(name,pageSize,pageNo);
    }

}
