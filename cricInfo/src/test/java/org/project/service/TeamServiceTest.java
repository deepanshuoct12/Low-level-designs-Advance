package org.project.service;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.project.model.Team;

public class TeamServiceTest {

    private TeamService teamService;

    @BeforeMethod
    public void setUp() {
        teamService = new TeamService();
    }

    @Test
    public void testCreate() {
        Team team = new Team();
        team.setName("Test Team");
        team.setCountry("Test Country");
        team.setCaptain("Test Captain");
        
        Team created = teamService.create(team);
        Assert.assertNotNull(created);
        Assert.assertNotNull(created.getId());
        Assert.assertEquals("Test Team", created.getName());
        Assert.assertEquals("Test Country", created.getCountry());
        Assert.assertEquals("Test Captain", created.getCaptain());
    }

    @Test
    public void testGetById() {
        Team team = new Team();
        team.setName("Get By ID Team");
        team.setCountry("Test Country");
        Team created = teamService.create(team);
        
        Team retrieved = teamService.getById(created.getId());
        Assert.assertNotNull(retrieved);
        Assert.assertEquals(created.getId(), retrieved.getId());
        Assert.assertEquals("Get By ID Team", retrieved.getName());
    }

    @Test
    public void testGetAll() {
        Team team1 = new Team();
        team1.setName("Team 1");
        team1.setCountry("Country 1");
        teamService.create(team1);

        Team team2 = new Team();
        team2.setName("Team 2");
        team2.setCountry("Country 2");
        teamService.create(team2);

        var allTeams = teamService.getAll();
        Assert.assertNotNull(allTeams);
        Assert.assertTrue(allTeams.size() >= 2);
    }

    @Test
    public void testUpdate() {
        Team team = new Team();
        team.setName("Original Team");
        team.setCountry("Test Country");
        Team created = teamService.create(team);
        
        created.setName("Updated Team");
        created.setCaptain("New Captain");
        Team updated = teamService.update(created.getId(), created);
        
        Assert.assertNotNull(updated);
        Assert.assertEquals("Updated Team", updated.getName());
        Assert.assertEquals("New Captain", updated.getCaptain());
    }

    @Test
    public void testDelete() {
        Team team = new Team();
        team.setName("Delete Team");
        team.setCountry("Test Country");
        Team created = teamService.create(team);
        
        boolean deleted = teamService.delete(created.getId());
        Assert.assertTrue(deleted);
        
        Team retrieved = teamService.getById(created.getId());
        Assert.assertNull(retrieved);
    }
}
