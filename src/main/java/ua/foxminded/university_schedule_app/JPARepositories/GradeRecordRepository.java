package ua.foxminded.university_schedule_app.JPARepositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.foxminded.university_schedule_app.Entity.GradeRecord;

@Repository
public interface GradeRecordRepository extends JpaRepository<GradeRecord, Integer> {
    
    Optional<GradeRecord> findByGradeId(Integer gradeId);
    
    //all student's grades
    List<GradeRecord> findByStudent_UserId (Integer studentId);
    
    //newest first:
 // All grades of a student, newest first
   List<GradeRecord> findByStudent_UserIdOrderByExamDateDesc (Integer studentId);
   
   List<GradeRecord> findByTeacher_UserIdOrderByExamDateDesc (Integer teacherId);
   
   //all student's grades for one course (filter) , newest first
   List<GradeRecord> findByStudent_UserIdAndCourse_CourseIdOrderByExamDateDesc (Integer studentId, Integer courseId);
   
   //filter all student's grades for one day
   List<GradeRecord> findByStudent_UserIdAndExamDateBetweenOrderByExamDateDesc (Integer studentId, LocalDate startDate, LocalDate endDate);
    
   //GradeRecords for this student in the teacher’s courses
   //teacher can see GradeRecords for this student in the teacher’s courses.
   List<GradeRecord> findByStudent_UserIdAndTeacher_UserIdOrderByExamDateDesc (Integer studentId, Integer teacherId);

}
