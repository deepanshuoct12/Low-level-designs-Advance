package org.project.strategy;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.project.enums.PlayerType;
import org.project.model.Player;

import java.util.Arrays;
import java.util.List;

public class PlayerByTeamSearchStrategyTest {

    private PlayerByTeamSearchStrategy strategy;

    @BeforeMethod
    public void setUp() {
        strategy = new PlayerByTeamSearchStrategy();
    }

    @Test
    public void testSearchByTeam() {
        Player player1 = new Player();
        player1.setId(1L);
        player1.setName("Player 1");
        player1.setTeamId(1L);
        player1.setPlayerType(PlayerType.BATSMAN);

        Player player2 = new Player();
        player2.setId(2L);
        player2.setName("Player 2");
        player2.setTeamId(2L);
        player2.setPlayerType(PlayerType.BOWLER);

        Player player3 = new Player();
        player3.setId(3L);
        player3.setName("Player 3");
        player3.setTeamId(1L);
        player3.setPlayerType(PlayerType.ALL_ROUNDER);

        List<Player> players = Arrays.asList(player1, player2, player3);
        
        List<Player> team1Players = strategy.search(players, "1");
        Assert.assertEquals(2, team1Players.size());
        Assert.assertTrue(team1Players.stream().allMatch(p -> p.getTeamId() == 1L));

        List<Player> team2Players = strategy.search(players, "2");
        Assert.assertEquals(1, team2Players.size());
        Assert.assertEquals(2L, team2Players.get(0).getTeamId());
    }

    @Test
    public void testSearchEmptyList() {
        List<Player> players = List.of();
        List<Player> result = strategy.search(players, "1");
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void testSearchNoMatches() {
        Player player1 = new Player();
        player1.setTeamId(2L);

        Player player2 = new Player();
        player2.setTeamId(3L);

        List<Player> players = Arrays.asList(player1, player2);
        
        List<Player> result = strategy.search(players, "1");
        Assert.assertTrue(result.isEmpty());
    }
}
