package ua.foxminded.university_schedule_app.JPARepositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.foxminded.university_schedule_app.Entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    List<User> findByFirstNameAndLastName(String firstName, String lastName);
}
