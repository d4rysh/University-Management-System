package ua.foxminded.university_schedule_app.Entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "departments")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    private Integer departmentId;
    
    @Column(name = "department_name")
    private String departmentName;
    
    @ManyToMany(mappedBy = "departments")
    private List<Course> courses;
    
    @OneToMany(mappedBy = "department")
    private List<Teacher> teachers;
    
    public Department() {};
    
    public Department(Integer departmentId, String departmentName) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }
    
    public Department(String departmentName) {
        this.departmentName = departmentName;
    }
    
    public Department(String departmentName, List<Course> courses) {
        this.departmentName = departmentName;
        this.courses = courses;
    }
    
    //getters
    public Integer getDepartmentId() {
        return departmentId;
    }
    
    public String getDepartmentName() {
        return departmentName;
    }
    
    public List<Course> getCourses() {
        return courses;
    }
    
    public List<Teacher> getTeachers() {
        return teachers;
    }
    
    //setters
    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }
    
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
    
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
    
    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }
}
