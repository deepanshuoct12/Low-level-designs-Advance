package org.project.stratergy;

import org.project.enums.SearchTerm;
import org.project.model.Course;
import org.project.service.CourseService;

import java.util.List;

import static org.project.enums.SearchTerm.NAME;

public class NameSearchStratergy implements ISearchStratergy {
    private CourseService courseService = new CourseService();

    @Override
    public boolean isApplicable(SearchTerm searchTerm) {
        return NAME.equals(searchTerm);
    }

    @Override
    public Course getCourse(String query) {
        return courseService.getAll().stream().filter(course -> course.getName().equals(query)).findFirst().orElse(null);
    }
}
