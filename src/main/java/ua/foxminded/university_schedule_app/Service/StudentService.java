package ua.foxminded.university_schedule_app.Service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.foxminded.university_schedule_app.Entity.Classroom;
import ua.foxminded.university_schedule_app.Entity.Group;
import ua.foxminded.university_schedule_app.Entity.Student;
import ua.foxminded.university_schedule_app.JPARepositories.GroupRepository;
import ua.foxminded.university_schedule_app.JPARepositories.StudentRepository;
import ua.foxminded.university_schedule_app.Util.PaginationValidator;

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
    
    @Transactional
    public Student addStudent(Student student) {
        logger.info("Starting addStudent method.");
        return studentRepository.save(student);
    }
    
    @Transactional
    public void addStudentToGroup(Integer userId, Integer groupId) {
        logger.info("Starting addStudentToGroup method.");
        Student student = studentRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Student with id " + userId + " does not exist."));

            Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group with id " + groupId + " does not exist."));

            student.setGroup(group);
            logger.debug("Added student {} to group {}.", userId, groupId);
    }
    
    @Transactional
    public void removeStudentFromGroup(Integer userId) {
        logger.info("Starting removeStudentFromGroup method.");
        Student student = studentRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Student with id " + userId + " does not exist."));

            student.setGroup(null);
            logger.debug("Removed student {} from group", userId);
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
    public List<Student> getStudentsWithoutGroup() {
        logger.info("Starting getStudentsWithoutGroup method.");
        List<Student> students = studentRepository.findByGroupIsNull();
        return students;
    }
    
    @Transactional(readOnly = true)
    public Page<Student> searchStudentsByLastName(String lastName, int pageNumber, int pageSize) {
      PaginationValidator.validatePagination(pageNumber, pageSize);
      logger.info("Starting searchStudentsByLastName method.");
      Page<Student> students = studentRepository.findByLastNameContainingIgnoreCase(lastName, PageRequest.of(pageNumber, pageSize));
      return students;
  }
  
    @Transactional(readOnly = true)
    public List<Student> searchStudentsWithoutGroupByLastName(String lastName) {
        logger.info("Starting searchStudentsWithoutGroupByLastName method.");
        List<Student> students = studentRepository.findByGroupIsNullAndLastNameContainingIgnoreCase(lastName);
        logger.debug("Retrieved {} students without group matching last name '{}'.", students.size(), lastName);
        return students;
    }
    
}