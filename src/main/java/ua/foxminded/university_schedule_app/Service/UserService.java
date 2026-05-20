package ua.foxminded.university_schedule_app.Service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.foxminded.university_schedule_app.Entity.User;
import ua.foxminded.university_schedule_app.JPARepositories.UserRepository;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    
    @Autowired 
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Transactional
    public User addUser(User user) {
        logger.info("Starting addUser for {} {}.", user.getFirstName(), user.getLastName());
        User savedUser = userRepository.save(user);
        logger.info("User added successfully with id: {}", savedUser.getUserId());
        return savedUser;
    }
    
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        logger.info("Starting getAllUsers method.");
        List<User> users = userRepository.findAll();
            logger.debug("Retrieved {} users from database.", users.size());
        return users;
    }
    
    @Transactional
    public void deleteUser(Integer userId) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("User with id " + userId + " does not exist.");
        }
        logger.info("Deleting user with id: {}", userId);
        userRepository.deleteById(userId);
    }
    
    @Transactional(readOnly = true)
    public Optional<User> getUserByEmail(String email) {
        logger.info("Starting getUserByEmail method for user with email {}.", email);
        return userRepository.findByEmail(email);
    }
    
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        logger.info("Starting existsByEmail method for user with email: {}.", email);
        return userRepository.existsByEmail(email);
    }
    
    @Transactional(readOnly = true)
    public List<User> getUserByFirstNameAndLastNames(String firstName, String lastName) {
        logger.info("Starting getUserByFirstNameAndLastNames method for {} {}.", firstName, lastName);
        return userRepository.findByFirstNameAndLastName(firstName, lastName);
    }
}