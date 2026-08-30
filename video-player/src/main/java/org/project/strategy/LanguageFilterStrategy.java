package org.project.strategy;

import org.project.enums.FilterContentTerm;
import org.project.model.Content;
import org.project.service.ContentService;

import java.util.List;
import java.util.stream.Collectors;

public class LanguageFilterStrategy implements IContentFilterStrategy {
    private final ContentService contentService;
    private final String language;

    public LanguageFilterStrategy(ContentService contentService, String language) {
        this.contentService = contentService;
        this.language = language;
    }

    @Override
    public List<Content> getContents(FilterContentTerm filterContentTerm) {
        return contentService.getAll().stream()
                .filter(content -> content.getLanguage() != null && content.getLanguage().equalsIgnoreCase(language))
                .collect(Collectors.toList());
    }
}
