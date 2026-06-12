package com.ytseries.sms.controller;

import com.ytseries.sms.ResponseModel.ResponseModel;
import com.ytseries.sms.dto.StudentDTO;
import com.ytseries.sms.entity.Student;
import com.ytseries.sms.services.StudentServices;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/student")
public class StudentController {
    private final StudentServices studentServices;

    public StudentController(StudentServices studentServices) {
        this.studentServices = studentServices;
    }

    @PostMapping("/save")
    public ResponseModel save(@RequestBody @Valid StudentDTO dto )
    {
       return studentServices.save(dto);
    }
   @GetMapping("/get/{id}" )
    public ResponseModel getStudent(@PathVariable Integer id )
   {
       return studentServices.getStudent(id);
   }

    @GetMapping("/students")
    public ResponseModel getALlStudents(@RequestParam int pageNo,
                                        @RequestParam int pageSize){
        return studentServices.getAllStudents(pageNo,pageSize);
    }
    @GetMapping("/findallStudent")
    public List<Student> findAllStudent(){
        return studentServices.findAllStudents();
    }

   @GetMapping("/findByFirstname/{firstname}")
   public Object findByFirstname(@PathVariable String firstname)
   {
       return studentServices.FindByFirstname(firstname);
   }
   @GetMapping("/findByPhoneno/{phoneno}")
   public Object findByPhoneno(@PathVariable String phoneno)
   {
       return studentServices.FindByphoneno(phoneno);
   }
   @GetMapping("/count")
   public Object countStudents(){
        return studentServices.conutStudents();
   }

   @PutMapping("/update/{id}")
    public ResponseModel updateStudent(@PathVariable Integer id,
                                @RequestBody Student students){

        return  studentServices.updatestudent(id,students);
   }
   @DeleteMapping("/delete/{id}")
   public ResponseModel deleteStudent(@PathVariable Integer id){
     return studentServices.deleteStudent(id);

   }
    @GetMapping("/search")
    public ResponseModel searchStudents(
            @RequestParam String keyword,
            @RequestParam int pageNo,
            @RequestParam int pageSize
    ) {
        return studentServices.SearchKeyword(keyword, pageNo, pageSize);
    }
}
