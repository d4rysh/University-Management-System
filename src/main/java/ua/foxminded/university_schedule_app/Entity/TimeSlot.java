package ua.foxminded.university_schedule_app.Entity;

import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "timeslots")
public class TimeSlot {
    
    @Id
    @Column(name = "slot_name")
    private String slotName;
    
    @Column(name = "start_time")
    private LocalTime startTime;
    
    @Column(name = "end_time")
    private LocalTime endTime;
    
    @OneToMany(mappedBy = "timeSlot")
    private List<Lesson> lessons;
    
    public TimeSlot() {};
    
    public TimeSlot(String slotName, LocalTime startTime, LocalTime endTime) {
        this.slotName = slotName;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    
    //getters
    public String getSlotName() {
        return slotName; 
        }
    
    public LocalTime getStartTime() { 
        return startTime;
        }
    
    public LocalTime getEndTime() { 
        return endTime; 
        }
    
    public List<Lesson> getLessons() { 
        return lessons; 
        }
    
    //setters
    
    public void setSlotName(String slotName) {
        this.slotName = slotName; 
        }
    
    public void setStartTime(LocalTime startTime) { 
        this.startTime = startTime;
        }
    
    public void setEndTime(LocalTime endTime) { 
        this.endTime = endTime; 
        }
    
    public void setLessons(List<Lesson> lessons) { 
        this.lessons = lessons; 
        }
}
