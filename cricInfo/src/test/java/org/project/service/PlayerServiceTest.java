package org.project.service;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.project.enums.PlayerType;
import org.project.model.Player;

public class PlayerServiceTest {

    private PlayerService playerService;

    @BeforeMethod
    public void setUp() {
        playerService = new PlayerService();
    }

    @Test
    public void testCreate() {
        Player player = new Player();
        player.setName("Test Player");
        player.setTeamId(1L);
        player.setPlayerType(PlayerType.BATSMAN);
        
        Player created = playerService.create(player);
        Assert.assertNotNull(created);
        Assert.assertNotNull(created.getId());
        Assert.assertEquals("Test Player", created.getName());
        Assert.assertEquals(1L, created.getTeamId());
        Assert.assertEquals(PlayerType.BATSMAN, created.getPlayerType());
    }

    @Test
    public void testGetById() {
        Player player = new Player();
        player.setName("Get By ID Player");
        player.setTeamId(1L);
        player.setPlayerType(PlayerType.BOWLER);
        Player created = playerService.create(player);
        
        Player retrieved = playerService.getById(created.getId());
        Assert.assertNotNull(retrieved);
        Assert.assertEquals(created.getId(), retrieved.getId());
        Assert.assertEquals("Get By ID Player", retrieved.getName());
    }

    @Test
    public void testGetAll() {
        Player player1 = new Player();
        player1.setName("Player 1");
        player1.setTeamId(1L);
        player1.setPlayerType(PlayerType.BATSMAN);
        playerService.create(player1);

        Player player2 = new Player();
        player2.setName("Player 2");
        player2.setTeamId(2L);
        player2.setPlayerType(PlayerType.BOWLER);
        playerService.create(player2);

        var allPlayers = playerService.getAll();
        Assert.assertNotNull(allPlayers);
        Assert.assertTrue(allPlayers.size() >= 2);
    }

    @Test
    public void testUpdate() {
        Player player = new Player();
        player.setName("Original Player");
        player.setTeamId(1L);
        player.setPlayerType(PlayerType.BATSMAN);
        Player created = playerService.create(player);
        
        created.setName("Updated Player");
        created.setPlayerType(PlayerType.ALL_ROUNDER);
        Player updated = playerService.update(created.getId(), created);
        
        Assert.assertNotNull(updated);
        Assert.assertEquals("Updated Player", updated.getName());
        Assert.assertEquals(PlayerType.ALL_ROUNDER, updated.getPlayerType());
    }

    @Test
    public void testDelete() {
        Player player = new Player();
        player.setName("Delete Player");
        player.setTeamId(1L);
        player.setPlayerType(PlayerType.BATSMAN);
        Player created = playerService.create(player);
        
        boolean deleted = playerService.delete(created.getId());
        Assert.assertTrue(deleted);
        
        Player retrieved = playerService.getById(created.getId());
        Assert.assertNull(retrieved);
    }
}
