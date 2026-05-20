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

import ua.foxminded.university_schedule_app.Entity.GradeRecord;
import ua.foxminded.university_schedule_app.JPARepositories.GradeRecordRepository;
import ua.foxminded.university_schedule_app.TestContainerConfig.PostgresTestContainerConfig;

@SpringBootTest(classes = { GradeRecordRepository .class})
@EnableAutoConfiguration
@EntityScan(basePackageClasses = GradeRecord.class)
@EnableJpaRepositories(basePackageClasses = GradeRecordRepository .class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class GradeRecordRepositoryTests extends PostgresTestContainerConfig {

    @Autowired
    private GradeRecordRepository gradeRecordRepository ;
    
    @Test
    @Transactional
    @Sql(scripts = {"/sql/insert_grade_record.sql"},
         executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void findByGradeId_shouldReturnGradeRecord_whenExists() {
        Optional<GradeRecord> record = gradeRecordRepository.findByGradeId(1);

        assertTrue(record.isPresent());
        assertEquals(95.0, record.get().getGrade());
    }

    @Test
    @Transactional
    @Sql(scripts = {"/sql/insert_grade_record.sql"},
         executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void findByStudentUserId_shouldReturnGrades_whenStudentExists() {
        List<GradeRecord> records = gradeRecordRepository.findByStudent_UserId(2);

        assertEquals(2, records.size());
    }

    @Test
    @Transactional
    @Sql(scripts = {"/sql/insert_grade_record.sql"},
         executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void findByTeacherUserIdOrderByExamDateDesc_shouldReturnGradesSorted() {
        List<GradeRecord> records = gradeRecordRepository.findByTeacher_UserIdOrderByExamDateDesc(3);

        assertEquals(2, records.size());
        assertTrue(records.get(0).getExamDate().isAfter(records.get(1).getExamDate()));
    }

    @Test
    @Transactional
    @Sql(scripts = {"/sql/insert_grade_record.sql"},
         executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void findByStudentUserIdAndCourseCourseIdOrderByExamDateDesc_shouldReturnGradesForCourse() {
        List<GradeRecord> records = gradeRecordRepository.findByStudent_UserIdAndCourse_CourseIdOrderByExamDateDesc(2, 1);

        assertEquals(2, records.size());
        assertEquals(1, records.get(0).getCourse().getCourseId());
    }

    @Test
    @Transactional
    @Sql(scripts = {"/sql/insert_grade_record.sql"},
         executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void findByStudentUserIdAndExamDateBetweenOrderByExamDateDesc_shouldReturnGradesInDateRange() {
        LocalDate start = LocalDate.of(2025, 1, 1);
        LocalDate end = LocalDate.of(2025, 12, 31);

        List<GradeRecord> records = gradeRecordRepository.findByStudent_UserIdAndExamDateBetweenOrderByExamDateDesc(2, start, end);

        assertEquals(2, records.size());
    }
}
