package ua.foxminded.university_schedule_app.Entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;
    
    @Column(name = "first_name")
    private String firstName;
    
    @Column(name = "last_name")
    private String lastName;
    
    @Column(name = "password")
    private String password;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;
    
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    
    @Column(name = "email")
    private String email;
    
    public User() {}
    
    
    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    public User(Integer userId, String firstName, String lastName) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    //getters
    public Integer getUserId() { 
        return userId; 
        }
    
    public String getFirstName() { 
        return firstName; 
        }
    
    public String getLastName() {
        return lastName; 
        }
    
    public String getPassword() {
        return password; 
        }
    
    public Gender getGender() {
        return gender; 
        }
    
    public LocalDate getDateOfBirth() {
        return dateOfBirth; 
        }
    
    public String getEmail() {
        return email; 
        }
    
    //setters
    public void setUserId(Integer userId) {
        this.userId = userId; 
        }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName; 
        }
    
    public void setLastName(String lastName) {
        this.lastName = lastName; 
        }
    
    public void setPassword(String password) {
        this.password = password; 
        }
    
    public void setEmail(String email) {
        this.email = email; 
        }
}
