package org.project.strategy;

import org.project.enums.MatchType;
import org.project.model.Match;

import java.util.List;
import java.util.stream.Collectors;

public class MatchByTypeSearchStrategy implements MatchSearchStrategy {
    @Override
    public List<Match> search(List<Match> matches, String criteria) {
        MatchType type = MatchType.valueOf(criteria.toUpperCase());
        return matches.stream()
                .filter(match -> match.getMatchType() == type)
                .collect(Collectors.toList());
    }
}
