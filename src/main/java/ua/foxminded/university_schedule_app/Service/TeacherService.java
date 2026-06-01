package ua.foxminded.university_schedule_app.Service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.foxminded.university_schedule_app.Entity.Teacher;
import ua.foxminded.university_schedule_app.JPARepositories.TeacherRepository;

@Service
public class TeacherService {
    private static final Logger logger = LoggerFactory.getLogger(TeacherService.class);
    private final TeacherRepository teacherRepository;
    
    @Autowired 
    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }
    
    @Transactional(readOnly = true)
    public List<Teacher> getAllTeachers() {
        logger.info("Starting getAllTeachers method.");
        List<Teacher> teachers = teacherRepository.findAll();
        logger.debug("Retrieved {} teachers from database.", teachers.size());
        return teachers;
    }
    
    @Transactional(readOnly = true)
    public List<Teacher> getTeachersByDepartmentId(Integer departmentId) {
        logger.info("Starting getTeachersByDepartmentId method.");
        List<Teacher> teachers = teacherRepository.findByDepartment_DepartmentId(departmentId);
        logger.debug("Retrieved {} teachers from database by department {}.", teachers.size(), departmentId);
        return teachers;
    }
    
    @Transactional(readOnly = true)
    public List<Teacher> getTeachersByCourseName(String courseName) {
        logger.info("Starting getTeachersByCourseName method.");
        List<Teacher> teachers = teacherRepository.findTeachersByCourseName(courseName);
        logger.debug("Retrieved {} teachers from database by course {}.", teachers.size(), courseName);
        return teachers;
    }
    
    @Transactional(readOnly = true)
    public List<Teacher> getTeachersByGroupId(Integer groupId) {
        logger.info("Starting getTeachersByGroupId method.");
        List<Teacher> teachers = teacherRepository.findTeachersByGroupId(groupId);
        logger.debug("Retrieved {} teachers from database that teach group {}.", teachers.size(), groupId);
        return teachers;
    }
    
    @Transactional(readOnly = true)
    public List<Teacher> getTeachersByLastName(String lastName) {
        logger.info("Starting getTeachersByLastName method.");
        List<Teacher> teachers = teacherRepository.findByLastNameStartingWith(lastName);
        logger.debug("Retrieved {} teachers from database by last name {}.", lastName);
        return teachers;
    }
    
}
