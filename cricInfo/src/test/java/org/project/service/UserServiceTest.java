package org.project.service;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.project.model.User;

public class UserServiceTest {

    private UserService userService;

    @BeforeMethod
    public void setUp() {
        userService = new UserService();
    }

    @Test
    public void testCreate() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setName("Test User");
        
        User created = userService.create(user);
        Assert.assertNotNull(created);
        Assert.assertNotNull(created.getId());
        Assert.assertEquals("test@example.com", created.getEmail());
        Assert.assertEquals("Test User", created.getName());
    }

    @Test
    public void testGetById() {
        User user = new User();
        user.setEmail("getbyid@example.com");
        user.setName("Get By ID User");
        User created = userService.create(user);
        
        User retrieved = userService.getById(created.getId());
        Assert.assertNotNull(retrieved);
        Assert.assertEquals(created.getId(), retrieved.getId());
        Assert.assertEquals("getbyid@example.com", retrieved.getEmail());
    }

    @Test
    public void testGetAll() {
        User user1 = new User();
        user1.setEmail("user1@example.com");
        user1.setName("User 1");
        userService.create(user1);

        User user2 = new User();
        user2.setEmail("user2@example.com");
        user2.setName("User 2");
        userService.create(user2);

        var allUsers = userService.getAll();
        Assert.assertNotNull(allUsers);
        Assert.assertTrue(allUsers.size() >= 2);
    }

    @Test
    public void testUpdate() {
        User user = new User();
        user.setEmail("update@example.com");
        user.setName("Original Name");
        User created = userService.create(user);
        
        created.setName("Updated Name");
        User updated = userService.update(created.getId(), created);
        
        Assert.assertNotNull(updated);
        Assert.assertEquals("Updated Name", updated.getName());
    }

    @Test
    public void testDelete() {
        User user = new User();
        user.setEmail("delete@example.com");
        user.setName("Delete User");
        User created = userService.create(user);
        
        boolean deleted = userService.delete(created.getId());
        Assert.assertTrue(deleted);
        
        User retrieved = userService.getById(created.getId());
        Assert.assertNull(retrieved);
    }
}
