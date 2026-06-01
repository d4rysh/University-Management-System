package ua.foxminded.university_schedule_app.Service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.foxminded.university_schedule_app.Entity.TimeSlot;
import ua.foxminded.university_schedule_app.JPARepositories.TimeSlotRepository;

@Service
public class TimeSlotService {
    private static final Logger logger = LoggerFactory.getLogger(TimeSlotService.class);
    private final TimeSlotRepository timeSlotRepository;
    
    @Autowired 
    public TimeSlotService(TimeSlotRepository timeSlotRepository) {
        this.timeSlotRepository = timeSlotRepository;
    }
    
    @Transactional
    public TimeSlot addTimeSlot(TimeSlot timeSlot) {
        logger.info("Starting addTimeSlot for slot: {}.", timeSlot.getSlotName());
        TimeSlot savedTimeSlot = timeSlotRepository.save(timeSlot);
        logger.info("TimeSlot added successfully with slot name: {}", savedTimeSlot.getSlotName());
        return savedTimeSlot;
    }
    
    @Transactional(readOnly = true)
    public List<TimeSlot> getAllTimeSlots() {
        logger.info("Starting getAllTimeSlots method.");
        List<TimeSlot> timeslots = timeSlotRepository.findAll();
        logger.debug("Retrieved {} timeslots from database.", timeslots.size());
        return timeslots;
}
    
    @Transactional
    public void deleteTimeSlot(String slotName) {
        if (!timeSlotRepository.existsById(slotName)) {
            throw new IllegalArgumentException("Timeslot with name" + slotName + " does not exist.");
        }
        logger.info("Deleting timeslot with name: {}", slotName);
        timeSlotRepository.deleteById(slotName);
    }
    
    @Transactional(readOnly = true)
    public Optional<TimeSlot> getTimeSlotBySlotName(String slotName) {
        logger.info("Starting getTimeSlotBySlotName method for slotName {}.", slotName);
        return timeSlotRepository.findBySlotName(slotName);
    }
}