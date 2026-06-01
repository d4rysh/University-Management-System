package ua.foxminded.university_schedule_app.Entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "students")
@PrimaryKeyJoinColumn(name = "user_id")
public class Student extends User{
    
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;
    
    @OneToMany(mappedBy = "student")
    private List<GradeRecord> gradeRecords;
    
    public Student() {
        super();
    }
    
    public Student(String firstName, String lastName) {
        super(firstName, lastName);
    }
    
    public Student(int userId, String firstName, String lastName) {
        super(userId, firstName, lastName);
    }
    
    //getters
    public Group getGroup() {
        return group;
    }
    
    public List<GradeRecord> getGradeRecords() {
        return gradeRecords;
    }
    
    //setters
    public void setGroup(Group group) {
        this.group = group;
    }
    
    public void setGradeRecords(List<GradeRecord> gradeRecords) {
        this.gradeRecords = gradeRecords;
    }
 
}
