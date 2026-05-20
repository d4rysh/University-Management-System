package ua.foxminded.university_schedule_app.RepositoriesTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import ua.foxminded.university_schedule_app.Entity.Teacher;
import ua.foxminded.university_schedule_app.JPARepositories.TeacherRepository;
import ua.foxminded.university_schedule_app.TestContainerConfig.PostgresTestContainerConfig;
@SpringBootTest(classes = { TeacherRepository .class})
@EnableAutoConfiguration
@EntityScan(basePackageClasses = Teacher.class)
@EnableJpaRepositories(basePackageClasses = TeacherRepository .class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TeacherRepositoryTests extends PostgresTestContainerConfig {

    @Autowired
    private TeacherRepository teacherRepository;
    
    @Test
    @Transactional
    @Sql("/sql/insert_teachers.sql")
    void existsByEmail_shouldReturnTrue_whenTeacherExists() {
        assertTrue(teacherRepository.existsByEmail("teacher1@example.com"));
    }

    @Test
    @Transactional
    @Sql("/sql/insert_teachers.sql")
    void existsByEmail_shouldReturnFalse_whenTeacherNotExists() {
        assertFalse(teacherRepository.existsByEmail("notExists@example.com"));
    }

    @Test
    @Transactional
    @Sql("/sql/insert_teachers.sql")
    void findByDepartmentId_shouldReturnTeachers_whenExists() {
        List<Teacher> teachers = teacherRepository.findByDepartment_DepartmentId(100);
        assertEquals(2, teachers.size());
    }

    @Test
    @Transactional
    @Sql("/sql/insert_teachers.sql")
    void findTeachersByCourseName_shouldReturnTeachers_whenExists() {
        List<Teacher> teachers = teacherRepository.findTeachersByCourseName("Math");
        assertEquals(1, teachers.size());
        assertEquals("Alice", teachers.get(0).getFirstName());
    }

    @Test
    @Transactional
    @Sql("/sql/insert_teachers.sql")
    void findTeachersByGroupId_shouldReturnTeachers_whenExists() {
        List<Teacher> teachers = teacherRepository.findTeachersByGroupId(200);
        assertEquals(1, teachers.size());
        assertEquals("Bob", teachers.get(0).getFirstName());
    }

    @Test
    @Transactional
    @Sql("/sql/insert_teachers.sql")
    void findByLastName_shouldReturnTeachers_whenExists() {
        List<Teacher> teachers = teacherRepository.findByLastName("Brown");
        assertEquals(1, teachers.size());
        assertEquals("Bob", teachers.get(0).getFirstName());
    }

    @Test
    @Transactional
    @Sql("/sql/insert_teachers.sql")
    void findByLastName_shouldReturnEmpty_whenNotExists() {
        List<Teacher> teachers = teacherRepository.findByLastName("NotExists");
        assertTrue(teachers.isEmpty());
    }
}
