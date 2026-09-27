package org.project.demo;

import org.project.enums.SearchTerm;
import org.project.model.Course;
import org.project.model.EnrollMent;
import org.project.model.User;
import org.project.service.CourseRegistrationSystem;
import org.project.service.CourseService;
import org.project.service.EnrollMentService;
import org.project.service.UserService;

import java.util.Arrays;
import java.util.List;

public class Driver {

    CourseRegistrationSystem courseRegistrationSystem = CourseRegistrationSystem.getInstance();
    private UserService userService = new UserService();
    private CourseService courseService = new CourseService();
    private EnrollMentService enrollMentService = new EnrollMentService();


    public void test() {
       List<User> users = initusers();
       List<Course> courses = initcourses();
        userService.addAll(users);
        courseService.addAll(courses);

        courseRegistrationSystem.enroll(users.get(0).getId(), courses.get(0).getId());
        courseRegistrationSystem.enroll(users.get(1).getId(), courses.get(1).getId());
        courseRegistrationSystem.enroll(users.get(2).getId(), courses.get(2).getId());

        System.out.println("ENROLL");
        List<EnrollMent> enrollMents = enrollMentService.getAll();
        for (EnrollMent enrollMent : enrollMents) {
            System.out.println(enrollMent.getCourseId() +  " : ");
            List<String> usersList = enrollMent.getUsers().stream().toList();
            System.out.println(usersList);
        }


        System.out.println("POST UNENROLL");
        courseRegistrationSystem.unEnroll(users.get(0).getId(), courses.get(0).getId());

         enrollMents = enrollMentService.getAll();
        for (EnrollMent enrollMent : enrollMents) {
            System.out.println(enrollMent.getCourseId() +  " : ");
            List<String> usersList = enrollMent.getUsers().stream().toList();
            System.out.println(usersList);
        }

        Course courses1 = courseRegistrationSystem.searchCourse(SearchTerm.CODE, "abc");

        System.out.println("course :" + courses1.getName() + " : " + courses1.getCode());

        Course courses2 = courseRegistrationSystem.searchCourse(SearchTerm.NAME, "eng");
        System.out.println("course :" + courses2.getName() + " : " + courses2.getCode());



    }

    private List<Course> initcourses() {
        Course course1 = new Course("maths",  50L, 20l, "abc");
        course1.setId("c1");

        Course course2 = new Course("eng",  50L, 20l, "def");
        course2.setId("c2");

        Course course3 = new Course("science",  50L, 20l, "ghi");
        course3.setId("c3");

        return  Arrays.asList(course1, course2, course3);
    }

    private List<User> initusers() {
        User user1 = new User("a", 20, "xyz.com");
        user1.setId("u1");

        User user2 = new User("b", 21, "feg.com");
        user2.setId("u2");

        User user3 = new User("c", 22, "jki.com");
        user3.setId("u3");

        return  Arrays.asList(user1, user2, user3);
    }
}
