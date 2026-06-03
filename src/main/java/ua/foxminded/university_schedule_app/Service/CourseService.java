package ua.foxminded.university_schedule_app.Service;

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
import ua.foxminded.university_schedule_app.Entity.Classroom;
import ua.foxminded.university_schedule_app.Entity.Course;
import ua.foxminded.university_schedule_app.JPARepositories.CourseRepository;
import ua.foxminded.university_schedule_app.Util.PaginationValidator;

@Service
public class CourseService {
    private static final Logger logger = LoggerFactory.getLogger(CourseService.class);
    private final CourseRepository courseRepository;
    
    @Autowired 
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }
    
    @Transactional
    public Course addCourse(Course course) {
        if (courseRepository.existsByCourseName(course.getCourseName())) {
            throw new IllegalArgumentException("Course with this name already exists!");
        }
        logger.info("Starting addCourse for course: {}.", course.getCourseName());
        Course savedCourse = courseRepository.save(course);
        logger.info("Course added successfully with id: {}", savedCourse.getCourseId());
        return savedCourse;
    }
    
    @Transactional(readOnly = true)
    public Page<Course> getAllCourses(int pageNumber, int pageSize) {
        PaginationValidator.validatePagination(pageNumber, pageSize);
        logger.info("Starting getAllCourses method.");
        Page<Course> courses = courseRepository.findAll(PageRequest.of(pageNumber, pageSize));
        return courses;
}
    
    @Transactional
    public void deleteCourse(Integer courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new EntityNotFoundException("Course not found with id: " + courseId);
        }
        logger.info("Deleting course with id: {}", courseId);
        courseRepository.deleteById(courseId);
    }
    
    @Transactional(readOnly = true)
    public Optional<Course> getCourseByCourseName(String courseName) {
        logger.info("Starting getCourseByCourseName method for: {}.", courseName);
        return courseRepository.findByCourseName(courseName);
    }
    
    @Transactional(readOnly = true)
    public List<Course> getCoursesByTeacherId (Integer userId) {
        logger.info("Starting getCoursesByTeacherId method for teacher with id {}.", userId);
        return courseRepository.findByTeacher_UserId(userId);
    }
    
    @Transactional(readOnly = true)
    public boolean existsByCourseName(String courseName) {
        logger.info("Starting existsByCourseName method for: {}.", courseName);
        return courseRepository.existsByCourseName(courseName);
    }
    
}
