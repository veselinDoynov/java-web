package web.repositories.web;

import web.entity.web.Course;
import web.entity.web.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import javax.transaction.Transactional;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer>  {

    @Query(value = "SELECT course.* FROM course ", nativeQuery = true)
    List<Course> getStudentCourses(@Param("studentId") Integer studentId);

    @Transactional
    @Modifying
    @Query(
            value =
                    "insert ignore into course_student (course_id, student_id) values (:courseId, :studentId)",
            nativeQuery = true)
    void attachCourseToStudent(@Param("courseId") Integer courseId, @Param("studentId") Integer studentId);

    @Transactional
    @Modifying
    @Query(
            value =
                    "delete from course_student where student_id =:studentId and course_id=:courseId ",
            nativeQuery = true)
    void detachCourseFromStudent(@Param("courseId") Integer courseId, @Param("studentId") Integer studentId);
}
