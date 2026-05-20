package ua.foxminded.university_schedule_app.RepositoriesTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
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

import ua.foxminded.university_schedule_app.Entity.Lesson;
import ua.foxminded.university_schedule_app.JPARepositories.LessonRepository;
import ua.foxminded.university_schedule_app.TestContainerConfig.PostgresTestContainerConfig;

@SpringBootTest(classes = { LessonRepository .class})
@EnableAutoConfiguration
@EntityScan(basePackageClasses = Lesson.class)
@EnableJpaRepositories(basePackageClasses = LessonRepository .class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LessonRepositoryTests extends PostgresTestContainerConfig {

    @Autowired
    private LessonRepository lessonRepository;
    
    @Test
    @Transactional
    @Sql("/sql/insert_lessons.sql")
    void findByGroupGroupIdAndDateBetween_shouldReturnLessons_whenExists() {
        List<Lesson> lessons = lessonRepository.findByGroup_GroupIdAndDateBetween(1,
                LocalDate.of(2025, 5, 1), LocalDate.of(2025, 5, 31));
        assertEquals(1, lessons.size());
    }

    @Test
    @Transactional
    @Sql("/sql/insert_lessons.sql")
    void findByTeacherUserIdAndDateBetween_shouldReturnLessons_whenExists() {
        List<Lesson> lessons = lessonRepository.findByTeacher_UserIdAndDateBetween(3,
                LocalDate.of(2025, 5, 1), LocalDate.of(2025, 5, 31));
        assertEquals(1, lessons.size());
    }

    @Test
    @Transactional
    @Sql("/sql/insert_lessons.sql")
    void findByCourseCourseIdAndDateBetween_shouldReturnLessons_whenExists() {
        List<Lesson> lessons = lessonRepository.findByCourse_CourseIdAndDateBetween(1,
                LocalDate.of(2025, 5, 1), LocalDate.of(2025, 5, 31));
        assertEquals(1, lessons.size());
    }

    @Test
    @Transactional
    @Sql("/sql/insert_lessons.sql")
    void findByDate_shouldReturnLessons_whenExists() {
        List<Lesson> lessons = lessonRepository.findByDate(LocalDate.of(2025, 5, 1));
        assertEquals(1, lessons.size());
    }

    @Test
    @Transactional
    @Sql("/sql/insert_lessons.sql")
    void findByGroupGroupIdAndDateAndTimeSlotSlotName_shouldReturnLesson_whenExists() {
        Optional<Lesson> lesson = lessonRepository.findByGroup_GroupIdAndDateAndTimeSlot_SlotName(1,
                LocalDate.of(2025, 5, 1), "First lesson");
        assertTrue(lesson.isPresent());
    }

    @Test
    @Transactional
    @Sql("/sql/insert_lessons.sql")
    void findByTeacherUserIdAndDateAndTimeSlotSlotName_shouldReturnLesson_whenExists() {
        Optional<Lesson> lesson = lessonRepository.findByTeacher_UserIdAndDateAndTimeSlot_SlotName(3,
                LocalDate.of(2025, 5, 1), "First lesson");
        assertTrue(lesson.isPresent());
    }

    @Test
    @Transactional
    @Sql("/sql/insert_lessons.sql")
    void existsByGroupGroupIdAndDateAndTimeSlotSlotName_shouldReturnTrue_whenLessonExists() {
        boolean exists = lessonRepository.existsByGroup_GroupIdAndDateAndTimeSlot_SlotName(1,
                LocalDate.of(2025, 5, 1), "First lesson");
        assertTrue(exists);
    }

    @Test
    @Transactional
    @Sql("/sql/insert_lessons.sql")
    void existsByTeacherUserIdAndDateAndTimeSlotSlotName_shouldReturnTrue_whenLessonExists() {
        boolean exists = lessonRepository.existsByTeacher_UserIdAndDateAndTimeSlot_SlotName(3,
                LocalDate.of(2025, 5, 1), "First lesson");
        assertTrue(exists);
    }
}
