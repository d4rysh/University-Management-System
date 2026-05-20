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
@Table(name = "grade_records")
public class GradeRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grade_id")
    private Integer gradeId;
    
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
    
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
    
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
    
    @Column(name = "grade")
    private Double grade;
    
    @Column(name = "exam_date")
    private LocalDate examDate;
    
    
    public GradeRecord() {};
    
    public GradeRecord(Student student, Course course, Teacher teacher, Double grade, LocalDate examDate) {
        this.student = student;
        this.course = course;
        this.teacher = teacher;
        this.grade = grade;
        this.examDate = examDate;
    }
   
   //getters
   public Integer getGradeId() {
       return gradeId;
   }
   
   public Student getStudent() {
       return student;
   }
   
   public Course getCourse() {
       return course;
   }
   
   public Teacher getTeacher() {
       return teacher;
   }
   
   public Double getGrade() {
       return grade;
   }
   
   public LocalDate getExamDate() {
       return examDate;
   }
   
   //setters
   public void setGradeId(Integer gradeId) {
       this.gradeId = gradeId;
   }
   
   public void setStudent(Student student) {
       this.student = student;
   }
   
   public void setCourse(Course course) {
       this.course = course;
   }
   
   public void setTeacher(Teacher teacher) {
       this.teacher = teacher;
   }
   
   public void setGrade(Double grade) {
       this.grade = grade;
   }
   
   public void setExamDate(LocalDate examDate) {
       this.examDate = examDate;
   }
    
}
