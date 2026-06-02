package com.ytseries.sms.repo;

import com.ytseries.sms.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StudentRepo extends JpaRepository<Student,Integer> {
     @Query(value = "SELECT * FROM student", nativeQuery = true)
     List<Student> findAllStudents();


     List<Student> findByFirstname(String firstname);
     List<Student> findByEmail(String Email);

     // JPQL
     // Native
     @Query(value = "SELECT * FROM Student limit ?1 offset ?2",nativeQuery = true)
     List<Student> findAllPagination(int pageSize,int offset);

     List<Student>findByPhoneno(String phoneno);

     @Query("SELECT s FROM Student s WHERE " +
             "LOWER(s.firstname) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
             "LOWER(s.lastname) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
             "LOWER(s.email) LIKE LOWER(CONCAT('%', :keyword, '%'))")
     Page<Student> searchStudents(@Param("keyword") String keyword, Pageable pageable);
}
