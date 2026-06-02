package com.ytseries.sms.dao;

import com.ytseries.sms.repo.StudentRepo;
import com.ytseries.sms.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentDao {
    @Autowired
    private StudentRepo studentRepo;


    public Student save(Student student) {
       return studentRepo.save(student);
    }

    public Student getStudent(Integer id) {
        return studentRepo.findById(id).orElse(null);
    }

    public List<Student> findByFirstname(String firstname)
    {
       return studentRepo.findByFirstname(firstname);
    }

    public List<Student> findByPhoneno(String phoneno)
    {
        return  studentRepo.findByPhoneno(phoneno);
    }
    public List<Student>findByEmail(String email)
    {
        return studentRepo.findByEmail(email);
    }

    public Long countStudents()
    {
        return studentRepo.count();
    }

    public List<Student>findAllStudents(){
        return studentRepo.findAllStudents();
    }
    public Page<Student>findAll(Pageable pageable){
        return studentRepo.findAll(pageable);
    }
    public void delete(Integer id){
        studentRepo.deleteById(id);
    }
    public Page<Student> SearchKeyword (String keyword,Pageable pageable){
        return studentRepo.searchStudents(keyword,pageable);

    }
}

