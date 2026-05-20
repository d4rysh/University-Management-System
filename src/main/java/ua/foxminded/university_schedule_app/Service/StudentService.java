package ua.foxminded.university_schedule_app.Service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.foxminded.university_schedule_app.Entity.Student;
import ua.foxminded.university_schedule_app.JPARepositories.GroupRepository;
import ua.foxminded.university_schedule_app.JPARepositories.StudentRepository;

@Service
public class StudentService {
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;
    
    @Autowired 
    public StudentService(StudentRepository studentRepository, GroupRepository groupRepository) {
        this.studentRepository = studentRepository;
        this.groupRepository = groupRepository;
    }
    
    @Transactional(readOnly = true)
    public List<Student> getAllStudents() {
        logger.info("Starting getAllStudents method.");
        List<Student> students = studentRepository.findAll();
        logger.debug("Retrieved {} students from database.", students.size());
        return students;
    }
    
    @Transactional(readOnly = true)
    public List<Student> getStudentsByCourseName(String courseName) {
        logger.info("Starting getStudentsByCourseName method.");
        List<Student> students = studentRepository.findStudentsByCourseName(courseName);
        logger.debug("Retrieved {} students from database by course {}.", students.size(), courseName);
        return students;
    }
    
    @Transactional(readOnly = true)
    public int countStudentsInGroup(int groupId) {
        if (!groupRepository.existsByGroupId(groupId)) {
            throw new IllegalArgumentException("Group with id " + groupId + " does not exist.");
        }
        
        logger.info("Starting countStudentsInGroup method for group {}.", groupId);
        int count = studentRepository.countStudentsInGroup(groupId);
        logger.debug("Counted students in a group {} result: {}", groupId, count);
        return count;
    }
    
    @Transactional(readOnly = true)
    public List<Student> getStudentsByGroupId(Integer groupId) {
        logger.info("Starting getStudentsByGroupId method.");
        List<Student> students = studentRepository.findByGroup_GroupId(groupId);
        logger.debug("Retrieved {} students from database by group {}.", students.size(), groupId);
        return students;
    }
    
    @Transactional(readOnly = true)
    public List<Student> getStudentsByLastName(String lastName) {
        logger.info("Starting getStudentsByLastName method.");
        List<Student> students = studentRepository.findByLastName(lastName);
        logger.debug("Retrieved {} students from database by last name {}.", lastName);
        return students;
    }
    
}