package org.project.service;

import org.project.model.Content;
import org.project.enums.SearchType;
import org.project.model.WatchProgress;

import java.util.List;

public interface IOTTService {
    List<Content> searchContent(String query, SearchType searchType);
    WatchProgress startWatching(Long userId, Long contentId);
    WatchProgress resumeWatching(Long userId, Long contentId);
    WatchProgress updateProgress(Long watchProgressId, Long positionSeconds);
    WatchProgress stopWatching(Long watchProgressId);
    List<WatchProgress> getContinueWatching(Long userId);
}
