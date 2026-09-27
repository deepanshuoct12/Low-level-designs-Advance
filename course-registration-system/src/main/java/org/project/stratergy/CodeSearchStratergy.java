package org.project.stratergy;

import org.project.enums.SearchTerm;
import org.project.model.Course;
import org.project.service.CourseService;

import java.util.List;

import static org.project.enums.SearchTerm.CODE;

public class CodeSearchStratergy implements ISearchStratergy {
    private CourseService courseService = new CourseService();

    @Override
    public boolean isApplicable(SearchTerm searchTerm) {
        return CODE.equals(searchTerm);
    }

    @Override
    public Course getCourse(String query) {
        return courseService.getAll().stream().filter(course -> course.getCode().equals(query)).findFirst().orElse(null);
    }
}
