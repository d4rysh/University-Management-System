package ua.foxminded.university_schedule_app.ServicesTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import jakarta.persistence.EntityNotFoundException;
import ua.foxminded.university_schedule_app.Entity.Classroom;
import ua.foxminded.university_schedule_app.Entity.Course;
import ua.foxminded.university_schedule_app.Entity.Group;
import ua.foxminded.university_schedule_app.Entity.Lesson;
import ua.foxminded.university_schedule_app.Entity.Teacher;
import ua.foxminded.university_schedule_app.Entity.TimeSlot;
import ua.foxminded.university_schedule_app.JPARepositories.LessonRepository;
import ua.foxminded.university_schedule_app.Service.LessonService;

@SpringBootTest(classes = {LessonService.class})
public class LessonServiceTests {

    @MockBean
    private LessonRepository lessonRepository;

    @Autowired
    private LessonService lessonService;

    private Lesson getLessonEntity() {
        Lesson lesson = new Lesson();
        lesson.setLessonId(1);
        lesson.setLessonDate(LocalDate.of(2025, 5, 1));

        Group group = new Group();
        group.setGroupId(100);

        Teacher teacher = new Teacher();
        teacher.setUserId(200);

        Course course = new Course();
        course.setCourseId(300);

        Classroom classroom = new Classroom();
        classroom.setClassroomId(400);

        TimeSlot timeSlot = new TimeSlot();
        timeSlot.setSlotName("First lesson");

        lesson.setGroup(group);
        lesson.setTeacher(teacher);
        lesson.setCourse(course);
        lesson.setClassroom(classroom);
        lesson.setTimeSlot(timeSlot);

        return lesson;
    }

    @Test
    void addLesson_shouldThrowException_whenGroupAlreadyHasLessonAtThisTime() {
        Lesson lesson = getLessonEntity();
        when(lessonRepository.existsByGroup_GroupIdAndDateAndTimeSlot_SlotName(
                100, lesson.getLessonDate(), "First lesson")).thenReturn(true);

        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> lessonService.addLesson(lesson));

        assertEquals("Group already has a lesson at this time.", e.getMessage());
    }

    @Test
    void addLesson_shouldThrowException_whenTeacherAlreadyHasLessonAtThisTime() {
        Lesson lesson = getLessonEntity();
        when(lessonRepository.existsByGroup_GroupIdAndDateAndTimeSlot_SlotName(
                100, lesson.getLessonDate(), "First lesson")).thenReturn(false);
        when(lessonRepository.existsByTeacher_UserIdAndDateAndTimeSlot_SlotName(
                200, lesson.getLessonDate(), "First lesson")).thenReturn(true);

        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> lessonService.addLesson(lesson));

        assertEquals("Teacher already has a lesson at this time.", e.getMessage());
    }

    @Test
    void addLesson_shouldSaveLesson_whenNoConflicts() {
        Lesson lesson = getLessonEntity();
        when(lessonRepository.existsByGroup_GroupIdAndDateAndTimeSlot_SlotName(
                100, lesson.getLessonDate(), "First lesson")).thenReturn(false);
        when(lessonRepository.existsByTeacher_UserIdAndDateAndTimeSlot_SlotName(
                200, lesson.getLessonDate(), "First lesson")).thenReturn(false);
        when(lessonRepository.save(any(Lesson.class))).thenReturn(lesson);

        Lesson result = lessonService.addLesson(lesson);

        assertEquals(1, result.getLessonId());
        verify(lessonRepository).save(any(Lesson.class));
    }

    @Test
    void deleteLesson_shouldThrowException_whenLessonNotExists() {
        when(lessonRepository.existsById(99)).thenReturn(false);

        EntityNotFoundException e = assertThrows(EntityNotFoundException.class,
                () -> lessonService.deleteLesson(99));

        assertEquals("Lesson not found with id: 99", e.getMessage());
    }

    @Test
    void deleteLesson_shouldDelete_whenLessonExists() {
        when(lessonRepository.existsById(1)).thenReturn(true);

        lessonService.deleteLesson(1);

        verify(lessonRepository).deleteById(1);
    }
}