package org.project.strategy;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.project.enums.MatchState;
import org.project.model.Match;

import java.util.Arrays;
import java.util.List;

public class MatchByStateSearchStrategyTest {

    private MatchByStateSearchStrategy strategy;

    @BeforeMethod
    public void setUp() {
        strategy = new MatchByStateSearchStrategy();
    }

    @Test
    public void testSearchByState() {
        Match match1 = new Match();
        match1.setId(1L);
        match1.setName("Match 1");
        match1.setState(MatchState.ONGOING);

        Match match2 = new Match();
        match2.setId(2L);
        match2.setName("Match 2");
        match2.setState(MatchState.COMPLETED);

        Match match3 = new Match();
        match3.setId(3L);
        match3.setName("Match 3");
        match3.setState(MatchState.ONGOING);

        List<Match> matches = Arrays.asList(match1, match2, match3);
        
        List<Match> ongoingMatches = strategy.search(matches, "ONGOING");
        Assert.assertEquals(2, ongoingMatches.size());
        Assert.assertTrue(ongoingMatches.stream().allMatch(m -> m.getState() == MatchState.ONGOING));

        List<Match> completedMatches = strategy.search(matches, "COMPLETED");
        Assert.assertEquals(1, completedMatches.size());
        Assert.assertEquals(MatchState.COMPLETED, completedMatches.get(0).getState());
    }

    @Test
    public void testSearchEmptyList() {
        List<Match> matches = List.of();
        List<Match> result = strategy.search(matches, "ONGOING");
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void testSearchNoMatches() {
        Match match1 = new Match();
        match1.setState(MatchState.COMPLETED);

        Match match2 = new Match();
        match2.setState(MatchState.NOT_STARTED);

        List<Match> matches = Arrays.asList(match1, match2);
        
        List<Match> result = strategy.search(matches, "ONGOING");
        Assert.assertTrue(result.isEmpty());
    }
}
