package ua.foxminded.university_schedule_app.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
@Entity
@Table(name = "admins")
@PrimaryKeyJoinColumn(name = "admin_id")
public class Admin extends User{

    public Admin() {
        super();
    }
    
    public Admin(String firstName, String lastName) {
        super(firstName, lastName);
    }
    
    public Admin(int userId, String firstName, String lastName) {
        super(userId, firstName, lastName);
    }
}
