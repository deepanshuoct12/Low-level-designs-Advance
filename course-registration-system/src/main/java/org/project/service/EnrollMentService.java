package org.project.service;

import org.project.model.EnrollMent;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class EnrollMentService {
    private static  ConcurrentHashMap<String, EnrollMent> enrollMents = new ConcurrentHashMap<>();


    public void update(EnrollMent enrollMent) {
        enrollMents.put(enrollMent.getId(), enrollMent);
    }

    public EnrollMent getByUserIdAndCourseId(String userId, String courseId) {
        EnrollMent enrollMent = enrollMents.values().stream().filter(e -> e.getCourseId().equals(courseId) && e.getUsers().contains(userId)).findFirst().orElse(null);
        return enrollMent;
    }

    public List<EnrollMent> getAll() {
        return enrollMents.values().stream().toList();
    }
}
