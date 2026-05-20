package ua.foxminded.university_schedule_app.ServicesTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import jakarta.persistence.EntityNotFoundException;
import ua.foxminded.university_schedule_app.Entity.Department;
import ua.foxminded.university_schedule_app.JPARepositories.DepartmentRepository;
import ua.foxminded.university_schedule_app.Service.DepartmentService;

@SpringBootTest(classes = {DepartmentService.class})
public class DepartmentServiceTests {

    @MockBean
    private DepartmentRepository departmentRepository;

    @Autowired
    private DepartmentService departmentService;

    private Department getDepartmentEntity() {
        Department department = new Department();
        department.setDepartmentId(1);
        department.setDepartmentName("Mathematics");
        return department;
    }

    @Test
    void addDepartment_shouldThrowException_whenDepartmentNameAlreadyExists() {
        Department department = getDepartmentEntity();
        when(departmentRepository.existsByDepartmentName("Mathematics")).thenReturn(true);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> departmentService.addDepartment(department));

        assertEquals("Department with this name already exists!", e.getMessage());
    }

    @Test
    void addDepartment_shouldSaveDepartment_whenValid() {
        Department department = getDepartmentEntity();
        when(departmentRepository.existsByDepartmentName("Mathematics")).thenReturn(false);
        when(departmentRepository.save(any(Department.class))).thenReturn(department);

        Department result = departmentService.addDepartment(department);

        assertEquals("Mathematics", result.getDepartmentName());
        assertEquals(1, result.getDepartmentId());
        verify(departmentRepository).save(any(Department.class));
    }

    @Test
    void deleteDepartment_shouldThrowException_whenDepartmentNotExists() {
        when(departmentRepository.existsById(99)).thenReturn(false);

        EntityNotFoundException e = assertThrows(EntityNotFoundException.class,
                () -> departmentService.deleteCourse(99));

        assertEquals("Department not found with id: 99", e.getMessage());
    }

    @Test
    void deleteDepartment_shouldDelete_whenDepartmentExists() {
        when(departmentRepository.existsById(1)).thenReturn(true);

        departmentService.deleteCourse(1);

        verify(departmentRepository).deleteById(1);
    }
}
