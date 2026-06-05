package com.ytseries.sms.services;

import com.ytseries.sms.dao.StudentDao;
import com.ytseries.sms.ResponseModel.ResponseModel;
import com.ytseries.sms.dto.StudentDTO;
import com.ytseries.sms.entity.Student;
import com.ytseries.sms.exception.DuplicateExceptionResource;
import com.ytseries.sms.exception.NotFoundExceptionResourse;
import com.ytseries.sms.utill.APIMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentServices {

    @Autowired
    private StudentDao studentDao;

    // Save Student
    public ResponseModel save(StudentDTO students) {

          List<Student> PhoneExists =studentDao.findByPhoneno(students.getPhoneno());
          List<Student> EmailExists= studentDao.findByEmail(students.getEmail());
          if (!PhoneExists.isEmpty()){
             throw new DuplicateExceptionResource((APIMessage.Student_Already_exists));
          }
          if (!EmailExists.isEmpty()){
             throw new DuplicateExceptionResource((APIMessage.Email_Already_exists));
          }

   Student student =StudentDTO.toEntity(students);
          Student student1=studentDao.save(student);
         StudentDTO toDto= StudentDTO.toDTO(student1);

       return ResponseModel.created(APIMessage.Student_Created,
                                    toDto);
    }

    public ResponseModel getStudent(Integer id) {
        Student student = studentDao.getStudent(id);
        if (student == null) {
           throw new NotFoundExceptionResourse((APIMessage.Student_Not_Found));
        }
        return ResponseModel.success(APIMessage.Student_Found,studentDao.getStudent(id));
    }

    public ResponseModel getAllStudents(int pageNo, int pageSize) {
        Pageable pageable =PageRequest.of(pageNo,pageSize);
        Page<Student> students = studentDao.findAll(pageable);
        List<StudentDTO> dtoList = new ArrayList<>();

        Map<String, Object> pageResult = new HashMap<>();
        pageResult.put("pageSize", pageSize);
        pageResult.put("pageNo", pageNo);
        pageResult.put("totalRecords",students.getTotalElements());
        pageResult.put("pageCount", students.getTotalPages());

        Map<String, Object> result = new HashMap<>();
        result.put("data", dtoList);
        result.put("totalRecords", pageResult);

        for (Student s : students) {

            StudentDTO dto = new StudentDTO();

            dto.setStudentid(s.getStudentid());
            dto.setFirstname(s.getFirstname());
            dto.setLastname(s.getLastname());
            dto.setPhoneno(s.getPhoneno());
            dto.setEmail(s.getEmail());
            dto.setGender(s.getGender());
            dto.setStatus(s.getStatus());
            dto.setCourse(s.getCourse());

            dtoList.add(dto);
        }
        return ResponseModel.success(APIMessage.Student_Found,
                result);
    }

    public ResponseModel updatestudent(Integer studentid, Student students) {

        Student existing = studentDao.getStudent(studentid);
        List<Student> PhoneExist = studentDao.findByPhoneno(students.getPhoneno());
        List<Student> EmailExist = studentDao.findByEmail(students.getEmail());

        // Check if phone or email is already taken by a DIFFERENT student record
        boolean isPhoneTaken = PhoneExist.stream().anyMatch(s -> !s.getStudentid().equals(studentid));
        boolean isEmailTaken = EmailExist.stream().anyMatch(s -> !s.getStudentid().equals(studentid));

        if (!isPhoneTaken && !isEmailTaken) {
          if (existing != null) {
              if (students.getFirstname() != null) existing.setFirstname(students.getFirstname());
              if (students.getLastname() != null) existing.setLastname(students.getLastname());
              if (students.getPhoneno() != null) existing.setPhoneno(students.getPhoneno());
              if (students.getEmail() != null) existing.setEmail(students.getEmail());
              if (students.getGender() != null) existing.setGender(students.getGender());
              if (students.getStatus() != null) existing.setStatus(students.getStatus());

              studentDao.save(existing);
              return ResponseModel.success(APIMessage.Student_Updated, existing);
          }
        }
        return ResponseModel.Conflict(APIMessage.STUDENT_ALREADY_PRESENT, null);
    }

    public ResponseModel deleteStudent(Integer id) {

        Student existing = studentDao.getStudent(id);
        if (existing == null) {
            return ResponseModel.NotFound(APIMessage.Student_Not_Found, null);
        }
      studentDao.delete(existing.getStudentid());
        return ResponseModel.success(APIMessage.Student_Deleted,existing);
    }


    public Object FindByFirstname(String firstname) {
        List<Student> student = studentDao.findByFirstname(firstname);
        if (student.isEmpty()) {
            return "Student not Found";
        }
        return studentDao.findByFirstname(firstname);
    }

    public Object FindByphoneno(String phoneno) {
        List<Student> student = studentDao.findByPhoneno(phoneno);
        if (student.isEmpty()) {
            return "Student Not Found";
        }
        return studentDao.findByPhoneno(phoneno);
    }

    public Object conutStudents() {
        if (studentDao.countStudents() == 0) {
            return "Student not Found";
        }
        return studentDao.countStudents();
    }

    public List<Student> findAllStudents() {
        return studentDao.findAllStudents();
    }
    public ResponseModel SearchKeyword (String keyword,int pageNo,int pageSize)
    {
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Page<Student> page = studentDao.SearchKeyword(keyword,pageable);
        if (page.isEmpty()){
            return ResponseModel.NotFound(APIMessage.Student_Not_Found,null);
        }
        return ResponseModel.success(APIMessage.Student_Found,page.getContent());
    }
}