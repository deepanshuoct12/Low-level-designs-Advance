package org.project.service;

import org.project.enums.SearchTerm;
import org.project.exception.InvalidInputException;
import org.project.model.Course;
import org.project.model.EnrollMent;
import org.project.model.User;
import org.project.stratergy.CodeSearchStratergy;
import org.project.stratergy.ISearchStratergy;
import org.project.stratergy.NameSearchStratergy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseRegistrationSystem implements ICourseRegistrationSystem {
    private UserService userService;
    private CourseService courseService;
    private static CourseRegistrationSystem courseRegistrationSystem;
    private EnrollMentService        enrollMentService;
    Map<SearchTerm, ISearchStratergy> searchTermISearchStratergies;

    private CourseRegistrationSystem() {
        userService = new UserService();
        courseService = new CourseService();
        enrollMentService = new EnrollMentService();
        searchTermISearchStratergies = new HashMap<>();
        searchTermISearchStratergies.put(SearchTerm.CODE, new CodeSearchStratergy());
        searchTermISearchStratergies.put(SearchTerm.NAME, new NameSearchStratergy());
    }

    public static  CourseRegistrationSystem getInstance() {
      if (courseRegistrationSystem == null) {
           synchronized (CourseRegistrationSystem.class) {
               if (courseRegistrationSystem == null) {
                   courseRegistrationSystem = new CourseRegistrationSystem();
               }
           }
      }

      return  courseRegistrationSystem;
    }

    @Override
    public void enroll(String userId, String courseId) {
        EnrollMent enrollMent = new EnrollMent();
        User user = userService.get(userId);
        Course course = courseService.get(courseId);

        if (user == null || course == null) {
            throw new InvalidInputException();
        }

        enrollMent.setCourseId(courseId);
        enrollMent.getUsers().add(userId);
        enrollMentService.update(enrollMent);
    }

    @Override
    public void unEnroll(String userId, String courseId) {
        EnrollMent enrollMent = enrollMentService.getByUserIdAndCourseId(userId, courseId);
        User user = userService.get(userId);
        Course course = courseService.get(courseId);

        if (user == null || course == null) {
            throw new InvalidInputException();
        }

        enrollMent.getUsers().remove(userId);
        enrollMentService.update(enrollMent);
    }

    @Override
    public Course searchCourse(SearchTerm searchTerm, String query) {
        return searchTermISearchStratergies.get(searchTerm).getCourse(query);
    }
}
