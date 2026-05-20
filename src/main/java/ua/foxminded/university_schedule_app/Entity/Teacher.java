package ua.foxminded.university_schedule_app.Entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "teachers")
@PrimaryKeyJoinColumn(name = "teacher_id")
public class Teacher extends User{
    
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    
    @OneToMany(mappedBy = "teacher")
    private List<Course> courses;
    
    @OneToMany(mappedBy = "teacher")
    private List<Lesson> lessons;
    
    @OneToMany(mappedBy = "teacher")
    private List<GradeRecord> gradeRecords;
    
    public Teacher() {
        super();
    }
    
    public Teacher(String firstName, String lastName) {
        super(firstName, lastName);
    }
    
    public Teacher(int userId, String firstName, String lastName) {
        super(userId, firstName, lastName);
    }
    
    //getters
    public Department getDepartment() {
        return department; 
        }
    
    public List<Course> getCourses() {
        return courses; 
        }
    
    public List<Lesson> getLessons() {
        return lessons; 
        }
    
    public List<GradeRecord> getGradeRecords() {
        return gradeRecords; 
        }
    
    
    //setters
    public void setDepartment(Department department) {
        this.department = department;
    }
    
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
    
    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }
    
    public void setGradeRecords(List<GradeRecord> gradeRecords) {
        this.gradeRecords = gradeRecords;
    }
    
}
