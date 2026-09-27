package org.project.service;

import org.project.enums.SearchTerm;
import org.project.model.Course;

import java.util.List;

public interface ICourseRegistrationSystem {
  void enroll(String userId, String courseId);
  void unEnroll(String userId, String courseId);
  Course searchCourse(SearchTerm searchTerm, String query);
}
