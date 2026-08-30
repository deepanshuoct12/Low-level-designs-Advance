package org.project.strategy;

import org.project.enums.FilterContentTerm;
import org.project.model.Content;

import java.util.List;

public interface IContentFilterStrategy {
    List<Content> getContents(FilterContentTerm filterContentTerm);
}
