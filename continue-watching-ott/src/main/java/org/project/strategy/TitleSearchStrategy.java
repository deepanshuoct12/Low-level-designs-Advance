package org.project.strategy;

import org.project.model.Content;
import org.project.service.ContentService;

import java.util.List;
import java.util.stream.Collectors;

public class TitleSearchStrategy implements SearchStrategy {
    private final ContentService contentService;

    public TitleSearchStrategy(ContentService contentService) {
        this.contentService = contentService;
    }

    @Override
    public List<Content> search(String query) {
        return contentService.getAll().stream()
                .filter(content -> content.getTitle().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }
}
