package ua.foxminded.university_schedule_app.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    
    private void validatePagination(int pageNumber, int pageSize) {
        if (pageNumber<0) {
            throw new IllegalArgumentException("pageNumber must be 0 and over");
        }
        if (pageSize>15 || pageSize<=0) {
            throw new IllegalArgumentException("pageSize must be from 1 to 15");
        }
    }
    
    @Transactional
    public GradeRecord addGradeRecord(GradeRecord gradeRecord) {
        
        logger.info("Adding grade record for student {} in course {}.", gradeRecord.getStudent().getUserId(), gradeRecord.getCourse().getCourseId());
        GradeRecord savedGrade = gradeRecordRepository.save(gradeRecord);
        logger.info("Grade added successfully for student {} in course {}.", gradeRecord.getStudent().getUserId(), gradeRecord.getCourse().getCourseId());
        return savedGrade;
    }
    
    @Transactional(readOnly = true)
    public Page<GradeRecord> getAllGrades(int pageNumber, int pageSize) {
        validatePagination(pageNumber, pageSize);
        logger.info("Fetching all grade records.");
        Page<GradeRecord> grades = gradeRecordRepository.findAll(PageRequest.of(pageNumber, pageSize));
        return grades;
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
        return gradeRecordRepository.findById(gradeId);
    }
    
 // All grades of a student, teacher, newest first
    @Transactional(readOnly = true)
    public Page<GradeRecord> getGradesByStudentNewestFirst(Integer studentId, int pageNumber, int pageSize) {
        validatePagination(pageNumber, pageSize);
        logger.info("Starting getGradesByStudentNewestFirst method for student with id {}.", studentId);
        Page<GradeRecord> grades = gradeRecordRepository.findByStudent_UserIdOrderByExamDateDesc(studentId, PageRequest.of(pageNumber, pageSize));
        return grades;
    }
    
    @Transactional(readOnly = true)
    public Page<GradeRecord> getGradesByTeacherNewestFirst(Integer teacherId, int pageNumber, int pageSize) {
        validatePagination(pageNumber, pageSize);
        logger.info("Starting getGradesByTeacherNewestFirst method for teacher with id {}.", teacherId);
        Page<GradeRecord> grades = gradeRecordRepository.findByTeacher_UserIdOrderByExamDateDesc(teacherId, PageRequest.of(pageNumber, pageSize));
        return grades;
    } 
    
  //all student's grades for one course (filter) , newest first
    @Transactional(readOnly = true)
    public Page<GradeRecord> getGradesByStudentAndCourseNewestFirst(Integer studentId, Integer courseId, int pageNumber, int pageSize) {
        validatePagination(pageNumber, pageSize);
        logger.info("Starting getGradesByStudentAndCourseNewestFirst method for student {} for the course {}.", studentId, courseId);
        Page<GradeRecord> grades = gradeRecordRepository.findByStudent_UserIdAndCourse_CourseIdOrderByExamDateDesc(studentId, courseId, PageRequest.of(pageNumber, pageSize));
        return grades;
    }
   
  //teacher can see GradeRecords for this student in the teacher’s courses.
    @Transactional(readOnly = true)
    public Page<GradeRecord> getGradesByStudentAndTeacher(Integer studentId, Integer teacherId, int pageNumber, int pageSize) {
        validatePagination(pageNumber, pageSize);
        logger.info("Starting getGradesByStudentAndTeacher method for student {} and teacher {}.", studentId, teacherId);
        Page<GradeRecord> grades = gradeRecordRepository.findByStudent_UserIdAndTeacher_UserIdOrderByExamDateDesc(studentId, teacherId, PageRequest.of(pageNumber, pageSize));
        return grades;
    }
}
