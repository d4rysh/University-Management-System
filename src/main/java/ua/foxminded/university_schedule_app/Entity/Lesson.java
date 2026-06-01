package ua.foxminded.university_schedule_app.Entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "lessons")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lesson_id")
    private Integer lessonId;
    
    @Column (name = "date")
    private LocalDate date;
    
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
    
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
    
    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;
    
    @ManyToOne
    @JoinColumn(name = "slot_name")
    private TimeSlot timeSlot;
    
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;
    
    public Lesson() {}; 
    
    public Lesson(LocalDate date, Course course, Teacher teacher, Classroom classroom, TimeSlot timeSlot, Group group) {
        this.date = date;
        this.course = course;
        this.teacher = teacher;
        this.classroom = classroom;
        this.timeSlot = timeSlot;
        this.group = group;
    }
    
    //getters
    public Integer getLessonId() {
        return lessonId;
    }
    
    public LocalDate getLessonDate() {
        return date;
    }
    
    public Course getCourse() {
        return course;
    }
    
    public Teacher getTeacher() {
        return teacher;
    }
    
    public Classroom getClassroom() {
        return classroom;
    }
    
    public TimeSlot getTimeSlot() {
        return timeSlot;
    }
    
    public Group getGroup() {
        return group;
    }
    
    //setters
    public void setLessonId(Integer lessonId) {
        this.lessonId = lessonId;
    }
    
    public void setLessonDate(LocalDate date) {
        this.date = date;
    }
    
    public void setCourse(Course course) {
        this.course = course;
    }
    
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
    
    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }
    
    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }
    
    public void setGroup(Group group) {
        this.group = group;
    }
    
}
