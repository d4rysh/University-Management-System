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
import ua.foxminded.university_schedule_app.Entity.Department;
import ua.foxminded.university_schedule_app.JPARepositories.DepartmentRepository;
@Service
public class DepartmentService {
    private static final Logger logger = LoggerFactory.getLogger(CourseService.class);
    private final DepartmentRepository departmentRepository;
    
    @Autowired 
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }
    
    @Transactional
    public Department addDepartment(Department department) {
        if (departmentRepository.existsByDepartmentName(department.getDepartmentName())) {
            throw new IllegalArgumentException("Department with this name already exists!");
        }
        logger.info("Starting addDepartment for department: {}.", department.getDepartmentName());
        Department savedDepartment = departmentRepository.save(department);
        logger.info("Department added successfully with id: {}", savedDepartment.getDepartmentId());
        return savedDepartment;
    }
    
    @Transactional(readOnly = true)
    public Page<Department> getAllDepartments(int pageNumber, int pageSize) {
        if (pageNumber<0) {
            throw new IllegalArgumentException("pageNumber must be 0 and over");
        }
        if (pageSize>15 || pageSize<=0) {
            throw new IllegalArgumentException("pageSize must be from 1 to 15");
        }
        logger.info("Starting getAllDepartments method.");
        Page<Department> departments = departmentRepository.findAll(PageRequest.of(pageNumber, pageSize));
        return departments;
}
    
    @Transactional
    public void deleteCourse(Integer departmentId) {
        if (!departmentRepository.existsById(departmentId)) {
            throw new EntityNotFoundException("Department not found with id: " + departmentId);
        }
        logger.info("Deleting department with id: {}", departmentId);
        departmentRepository.deleteById(departmentId);
    }
    
    
    @Transactional(readOnly = true)
    public Optional<Department> getDepartmentDepartmentName(String departmentName) {
        logger.info("Starting getDepartmentDepartmentName method for: {}.", departmentName);
        return departmentRepository.findByDepartmentName(departmentName);
    }
   
    
    @Transactional(readOnly = true)
    public Optional<Department> getDepartmentByTeacherId(Integer teacherId) {
        logger.info("Starting getDepartmentByTeacherId method for teacher with id {}.", teacherId);
        return departmentRepository.findDepartmentByTeacherId(teacherId);
    }
    
    @Transactional(readOnly = true)
    public boolean existsByDepartmentName (String departmentName) {
        logger.info("Starting existsByDepartmentName method for department {}.", departmentName);
        return departmentRepository.existsByDepartmentName(departmentName);
    }
    
}

