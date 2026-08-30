package org.project.strategy;

import org.project.model.Match;

import java.util.List;
import java.util.stream.Collectors;

public class MatchByTermSearchStrategy implements MatchSearchStrategy {
    @Override
    public List<Match> search(List<Match> matches, String criteria) {
        String searchTerm = criteria.toLowerCase();
        return matches.stream()
                .filter(match -> match.getName().toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());
    }
}
