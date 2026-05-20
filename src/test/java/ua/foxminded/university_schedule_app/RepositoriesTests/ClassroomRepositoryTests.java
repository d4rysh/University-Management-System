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

import ua.foxminded.university_schedule_app.Entity.Classroom;
import ua.foxminded.university_schedule_app.JPARepositories.ClassroomRepository;
import ua.foxminded.university_schedule_app.TestContainerConfig.PostgresTestContainerConfig;

@SpringBootTest(classes = { ClassroomRepository.class})
@EnableAutoConfiguration
@EntityScan(basePackageClasses = Classroom.class)
@EnableJpaRepositories(basePackageClasses = ClassroomRepository.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ClassroomRepositoryTests extends PostgresTestContainerConfig {
    
    @Autowired
    private ClassroomRepository classroomRepository;
    
    @Test
    @Transactional
    @Sql(
        scripts = {"/sql/insert_classroom.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    void findByRoomNumber_shouldReturnClassroom_whenRoomExists() {
        Optional<Classroom> classroom = classroomRepository.findByRoomNumber(304);

        assertTrue(classroom.isPresent());
        assertEquals(304, classroom.get().getRoomNumber());
    }
    
    @Test
    @Transactional
    void findByRoomNumber_shouldReturnEmpty_whenRoomDoesNotExist() {
        Optional<Classroom> classroom = classroomRepository.findByRoomNumber(999);

        assertTrue(classroom.isEmpty());
    }

    @Test
    @Transactional
    @Sql(
        scripts = {"/sql/insert_classroom.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    void existsByRoomNumber_shouldReturnTrue_whenRoomExists() {
        boolean exists = classroomRepository.existsByRoomNumber(304);

        assertTrue(exists);
    }

}
