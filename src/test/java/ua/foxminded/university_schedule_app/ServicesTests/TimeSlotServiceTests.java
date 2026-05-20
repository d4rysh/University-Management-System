package ua.foxminded.university_schedule_app.ServicesTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import ua.foxminded.university_schedule_app.Entity.TimeSlot;
import ua.foxminded.university_schedule_app.JPARepositories.TimeSlotRepository;
import ua.foxminded.university_schedule_app.Service.TimeSlotService;

@SpringBootTest(classes = {TimeSlotService.class})
public class TimeSlotServiceTests {

    @MockBean
    private TimeSlotRepository timeSlotRepository;

    @Autowired
    private TimeSlotService timeSlotService;

    private TimeSlot getTimeSlotEntity() {
        TimeSlot slot = new TimeSlot();
        slot.setTimeslotId(1);
        slot.setSlotName("First lesson");
        return slot;
    }

    @Test
    void addTimeSlot_shouldSaveTimeSlot_whenValid() {
        TimeSlot slot = getTimeSlotEntity();
        when(timeSlotRepository.save(any(TimeSlot.class))).thenReturn(slot);

        TimeSlot result = timeSlotService.addTimeSlot(slot);

        assertEquals("First lesson", result.getSlotName());
        assertEquals(1, result.getTimeslotId());
        verify(timeSlotRepository).save(any(TimeSlot.class));
    }

    @Test
    void deleteTimeSlot_shouldThrowException_whenTimeSlotNotExists() {
        when(timeSlotRepository.existsById(99)).thenReturn(false);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> timeSlotService.deleteTimeSlot(99));

        assertEquals("Timeslot with id 99 does not exist.", e.getMessage());
    }

    @Test
    void deleteTimeSlot_shouldDelete_whenTimeSlotExists() {
        when(timeSlotRepository.existsById(1)).thenReturn(true);

        timeSlotService.deleteTimeSlot(1);

        verify(timeSlotRepository).deleteById(1);
    }
}
