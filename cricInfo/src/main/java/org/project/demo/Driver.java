package org.project.demo;

import org.project.enums.MatchSearchTerm;
import org.project.enums.MatchState;
import org.project.enums.MatchType;
import org.project.enums.PlayerSearchTerm;
import org.project.enums.PlayerType;
import org.project.model.*;
import org.project.service.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Driver {
    
    public static void runDemo() {
        CricInfoService cricInfoService = CricInfoService.getInstance();

        // Demo: Create sample data
        createSampleData(cricInfoService);

        // Demo: Search matches by different criteria
        System.out.println("=== Match Search Demo ===");
        
        // Search matches by state
        List<Match> ongoingMatches = cricInfoService.getMatchesBySearchTerm(MatchSearchTerm.STATE, "ONGOING");
        System.out.println("Ongoing Matches: " + ongoingMatches.size());
        
        // Search matches by type
        List<Match> t20Matches = cricInfoService.getMatchesBySearchTerm(MatchSearchTerm.MATCH_TYPE, "T20");
        System.out.println("T20 Matches: " + t20Matches.size());
        
        // Search matches by term
        List<Match> indiaMatches = cricInfoService.getMatchesBySearchTerm(MatchSearchTerm.TERM, "India");
        System.out.println("India Matches: " + indiaMatches.size());

        // Demo: Get current score for a match
        System.out.println("\n=== Current Score Demo ===");
        CurrentScore currentScore = cricInfoService.getCurrentScore(1L);
        if (currentScore != null) {
            System.out.println("Current Score for Match 1:");
            System.out.println("Total Runs: " + currentScore.getTotalRuns());
            System.out.println("Total Wickets: " + currentScore.getTotalWickets());
            System.out.println("Overs: " + currentScore.getOvers());
            System.out.println("Current Run Rate: " + currentScore.getCurrentRunRate());
        }

        // Demo: Get match summary
        System.out.println("\n=== Match Summary Demo ===");
        MatchSummary matchSummary = cricInfoService.getMatchSummary(1L);
        if (matchSummary != null) {
            System.out.println("Match Summary for Match 1:");
            System.out.println("Winner Team ID: " + matchSummary.getWinnerTeamId());
            System.out.println("Margin: " + matchSummary.getMargin());
            System.out.println("Man of the Match ID: " + matchSummary.getManOfTheMatchId());
            System.out.println("Match Result: " + matchSummary.getMatchResult());
        }

        // Demo: Search players by team
        System.out.println("\n=== Player Search Demo ===");
        List<Player> teamPlayers = cricInfoService.getPlayersBySearchTerm(PlayerSearchTerm.TEAM, "1");
        System.out.println("Players in Team 1: " + teamPlayers.size());
        
        // Search players by type
        List<Player> bowlers = cricInfoService.getPlayersBySearchTerm(PlayerSearchTerm.PLAYER_TYPE, "BOWLER");
        System.out.println("Bowlers: " + bowlers.size());

        // Demo: Get player statistics
        System.out.println("\n=== Player Statistics Demo ===");
        BatsmanStats batsmanStats = cricInfoService.getBatsmanStats(1L);
        if (batsmanStats != null) {
            System.out.println("Batsman Stats for Player 1:");
            System.out.println("Total Runs: " + batsmanStats.getTotalRunsScored());
            System.out.println("Batting Average: " + batsmanStats.getBattingAverage());
            System.out.println("Strike Rate: " + batsmanStats.getStrikeRate());
            System.out.println("Highest Score: " + batsmanStats.getHighestScore());
        }

        BowlerStats bowlerStats = cricInfoService.getBowlerStats(2L);
        if (bowlerStats != null) {
            System.out.println("\nBowler Stats for Player 2:");
            System.out.println("Total Wickets: " + bowlerStats.getTotalWicketsTaken());
            System.out.println("Economy Rate: " + bowlerStats.getEconomyRate());
            System.out.println("Total Overs Bowled: " + bowlerStats.getTotalOversBowled());
        }

        // Demo: Get leaderboard
        System.out.println("\n=== Leaderboard Demo ===");
        Leaderboard leaderboard = cricInfoService.getLeaderboardByTournament(1L);
        if (leaderboard != null) {
            System.out.println("Leaderboard for Tournament 1:");
            System.out.println("Tournament Name: " + leaderboard.getTournamentName());
            System.out.println("Team Standings: " + leaderboard.getTeamStandings().size());
        }

        // Demo: Get teams by country
        System.out.println("\n=== Team Search Demo ===");
        List<Team> indianTeams = cricInfoService.getTeamsByCountry("India");
        System.out.println("Indian Teams: " + indianTeams.size());

        // Demo: Update match state
        System.out.println("\n=== Match State Update Demo ===");
        boolean updated = cricInfoService.updateMatchState(1L, MatchState.COMPLETED);
        System.out.println("Match state updated: " + updated);

        // Demo: Get match schedule
        System.out.println("\n=== Match Schedule Demo ===");
        List<MatchSchedule> schedules = cricInfoService.getMatchScheduleByDateRange(
            LocalDateTime.now().minusDays(7), 
            LocalDateTime.now().plusDays(7)
        );
        System.out.println("Matches scheduled in next 7 days: " + schedules.size());
    }

    private static void createSampleData(CricInfoService cricInfoService) {
        System.out.println("Creating sample data...");
        
        UserService userService = new UserService();
        TeamService teamService = new TeamService();
        PlayerService playerService = new PlayerService();
        MatchService matchService = new MatchService();
        MatchScheduleService matchScheduleService = new MatchScheduleService();
        CurrentScoreService currentScoreService = new CurrentScoreService();
        MatchSummaryService matchSummaryService = new MatchSummaryService();
        LeaderboardService leaderboardService = new LeaderboardService();
        TeamStandingService teamStandingService = new TeamStandingService();
        StatisticsService statisticsService = new StatisticsService();

        // Create 1 user
        User user = new User();
        user.setEmail("admin@cricinfo.com");
        user.setName("Ravi kishan");
        userService.create(user);
        System.out.println("Created user: " + user.getName());

        // Create 4 teams
        Team team1 = new Team();
        team1.setName("India");
        team1.setCountry("India");
        team1.setCaptain("Virat Kohli");
        teamService.create(team1);

        Team team2 = new Team();
        team2.setName("Australia");
        team2.setCountry("Australia");
        team2.setCaptain("Pat Cummins");
        teamService.create(team2);

        Team team3 = new Team();
        team3.setName("England");
        team3.setCountry("England");
        team3.setCaptain("Ben Stokes");
        teamService.create(team3);

        Team team4 = new Team();
        team4.setName("South Africa");
        team4.setCountry("South Africa");
        team4.setCaptain("Temba Bavuma");
        teamService.create(team4);
        System.out.println("Created 4 teams");

        // Create players for each team
        for (long teamId = 1; teamId <= 4; teamId++) {
            for (int i = 1; i <= 3; i++) {
                Player player = new Player();
                player.setName("Player " + teamId + "-" + i);
                player.setTeamId(teamId);
                player.setPlayerType(i % 2 == 0 ? PlayerType.BOWLER : PlayerType.BATSMAN);
                playerService.create(player);
                
                // Create statistics for each player
                if (i % 2 == 0) {
                    BowlerStats bowlerStats = new BowlerStats();
                    bowlerStats.setId(player.getId());
                    bowlerStats.setTotalWicketsTaken(10 + i);
                    bowlerStats.setEconomyRate(5.5 + i * 0.1);
                    bowlerStats.setTotalOversBowled(50 + i * 10);
                    statisticsService.create(bowlerStats);
                } else {
                    BatsmanStats batsmanStats = new BatsmanStats();
                    batsmanStats.setId(player.getId());
                    batsmanStats.setTotalRunsScored(500 + i * 100);
                    batsmanStats.setBattingAverage(45.0 + i * 5);
                    batsmanStats.setStrikeRate(130.0 + i * 10);
                    batsmanStats.setHighestScore(100 + i * 20);
                    statisticsService.create(batsmanStats);
                }
            }
        }
        System.out.println("Created players and statistics");

        // Create tournament leaderboard
        Leaderboard leaderboard = new Leaderboard();
        leaderboard.setTournamentId(1L);
        leaderboard.setTournamentName("ICC T20 World Cup");
        leaderboard.setTeamStandings(new ArrayList<>());
        leaderboardService.create(leaderboard);

        // Create team standings and add to leaderboard
        TeamStanding standing1 = new TeamStanding();
        standing1.setTeamId(1L);
        standing1.setTeamName("India");
        standing1.setRank(1);
        standing1.setMatchesPlayed(2);
        standing1.setMatchesWon(2);
        standing1.setMatchesLost(0);
        standing1.setPoints(4);
        teamStandingService.create(standing1);
        leaderboard.getTeamStandings().add(standing1);

        TeamStanding standing2 = new TeamStanding();
        standing2.setTeamId(2L);
        standing2.setTeamName("Australia");
        standing2.setRank(2);
        standing2.setMatchesPlayed(2);
        standing2.setMatchesWon(1);
        standing2.setMatchesLost(1);
        standing2.setPoints(2);
        teamStandingService.create(standing2);
        leaderboard.getTeamStandings().add(standing2);

        TeamStanding standing3 = new TeamStanding();
        standing3.setTeamId(3L);
        standing3.setTeamName("England");
        standing3.setRank(3);
        standing3.setMatchesPlayed(2);
        standing3.setMatchesWon(0);
        standing3.setMatchesLost(2);
        standing3.setPoints(0);
        teamStandingService.create(standing3);
        leaderboard.getTeamStandings().add(standing3);

        TeamStanding standing4 = new TeamStanding();
        standing4.setTeamId(4L);
        standing4.setTeamName("South Africa");
        standing4.setRank(4);
        standing4.setMatchesPlayed(2);
        standing4.setMatchesWon(0);
        standing4.setMatchesLost(2);
        standing4.setPoints(0);
        teamStandingService.create(standing4);
        leaderboard.getTeamStandings().add(standing4);

        // Update leaderboard with team standings
        leaderboardService.update(leaderboard.getId(), leaderboard);
        System.out.println("Created leaderboard and team standings");

        // Create 2 matches - India wins both
        Match match1 = new Match();
        match1.setName("India vs Australia");
        match1.setState(MatchState.COMPLETED);
        match1.setTeam1Id(1L);
        match1.setTeam2Id(2L);
        match1.setTossWinnerTeamId(1L);
        match1.setTossDecision("Bat");
        match1.setMatchType(MatchType.T20);
        matchService.create(match1);

        Match match2 = new Match();
        match2.setName("India vs England");
        match2.setState(MatchState.COMPLETED);
        match2.setTeam1Id(1L);
        match2.setTeam2Id(3L);
        match2.setTossWinnerTeamId(1L);
        match2.setTossDecision("Bowl");
        match2.setMatchType(MatchType.T20);
        matchService.create(match2);
        System.out.println("Created 2 matches");

        // Create match schedules
        MatchSchedule schedule1 = new MatchSchedule();
        schedule1.setMatchId(1L);
        schedule1.setStartDateTime(LocalDateTime.now().minusDays(1));
        schedule1.setEndDateTime(LocalDateTime.now().minusDays(1).plusHours(3));
        schedule1.setVenue("Melbourne Cricket Ground");
        schedule1.setCity("Melbourne");
        schedule1.setCountry("Australia");
        schedule1.setOvers(20);
        matchScheduleService.create(schedule1);

        MatchSchedule schedule2 = new MatchSchedule();
        schedule2.setMatchId(2L);
        schedule2.setStartDateTime(LocalDateTime.now().minusDays(2));
        schedule2.setEndDateTime(LocalDateTime.now().minusDays(2).plusHours(3));
        schedule2.setVenue("Lord's Cricket Ground");
        schedule2.setCity("London");
        schedule2.setCountry("England");
        schedule2.setOvers(20);
        matchScheduleService.create(schedule2);
        System.out.println("Created match schedules");

        // Create current scores (for demo purposes, showing final scores)
        CurrentScore score1 = new CurrentScore();
        score1.setMatchId(1L);
        score1.setBattingTeamId(1L);
        score1.setBowlingTeamId(2L);
        score1.setTotalRuns(185);
        score1.setTotalWickets(4);
        score1.setOvers(20.0);
        score1.setCurrentRunRate(9.25);
        score1.setTargetScore(186);
        currentScoreService.create(score1);

        CurrentScore score2 = new CurrentScore();
        score2.setMatchId(2L);
        score2.setBattingTeamId(1L);
        score2.setBowlingTeamId(3L);
        score2.setTotalRuns(178);
        score2.setTotalWickets(3);
        score2.setOvers(20.0);
        score2.setCurrentRunRate(8.9);
        score2.setTargetScore(179);
        currentScoreService.create(score2);
        System.out.println("Created current scores");

        // Create match summaries
        MatchSummary summary1 = new MatchSummary();
        summary1.setMatchId(1L);
        summary1.setWinnerTeamId(1L);
        summary1.setMargin(6);
        summary1.setManOfTheMatchId(1L);
        summary1.setHighestRunScorerId(1L);
        summary1.setHighestRuns(85);
        summary1.setHighestWicketTakerId(2L);
        summary1.setHighestWickets(3);
        summary1.setMatchResult("India won by 6 wickets");
        summary1.setTotalMatchDuration(3.0);
        summary1.setTossWinnerTeam("India");
        matchSummaryService.create(summary1);

        MatchSummary summary2 = new MatchSummary();
        summary2.setMatchId(2L);
        summary2.setWinnerTeamId(1L);
        summary2.setMargin(5);
        summary2.setManOfTheMatchId(4L);
        summary2.setHighestRunScorerId(4L);
        summary2.setHighestRuns(92);
        summary2.setHighestWicketTakerId(5L);
        summary2.setHighestWickets(2);
        summary2.setMatchResult("India won by 5 wickets");
        summary2.setTotalMatchDuration(3.0);
        summary2.setTossWinnerTeam("India");
        matchSummaryService.create(summary2);
        System.out.println("Created match summaries");

        System.out.println("Sample data creation completed!");
    }
}
