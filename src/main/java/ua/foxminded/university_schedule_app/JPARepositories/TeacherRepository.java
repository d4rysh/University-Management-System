package ua.foxminded.university_schedule_app.JPARepositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ua.foxminded.university_schedule_app.Entity.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {

    boolean existsByEmail(String email);
    
    List<Teacher> findByDepartment_DepartmentId(Integer departmentId);
    
    @Query("""
            SELECT DISTINCT t
            FROM Teacher t
            JOIN t.courses c
            WHERE c.courseName = :courseName
            """)
    List<Teacher> findTeachersByCourseName(@Param("courseName")String courseName);

    @Query("""
            SELECT DISTINCT t
            FROM Teacher t
            JOIN t.lessons l
            WHERE l.group.groupId = :groupId
            """)
    List<Teacher> findTeachersByGroupId(@Param("groupId")Integer groupId);
    
  //looking for teachers by LastName (for admin's fast search)
    List<Teacher> findByLastName(String lastName);
    
}
