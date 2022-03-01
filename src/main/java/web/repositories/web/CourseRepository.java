package web.repositories.web;

import web.entity.web.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Integer>  {

    @Query(value = "SELECT CONCAT(course.id, ' - ', course.title) FROM course " +
            "Join course_student on course_student.course_id = course.id " +
            "Where course_student.student_id=:studentId", nativeQuery = true)
    List<Object> getStudentCourses(@Param("studentId") Integer studentId);

    @Query(value="Select course.* From course Where title=:title", nativeQuery = true)
    Course findByTitle(@Param("title") String title);

}
