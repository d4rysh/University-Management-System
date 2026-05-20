package ua.foxminded.university_schedule_app.JPARepositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ua.foxminded.university_schedule_app.Entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    @Query("""
            SELECT DISTINCT s
            FROM Student s
            JOIN s.group g
            JOIN g.lessons l
            JOIN l.course c
            WHERE c.courseName = :courseName
            """)
    List<Student> findStudentsByCourseName(@Param("courseName")String courseName);

    @Query("SELECT COUNT(s) FROM Student s WHERE s.group.groupId = :groupId")
    int countStudentsInGroup(@Param("groupId")int groupId);
  
    List<Student> findByGroup_GroupId(Integer groupId);


    boolean existsByEmail(String email);
    
    //looking for students by LastName (for admin's fast search)
    List<Student> findByLastName(String lastName);
    
}

