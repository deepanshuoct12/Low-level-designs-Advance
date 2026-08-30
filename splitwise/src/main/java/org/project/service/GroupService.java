package org.project.service;

import org.project.model.Group;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class GroupService {
    private static final ConcurrentHashMap<String, Group> GROUPS = new ConcurrentHashMap<>();

    public Group create(Group group) {
        if (group.getId() == null) {
            group.setId(UUID.randomUUID().toString());
        }
        long now = System.currentTimeMillis();
        group.setCreatedAt(now);
        group.setUpdatedAt(now);
        GROUPS.put(group.getId(), group);
        return group;
    }

    public Group getById(String id) {
        return GROUPS.get(id);
    }

    public List<Group> getAll() {
        return new ArrayList<>(GROUPS.values());
    }

    public Group update(Group group) {
        if (!GROUPS.containsKey(group.getId())) {
            return null;
        }
        group.setUpdatedAt(System.currentTimeMillis());
        GROUPS.put(group.getId(), group);
        return group;
    }

    public void delete(String id) {
        GROUPS.remove(id);
    }
}
