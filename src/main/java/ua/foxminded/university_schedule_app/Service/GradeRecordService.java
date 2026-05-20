package ua.foxminded.university_schedule_app.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import ua.foxminded.university_schedule_app.Entity.GradeRecord;
import ua.foxminded.university_schedule_app.JPARepositories.GradeRecordRepository;

@Service
public class GradeRecordService {
    private static final Logger logger = LoggerFactory.getLogger(GradeRecordService.class);
    private final GradeRecordRepository gradeRecordRepository;
    
    @Autowired 
    public GradeRecordService(GradeRecordRepository gradeRecordRepository) {
        this.gradeRecordRepository = gradeRecordRepository;
    }
    
    @Transactional
    public GradeRecord addGradeRecord(GradeRecord gradeRecord) {
        
        logger.info("Adding grade record for student {} in course {}.", gradeRecord.getStudent().getUserId(), gradeRecord.getCourse().getCourseId());
        GradeRecord savedGrade = gradeRecordRepository.save(gradeRecord);
        logger.info("Grade added successfully for student {} in course {}.", gradeRecord.getStudent().getUserId(), gradeRecord.getCourse().getCourseId());
        return savedGrade;
    }
    
    @Transactional(readOnly = true)
    public List<GradeRecord> getAllGrades() {
        logger.info("Fetching all grade records.");
        return gradeRecordRepository.findAll();
    }
    
    @Transactional
    public void deleteGradeRecord(Integer gradeId) {
        if (!gradeRecordRepository.existsById(gradeId)) {
            throw new EntityNotFoundException("Grade record not found with id: " + gradeId);
        }
        logger.info("Deleting grade record with id: {}", gradeId);
        gradeRecordRepository.deleteById(gradeId);
    }
    
    @Transactional(readOnly = true)
    public Optional<GradeRecord> getGradeRecordById(Integer gradeId) {
        logger.info("Fetching grade record with id {}.", gradeId);
        return gradeRecordRepository.findByGradeId(gradeId);
    }
    
  //all student's grades
    @Transactional(readOnly = true)
    public List<GradeRecord> getGradesByStudent(Integer studentId) {
        logger.info("Starting getGradesByStudent method for student with id {}.", studentId);
        return gradeRecordRepository.findByStudent_UserId(studentId);
    }
    
 // All grades of a student, teacher, newest first
    @Transactional(readOnly = true)
    public List<GradeRecord> getGradesByStudentNewestFirst(Integer studentId) {
        logger.info("Starting getGradesByStudentNewestFirst method for student with id {}.", studentId);
        return gradeRecordRepository.findByStudent_UserIdOrderByExamDateDesc(studentId);
    }
    
    @Transactional(readOnly = true)
    public List<GradeRecord> getGradesByTeacherNewestFirst(Integer teacherId) {
        logger.info("Starting getGradesByTeacherNewestFirst method for teacher with id {}.", teacherId);
        return gradeRecordRepository.findByTeacher_UserIdOrderByExamDateDesc(teacherId);
    } 
    
  //all student's grades for one course (filter) , newest first
    @Transactional(readOnly = true)
    public List<GradeRecord> getGradesByStudentAndCourseNewestFirst(Integer studentId, Integer courseId) {
        logger.info("Starting getGradesByStudentAndCourseNewestFirst method for student {} for the course {}.", studentId, courseId);
        return gradeRecordRepository.findByStudent_UserIdAndCourse_CourseIdOrderByExamDateDesc(studentId, courseId);
    }
    
  //filter all student's grades for one day
    @Transactional(readOnly = true)
    public List<GradeRecord> getGradesByStudentAndDateRange(Integer studentId, LocalDate startDate, LocalDate endDate) {
        logger.info("Starting getGradesByStudentAndDateRange method for student {} between {} and {}.", studentId, startDate, endDate);
        return gradeRecordRepository.findByStudent_UserIdAndExamDateBetweenOrderByExamDateDesc(studentId, startDate, endDate);
    }
    
  //teacher can see GradeRecords for this student in the teacher’s courses.
    @Transactional(readOnly = true)
    public List<GradeRecord> getGradesByStudentAndTeacher(Integer studentId, Integer teacherId) {
        logger.info("Starting getGradesByStudentAndTeacher method for student {} and teacher {}.", studentId, teacherId);
        return gradeRecordRepository.findByStudent_UserIdAndTeacher_UserIdOrderByExamDateDesc(studentId, teacherId);
    }
}
