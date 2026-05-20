package ua.foxminded.university_schedule_app.ServicesTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import jakarta.persistence.EntityNotFoundException;
import ua.foxminded.university_schedule_app.Entity.Course;
import ua.foxminded.university_schedule_app.Entity.GradeRecord;
import ua.foxminded.university_schedule_app.Entity.Student;
import ua.foxminded.university_schedule_app.Entity.Teacher;
import ua.foxminded.university_schedule_app.JPARepositories.GradeRecordRepository;
import ua.foxminded.university_schedule_app.Service.GradeRecordService;

@SpringBootTest(classes = {GradeRecordService.class})
public class GradeRecordServiceTests {

    @MockBean
    private GradeRecordRepository gradeRecordRepository;

    @Autowired
    private GradeRecordService gradeRecordService;

    private GradeRecord getGradeRecordEntity() {
        GradeRecord gradeRecord = new GradeRecord();

        Student student = new Student();
        student.setUserId(10);

        Course course = new Course();
        course.setCourseId(20);

        Teacher teacher = new Teacher();
        teacher.setUserId(30);

        gradeRecord.setGradeId(1);
        gradeRecord.setStudent(student);
        gradeRecord.setCourse(course);
        gradeRecord.setTeacher(teacher);
        gradeRecord.setGrade(95.0);
        gradeRecord.setExamDate(LocalDate.of(2025, 5, 1));

        return gradeRecord;
    }


    @Test
    void addGradeRecord_shouldSaveGradeRecord_whenValid() {
        GradeRecord gradeRecord = getGradeRecordEntity();
        when(gradeRecordRepository.save(any(GradeRecord.class))).thenReturn(gradeRecord);

        GradeRecord result = gradeRecordService.addGradeRecord(gradeRecord);

        assertEquals(1, result.getGradeId());
        verify(gradeRecordRepository).save(any(GradeRecord.class));
    }

    @Test
    void deleteGradeRecord_shouldThrowException_whenGradeNotExists() {
        when(gradeRecordRepository.existsById(99)).thenReturn(false);

        EntityNotFoundException e = assertThrows(EntityNotFoundException.class,
                () -> gradeRecordService.deleteGradeRecord(99));

        assertEquals("Grade record not found with id: 99", e.getMessage());
    }

    @Test
    void deleteGradeRecord_shouldDelete_whenGradeExists() {
        when(gradeRecordRepository.existsById(1)).thenReturn(true);

        gradeRecordService.deleteGradeRecord(1);

        verify(gradeRecordRepository).deleteById(1);
    }
}