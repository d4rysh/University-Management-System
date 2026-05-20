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
import ua.foxminded.university_schedule_app.Entity.Course;
import ua.foxminded.university_schedule_app.JPARepositories.CourseRepository;
import ua.foxminded.university_schedule_app.Service.CourseService;

@SpringBootTest(classes = {CourseService.class})
public class CourseServiceTests {

    @MockBean
    private CourseRepository courseRepository;

    @Autowired
    private CourseService courseService;

    private Course getCourseEntity() {
        Course course = new Course();
        course.setCourseId(1);
        course.setCourseName("Math");
        course.setCourseDescription("Fundamentals of mathematics including algebra and geometry");
        return course;
    }

    @Test
    void addCourse_shouldThrowException_whenCourseNameAlreadyExists() {
        Course course = getCourseEntity();
        when(courseRepository.existsByCourseName("Math")).thenReturn(true);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> courseService.addCourse(course));

        assertEquals("Course with this name already exists!", e.getMessage());
    }

    @Test
    void addCourse_shouldSaveCourse_whenValid() {
        Course course = getCourseEntity();
        when(courseRepository.existsByCourseName("Math")).thenReturn(false);
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        Course result = courseService.addCourse(course);

        assertEquals("Math", result.getCourseName());
        assertEquals(1, result.getCourseId());
        verify(courseRepository).save(any(Course.class));
    }

    @Test
    void deleteCourse_shouldThrowException_whenCourseNotExists() {
        when(courseRepository.existsById(99)).thenReturn(false);

        EntityNotFoundException e = assertThrows(EntityNotFoundException.class,
                () -> courseService.deleteCourse(99));

        assertEquals("Course not found with id: 99", e.getMessage());
    }

    @Test
    void deleteCourse_shouldDelete_whenCourseExists() {
        when(courseRepository.existsById(1)).thenReturn(true);

        courseService.deleteCourse(1);

        verify(courseRepository).deleteById(1);
    }
}