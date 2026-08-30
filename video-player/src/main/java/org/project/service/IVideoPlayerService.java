package org.project.service;

import org.project.enums.FilterContentTerm;
import org.project.model.Content;
import org.project.model.Watch;

import java.util.List;

public interface IVideoPlayerService {
    List<Content> getFeed(Long userId);

    List<Content> filterContent(FilterContentTerm filterContentTerm);

    Watch watchContent(Long userId, Long contentId);

    Watch updateProgress(Long userId, Long contentId, Long watchedDuration, Long lastWatchedPosition);

    Watch resumeContent(Long userId, Long contentId);
}
