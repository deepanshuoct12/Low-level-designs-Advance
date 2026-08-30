package org.project.service;

import org.project.enums.MatchSearchTerm;
import org.project.enums.MatchState;
import org.project.enums.PlayerSearchTerm;
import org.project.model.*;

import java.time.LocalDateTime;
import java.util.List;

public interface ICricInfoService {
    List<Match> getMatchesBySearchTerm(MatchSearchTerm searchTerm, String criteria);
    Leaderboard getLeaderboardByTournament(Long tournamentId);
    List<Team> getTeamsByCountry(String country);
    List<Player> getPlayersBySearchTerm(PlayerSearchTerm searchTerm, String criteria);
    CurrentScore getCurrentScore(Long matchId);
    BatsmanStats getBatsmanStats(Long playerId);
    BowlerStats getBowlerStats(Long playerId);
    List<MatchSchedule> getMatchScheduleByDateRange(LocalDateTime start, LocalDateTime end);
    MatchSchedule getMatchScheduleByMatchId(Long matchId);
    MatchSummary getMatchSummary(Long matchId);
    boolean updateMatchState(Long matchId, MatchState newState);
}
