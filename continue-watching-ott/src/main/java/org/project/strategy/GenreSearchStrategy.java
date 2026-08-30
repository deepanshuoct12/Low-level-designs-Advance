package org.project.strategy;

import org.project.model.Content;
import org.project.enums.Genre;
import org.project.service.ContentService;

import java.util.List;
import java.util.stream.Collectors;

public class GenreSearchStrategy implements SearchStrategy {
    private final ContentService contentService;

    public GenreSearchStrategy(ContentService contentService) {
        this.contentService = contentService;
    }

    @Override
    public List<Content> search(String query) {
        Genre genre = Genre.valueOf(query.toUpperCase());
        return contentService.getAll().stream()
                .filter(content -> content.getGenre() == genre)
                .collect(Collectors.toList());
    }
}
