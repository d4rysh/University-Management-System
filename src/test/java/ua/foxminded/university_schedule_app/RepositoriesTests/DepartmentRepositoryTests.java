package ua.foxminded.university_schedule_app.RepositoriesTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import ua.foxminded.university_schedule_app.Entity.Department;
import ua.foxminded.university_schedule_app.JPARepositories.DepartmentRepository;
import ua.foxminded.university_schedule_app.TestContainerConfig.PostgresTestContainerConfig;

@SpringBootTest(classes = { DepartmentRepository.class})
@EnableAutoConfiguration
@EntityScan(basePackageClasses = Department.class)
@EnableJpaRepositories(basePackageClasses = DepartmentRepository.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DepartmentRepositoryTests extends PostgresTestContainerConfig {

    @Autowired
    private DepartmentRepository departmentRepository;
    
    @Test
    @Transactional
    @Sql(
        scripts = {"/sql/insert_department.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    void findByDepartmentName_shouldReturnDepartment_whenExists() {
        Optional<Department> department = departmentRepository.findByDepartmentName("Mathematics Department");

        assertTrue(department.isPresent());
        assertEquals("Mathematics Department", department.get().getDepartmentName());
    }
    
    @Test
    @Transactional
    void findByDepartmentName_shouldReturnEmpty_whenDepartmentDoesNotExist() {
        Optional<Department> department = departmentRepository.findByDepartmentName("Physics Department");

        assertTrue(department.isEmpty());
    }

    @Test
    @Transactional
    @Sql(
        scripts = {"/sql/insert_department_with_teacher.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    void findDepartmentByTeacherId_shouldReturnDepartment_whenTeacherExists() {
        Optional<Department> department = departmentRepository.findDepartmentByTeacherId(4);

        assertTrue(department.isPresent());
        assertEquals("History Department", department.get().getDepartmentName());
    }

    @Test
    @Transactional
    @Sql(
        scripts = {"/sql/insert_department.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    void existsByDepartmentName_shouldReturnTrue_whenDepartmentExists() {
        boolean exists = departmentRepository.existsByDepartmentName("Mathematics Department");

        assertTrue(exists);
    }
}