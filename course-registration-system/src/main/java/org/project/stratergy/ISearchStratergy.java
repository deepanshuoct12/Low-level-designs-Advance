package org.project.stratergy;

import org.project.enums.SearchTerm;
import org.project.model.Course;

import java.util.List;

public interface ISearchStratergy {
    boolean isApplicable(SearchTerm searchTerm);
    Course getCourse(String query);
}
