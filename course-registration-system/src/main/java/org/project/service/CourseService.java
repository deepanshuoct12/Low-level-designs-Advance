package org.project.service;

import org.project.model.Course;
import org.project.model.User;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class CourseService {
    private static final ConcurrentHashMap<String, Course> courses = new ConcurrentHashMap<>();

    public Course get(String courseId) {
        return courses.get(courseId);
    }

    public void add(Course course){
        courses.put(course.getId(), course);
    }

    public void addAll(List<Course> courses) {
        for (Course course:courses) {
            add(course);
        }
    }

    public List<Course> getAll(){
        return courses.values().stream().toList();
    }
}
