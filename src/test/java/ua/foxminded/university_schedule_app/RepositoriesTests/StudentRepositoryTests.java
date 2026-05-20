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

import ua.foxminded.university_schedule_app.Entity.Student;
import ua.foxminded.university_schedule_app.JPARepositories.StudentRepository;
import ua.foxminded.university_schedule_app.TestContainerConfig.PostgresTestContainerConfig;
@SpringBootTest(classes = { StudentRepository .class})
@EnableAutoConfiguration
@EntityScan(basePackageClasses = Student.class)
@EnableJpaRepositories(basePackageClasses = StudentRepository .class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StudentRepositoryTests extends PostgresTestContainerConfig {

    @Autowired
    private StudentRepository studentRepository;
    
    @Test
    @Transactional
    @Sql("/sql/insert_students.sql")
    void findStudentsByCourseName_shouldReturnStudents_whenExists() {
        List<Student> students = studentRepository.findStudentsByCourseName("Math");
        assertEquals(2, students.size());
        assertEquals("Alice", students.get(0).getFirstName());
        assertEquals("Bob", students.get(1).getFirstName());
    }

    @Test
    @Transactional
    @Sql("/sql/insert_students.sql")
    void countStudentsInGroup_shouldReturnCorrectCount() {
        int count = studentRepository.countStudentsInGroup(200);
        assertEquals(2, count);
    }

    @Test
    @Transactional
    @Sql("/sql/insert_students.sql")
    void findByGroupId_shouldReturnStudents_whenExists() {
        List<Student> students = studentRepository.findByGroup_GroupId(200);
        assertEquals(2, students.size());
    }

    @Test
    @Transactional
    @Sql("/sql/insert_students.sql")
    void existsByEmail_shouldReturnTrue_whenStudentExists() {
        assertTrue(studentRepository.existsByEmail("student1@example.com"));
    }

    @Test
    @Transactional
    @Sql("/sql/insert_students.sql")
    void existsByEmail_shouldReturnFalse_whenStudentNotExists() {
        assertFalse(studentRepository.existsByEmail("ghost@example.com"));
    }

    @Test
    @Transactional
    @Sql("/sql/insert_students.sql")
    void findByLastName_shouldReturnStudents_whenExists() {
        List<Student> students = studentRepository.findByLastName("Brown");
        assertEquals(1, students.size());
        assertEquals("Bob", students.get(0).getFirstName());
    }

    @Test
    @Transactional
    @Sql("/sql/insert_students.sql")
    void findByLastName_shouldReturnEmpty_whenNotExists() {
        List<Student> students = studentRepository.findByLastName("NotExists");
        assertTrue(students.isEmpty());
    }
}
