package ua.foxminded.university_schedule_app.Entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
@Entity
@Table(name = "classrooms")
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "classroom_id")
    private Integer classroomId;
    
    @Column(name = "room_number")
    private int roomNumber;
    
    @OneToMany(mappedBy = "classroom")
    private List<Lesson> lessons;
    
    public Classroom() {};
    
    public Classroom(int roomNumber) {
        this.roomNumber = roomNumber;
    }
    
    public Classroom(Integer classroomId, int roomNumber) {
        this.classroomId = classroomId;
        this.roomNumber = roomNumber;
    }
    
    //getters
    public Integer getClassroomId() {
        return classroomId;
    }
    
    public int getRoomNumber() {
        return roomNumber;
    }
    
    public List<Lesson> getLessons() {
        return lessons;
    }
    
    //setters
    public void setClassroomId(Integer classroomId) {
        this.classroomId = classroomId;
    }
    
    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }
    
    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }
    
}
