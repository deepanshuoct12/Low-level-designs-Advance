package org.project.strategy;

import org.project.model.Content;

import java.util.List;

public interface SearchStrategy {
    List<Content> search(String query);
}
