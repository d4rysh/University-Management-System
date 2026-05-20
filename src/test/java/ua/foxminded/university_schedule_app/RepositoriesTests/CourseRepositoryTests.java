package ua.foxminded.university_schedule_app.RepositoriesTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
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

import ua.foxminded.university_schedule_app.Entity.Course;
import ua.foxminded.university_schedule_app.JPARepositories.CourseRepository;
import ua.foxminded.university_schedule_app.TestContainerConfig.PostgresTestContainerConfig;

@SpringBootTest(classes = { CourseRepository.class})
@EnableAutoConfiguration
@EntityScan(basePackageClasses = Course.class)
@EnableJpaRepositories(basePackageClasses = CourseRepository.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CourseRepositoryTests extends PostgresTestContainerConfig {

    @Autowired
    private CourseRepository courseRepository;
    
    @Test
    @Transactional
    @Sql(
        scripts = {"/sql/insert_course.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    void findByCourseName_shouldReturnCourse_whenCourseExists() {
        Optional<Course> course = courseRepository.findByCourseName("Math");

        assertTrue(course.isPresent());
        assertEquals("Math", course.get().getCourseName());
    }

    @Test
    @Transactional
    @Sql(
        scripts = {"/sql/insert_course.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    void existsByCourseName_shouldReturnTrue_whenCourseExists() {
        boolean exists = courseRepository.existsByCourseName("Math");

        assertTrue(exists);
    }

    @Test
    @Transactional
    @Sql(
        scripts = {"/sql/insert_two_courses_with_teacher.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    void findByTeacherUserId_shouldReturnCourses_whenTeacherHasCourses() {
        List<Course> courses = courseRepository.findByTeacher_UserId(4);

        assertEquals(2, courses.size());
        assertEquals("Math", courses.get(0).getCourseName());
        assertEquals("History", courses.get(1).getCourseName());
    }

    @Test
    @Transactional
    void findByCourseName_shouldReturnEmpty_whenCourseDoesNotExist() {
        Optional<Course> course = courseRepository.findByCourseName("Biology");

        assertTrue(course.isEmpty());
    }
}
