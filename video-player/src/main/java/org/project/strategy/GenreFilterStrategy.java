package org.project.strategy;

import org.project.enums.FilterContentTerm;
import org.project.enums.Genre;
import org.project.model.Content;
import org.project.service.ContentService;

import java.util.List;
import java.util.stream.Collectors;

public class GenreFilterStrategy implements IContentFilterStrategy {
    private final ContentService contentService;
    private final Genre genre;

    public GenreFilterStrategy(ContentService contentService, Genre genre) {
        this.contentService = contentService;
        this.genre = genre;
    }

    @Override
    public List<Content> getContents(FilterContentTerm filterContentTerm) {
        return contentService.getAll().stream()
                .filter(content -> content.getGenre() == genre)
                .collect(Collectors.toList());
    }
}
