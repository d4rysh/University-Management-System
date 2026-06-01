package ua.foxminded.university_schedule_app.JPARepositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.foxminded.university_schedule_app.Entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
    
    boolean existsByEmail(String email);
    
    List<Admin> findByLastNameStartingWith(String lastName);
}
