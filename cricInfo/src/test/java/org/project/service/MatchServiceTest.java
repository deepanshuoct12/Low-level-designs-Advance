package org.project.service;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.project.enums.MatchState;
import org.project.enums.MatchType;
import org.project.model.Match;

public class MatchServiceTest {

    private MatchService matchService;

    @BeforeMethod
    public void setUp() {
        matchService = new MatchService();
    }

    @Test
    public void testCreate() {
        Match match = new Match();
        match.setName("Test Match");
        match.setState(MatchState.ONGOING);
        match.setTeam1Id(1L);
        match.setTeam2Id(2L);
        match.setMatchType(MatchType.T20);
        
        Match created = matchService.create(match);
        Assert.assertNotNull(created);
        Assert.assertNotNull(created.getId());
        Assert.assertEquals("Test Match", created.getName());
        Assert.assertEquals(MatchState.ONGOING, created.getState());
        Assert.assertEquals(1L, created.getTeam1Id());
        Assert.assertEquals(2L, created.getTeam2Id());
        Assert.assertEquals(MatchType.T20, created.getMatchType());
    }

    @Test
    public void testGetById() {
        Match match = new Match();
        match.setName("Get By ID Match");
        match.setState(MatchState.COMPLETED);
        match.setTeam1Id(1L);
        match.setTeam2Id(2L);
        Match created = matchService.create(match);
        
        Match retrieved = matchService.getById(created.getId());
        Assert.assertNotNull(retrieved);
        Assert.assertEquals(created.getId(), retrieved.getId());
        Assert.assertEquals("Get By ID Match", retrieved.getName());
    }

    @Test
    public void testGetAll() {
        Match match1 = new Match();
        match1.setName("Match 1");
        match1.setState(MatchState.ONGOING);
        match1.setTeam1Id(1L);
        match1.setTeam2Id(2L);
        matchService.create(match1);

        Match match2 = new Match();
        match2.setName("Match 2");
        match2.setState(MatchState.COMPLETED);
        match2.setTeam1Id(3L);
        match2.setTeam2Id(4L);
        matchService.create(match2);

        var allMatches = matchService.getAll();
        Assert.assertNotNull(allMatches);
        Assert.assertTrue(allMatches.size() >= 2);
    }

    @Test
    public void testUpdate() {
        Match match = new Match();
        match.setName("Original Match");
        match.setState(MatchState.ONGOING);
        match.setTeam1Id(1L);
        match.setTeam2Id(2L);
        Match created = matchService.create(match);
        
        created.setState(MatchState.COMPLETED);
        created.setName("Updated Match");
        Match updated = matchService.update(created.getId(), created);
        
        Assert.assertNotNull(updated);
        Assert.assertEquals("Updated Match", updated.getName());
        Assert.assertEquals(MatchState.COMPLETED, updated.getState());
    }

    @Test
    public void testDelete() {
        Match match = new Match();
        match.setName("Delete Match");
        match.setState(MatchState.ONGOING);
        match.setTeam1Id(1L);
        match.setTeam2Id(2L);
        Match created = matchService.create(match);
        
        boolean deleted = matchService.delete(created.getId());
        Assert.assertTrue(deleted);
        
        Match retrieved = matchService.getById(created.getId());
        Assert.assertNull(retrieved);
    }
}
