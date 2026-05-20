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

import ua.foxminded.university_schedule_app.Entity.TimeSlot;
import ua.foxminded.university_schedule_app.JPARepositories.TimeSlotRepository;
import ua.foxminded.university_schedule_app.TestContainerConfig.PostgresTestContainerConfig;
@SpringBootTest(classes = { TimeSlotRepository .class})
@EnableAutoConfiguration
@EntityScan(basePackageClasses = TimeSlot.class)
@EnableJpaRepositories(basePackageClasses = TimeSlotRepository .class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TimeSlotRepositoryTests extends PostgresTestContainerConfig {

    @Autowired
    private TimeSlotRepository timeSlotRepository;
    
    @Test
    @Transactional
    @Sql("/sql/insert_timeslot.sql")
    void findBySlotName_shouldReturnTimeSlot_whenExists() {
        Optional<TimeSlot> slot = timeSlotRepository.findBySlotName("First lesson");
        assertTrue(slot.isPresent());
        assertEquals("First lesson", slot.get().getSlotName());
    }

    @Test
    @Transactional
    @Sql("/sql/insert_timeslot.sql")
    void findBySlotName_shouldReturnEmpty_whenNotExists() {
        Optional<TimeSlot> slot = timeSlotRepository.findBySlotName("Non-existent lesson");
        assertTrue(slot.isEmpty());
    }
}
