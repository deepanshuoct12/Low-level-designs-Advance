package org.project.service;

import org.project.enums.MatchSearchTerm;
import org.project.enums.MatchState;
import org.project.enums.PlayerSearchTerm;
import org.project.model.*;
import org.project.strategy.MatchByStateSearchStrategy;
import org.project.strategy.MatchByTermSearchStrategy;
import org.project.strategy.MatchByTypeSearchStrategy;
import org.project.strategy.MatchSearchStrategy;
import org.project.strategy.PlayerByTeamSearchStrategy;
import org.project.strategy.PlayerByTypeSearchStrategy;
import org.project.strategy.PlayerSearchStrategy;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.project.enums.MatchSearchTerm.MATCH_TYPE;
import static org.project.enums.MatchSearchTerm.STATE;
import static org.project.enums.MatchSearchTerm.TERM;
import static org.project.enums.PlayerSearchTerm.PLAYER_TYPE;
import static org.project.enums.PlayerSearchTerm.TEAM;


public class CricInfoService implements ICricInfoService {
    private static CricInfoService instance;
    
    private final MatchService matchService;
    private final LeaderboardService leaderboardService;
    private final TeamService teamService;
    private final PlayerService playerService;
    private final CurrentScoreService currentScoreService;
    private final StatisticsService statisticsService;
    private final MatchScheduleService matchScheduleService;
    private final MatchSummaryService matchSummaryService;

    private final Map<MatchSearchTerm, MatchSearchStrategy> matchSearchStrategies;
    private final Map<PlayerSearchTerm, PlayerSearchStrategy> playerSearchStrategies;

    private CricInfoService() {
        this.matchService = new MatchService();
        this.leaderboardService = new LeaderboardService();
        this.teamService = new TeamService();
        this.playerService = new PlayerService();
        this.currentScoreService = new CurrentScoreService();
        this.statisticsService = new StatisticsService();
        this.matchScheduleService = new MatchScheduleService();
        this.matchSummaryService = new MatchSummaryService();

        this.matchSearchStrategies = Map.of(
            STATE, new MatchByStateSearchStrategy(),
            MATCH_TYPE, new MatchByTypeSearchStrategy(),
            TERM, new MatchByTermSearchStrategy()
        );

        this.playerSearchStrategies = Map.of(
                TEAM, new PlayerByTeamSearchStrategy(),
            PLAYER_TYPE, new PlayerByTypeSearchStrategy()
        );
    }

    public static CricInfoService getInstance() {
        if (instance == null) {
            instance = new CricInfoService();
        }
        return instance;
    }

    @Override
    public List<Match> getMatchesBySearchTerm(MatchSearchTerm searchTerm, String criteria) {
        List<Match> allMatches = matchService.getAll();
        MatchSearchStrategy strategy = matchSearchStrategies.get(searchTerm);
        return strategy.search(allMatches, criteria);
    }

    @Override
    public Leaderboard getLeaderboardByTournament(Long tournamentId) {
        return leaderboardService.getAll().stream()
                .filter(lb -> lb.getTournamentId().equals(tournamentId))
                .findFirst()
                .orElse(null);
    }


    @Override
    public List<Team> getTeamsByCountry(String country) {
        return teamService.getAll().stream()
                .filter(team -> team.getCountry().equalsIgnoreCase(country))
                .collect(Collectors.toList());
    }

    @Override
    public List<Player> getPlayersBySearchTerm(PlayerSearchTerm searchTerm, String criteria) {
        List<Player> allPlayers = playerService.getAll();
        PlayerSearchStrategy strategy = playerSearchStrategies.get(searchTerm);
        return strategy.search(allPlayers, criteria);
    }

    @Override
    public CurrentScore getCurrentScore(Long matchId) {
        return currentScoreService.getAll().stream()
                .filter(score -> score.getMatchId().equals(matchId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public BatsmanStats getBatsmanStats(Long playerId) {
        return statisticsService.getAll().stream()
                .filter(stats -> stats.getId().equals(playerId))
                .filter(stats -> stats instanceof BatsmanStats)
                .map(stats -> (BatsmanStats) stats)
                .findFirst()
                .orElse(null);
    }

    @Override
    public BowlerStats getBowlerStats(Long playerId) {
        return statisticsService.getAll().stream()
                .filter(stats -> stats.getId().equals(playerId))
                .filter(stats -> stats instanceof BowlerStats)
                .map(stats -> (BowlerStats) stats)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<MatchSchedule> getMatchScheduleByDateRange(LocalDateTime start, LocalDateTime end) {
        return matchScheduleService.getAll().stream()
                .filter(schedule -> !schedule.getStartDateTime().isBefore(start) && 
                                   !schedule.getStartDateTime().isAfter(end))
                .collect(Collectors.toList());
    }

    @Override
    public MatchSchedule getMatchScheduleByMatchId(Long matchId) {
        return matchScheduleService.getAll().stream()
                .filter(schedule -> schedule.getMatchId().equals(matchId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public MatchSummary getMatchSummary(Long matchId) {
        return matchSummaryService.getAll().stream()
                .filter(summary -> summary.getMatchId().equals(matchId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean updateMatchState(Long matchId, MatchState newState) {
        Match match = matchService.getById(matchId);
        if (match != null) {
            match.setState(newState);
            matchService.update(matchId, match);
            return true;
        }
        return false;
    }
}
