package org.project.strategy;

import org.project.enums.MatchState;
import org.project.model.Match;

import java.util.List;
import java.util.stream.Collectors;

public class MatchByStateSearchStrategy implements MatchSearchStrategy {
    @Override
    public List<Match> search(List<Match> matches, String criteria) {
        MatchState state = MatchState.valueOf(criteria.toUpperCase());
        return matches.stream()
                .filter(match -> match.getState() == state)
                .collect(Collectors.toList());
    }
}
