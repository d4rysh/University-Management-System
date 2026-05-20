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
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Integer groupId;
    
    @Column(name = "group_name")
    private String groupName;
    
    @OneToMany(mappedBy = "group")
    private List<Student> students;
    
    @OneToMany(mappedBy = "group")
    private List<Lesson> lessons;
    
    public Group() {};
    
    public Group(Integer groupId, String groupName) {
        this.groupId = groupId;
        this.groupName = groupName;
    }
    
    public Group(String groupName) {
        this.groupName = groupName;
    }
    
    public Group(Integer groupId, String groupName, List<Student> students, List<Lesson> lessons) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.students = students;
        this.lessons = lessons;
    }
    
    //getters
    public Integer getGroupId() {
        return groupId; 
    }
    
    public String getGroupName() {
        return groupName; 
    }
    
    public List<Student> getStudents() {
        return students; 
    }
    
    public List<Lesson> getLessons() {
        return lessons; 
    }
    
    //setters
    public void setGroupId(Integer groupId) {
        this.groupId = groupId; 
    }
    
    public void setGroupName(String groupName) {
        this.groupName = groupName; 
    }
    
    public void setStudents(List<Student> students) {
        this.students = students; 
    }
    
    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons; 
    }
}
