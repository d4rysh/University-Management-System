package ua.foxminded.university_schedule_app.JPARepositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ua.foxminded.university_schedule_app.Entity.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {
    
    Optional<Group> findByGroupId(Integer groupId);
    
    Optional<Group> findByGroupName(String groupName);
    
    boolean existsByGroupName(String groupName);
    
    boolean existsByGroupId(Integer groupId);

    Optional<Group> findByStudents_UserId(Integer studentId);
    
    @Query("""
            SELECT DISTINCT g
            FROM Group g
            JOIN g.lessons l
            WHERE l.teacher.userId = :teacherId
            """)
    List<Group> findGroupsByUserId(Integer teacherId);
    
}
