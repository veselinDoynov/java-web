package web.services;

import web.entity.web.Course;
import web.repositories.web.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public Collection<Course> list() {

        return courseRepository.findAll();
    }

    public Course findCourseById(Integer id) {

        Optional<Course> result = courseRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }

        return null;
    }

    public Course findCourseByTitle(String title) {
        return courseRepository.findByTitle(title);
    }

    public Course saveCourse(Course course) {
        return courseRepository.saveAndFlush(course);
    }

    public String deleteCourse(Integer id) {
        courseRepository.deleteById(id);
        return "Success";
    }
}
