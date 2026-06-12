package com.ytseries.sms.controller;

import com.ytseries.sms.ResponseModel.ResponseModel;
import com.ytseries.sms.dto.EnrollmentDTO;
import com.ytseries.sms.services.EnrollmentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/enrollment")
public class CourseEnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @PostMapping("/insert")
    public ResponseModel insertEnrollment(@RequestBody  EnrollmentDTO dto) {
        return enrollmentService.save(dto);
    }
    @GetMapping("/get-all")
    public ResponseModel getAllEnrollment(@RequestParam int pageNo, @RequestParam int pageSize) {
        return enrollmentService.getAllEnrollment(pageNo, pageSize);
    }
    @GetMapping("/get/{id}")
    public ResponseModel getEnrollmentById(@PathVariable String id)
        {
        return enrollmentService.getEnrollmentById(id);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseModel  deleteEnrollmentById(@PathVariable String id)
    {
        return enrollmentService.deleteEnrollmentById(id);
    }
}
