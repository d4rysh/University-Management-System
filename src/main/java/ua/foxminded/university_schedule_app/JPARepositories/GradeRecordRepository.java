package ua.foxminded.university_schedule_app.JPARepositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.foxminded.university_schedule_app.Entity.GradeRecord;

@Repository
public interface GradeRecordRepository extends JpaRepository<GradeRecord, Integer> {
    
    //newest first:
 // All grades of a student, newest first
    Page<GradeRecord> findByStudent_UserIdOrderByExamDateDesc (Integer studentId, Pageable pageable);
    
   //all student's grades for one course (filter) , newest first
    Page<GradeRecord> findByStudent_UserIdAndCourse_CourseIdOrderByExamDateDesc (Integer studentId, Integer courseId, Pageable pageable);
   
    Page<GradeRecord> findByStudent_UserIdAndTeacher_UserIdOrderByExamDateDesc (Integer studentId, Integer teacherId, Pageable pageable);

    Page<GradeRecord> findByTeacher_UserIdAndCourse_CourseIdOrderByExamDateDesc(Integer teacherId, Integer courseId, Pageable pageable);
}
