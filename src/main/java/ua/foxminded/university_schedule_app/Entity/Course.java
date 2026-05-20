package ua.foxminded.university_schedule_app.Entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Integer courseId;
    
    @Column(name = "course_name")
    private String courseName;
    
    @Column(name = "course_description")
    private String courseDescription;
    
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
    
    @ManyToMany
    @JoinTable(
            name = "courses_departments",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "department_id")
        )
    private List<Department> departments;
   
    @OneToMany(mappedBy = "course")
    private List<GradeRecord> gradeRecords;
    
    @OneToMany(mappedBy = "course")
    private List<Lesson> lessons;

    
    public Course() {};
    
    public Course(String courseName) {
        this.courseName = courseName;
    };
    
    public Course(Integer courseId, String courseName, String courseDescription) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseDescription = courseDescription;
    }
    
    public Course(String courseName, String courseDescription) {
        this.courseName = courseName;
        this.courseDescription = courseDescription;
    }
    
    public Course(String courseName, String courseDescription, Teacher teacher, List<Department> departments) {
        this.courseName = courseName;
        this.courseDescription = courseDescription;
        this.teacher = teacher;
        this.departments = departments;
    }
    
    //getters
    public Integer getCourseId() { 
        return courseId; 
        }
    
    public String getCourseName() { 
        return courseName; 
        }
    
    public String getCourseDescription() { 
        return courseDescription; 
        }
    
    public Teacher getTeacher() { 
        return teacher; 
        }
    
    public List<Department> getDepartments() { 
        return departments; 
        }
    
    public List<GradeRecord> getGradeRecords() {    
        return gradeRecords;
    }
    
    public List<Lesson> getLessons() {    
        return lessons;
    }
    
    //setters
    public void setCourseId(Integer courseId) { 
        this.courseId = courseId; 
        }
    
    public void setCourseName(String courseName) { 
        this.courseName = courseName; 
        }
    
    public void setCourseDescription(String courseDescription) { 
        this.courseDescription = courseDescription; 
        }
    
    public void setTeacher(Teacher teacher) { 
        this.teacher = teacher; 
        }
    
    public void setDepartments(List<Department> departments) {
        this.departments = departments; 
        }
    
    public void setGradeRecords(List<GradeRecord> gradeRecords) {    
        this.gradeRecords = gradeRecords;
    }
    
    public void setLessons(List<Lesson> lessons) {    
        this.lessons = lessons;
    }
    
}
