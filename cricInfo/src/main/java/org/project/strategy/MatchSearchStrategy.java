package org.project.strategy;

import org.project.model.Match;

import java.util.List;

public interface MatchSearchStrategy {
    List<Match> search(List<Match> matches, String criteria);
}
