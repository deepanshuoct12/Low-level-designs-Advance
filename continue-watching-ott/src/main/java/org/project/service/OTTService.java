package org.project.service;

import org.project.enums.SearchType;
import org.project.enums.WatchStatus;
import org.project.exception.InvalidInputException;
import org.project.model.Content;
import org.project.model.WatchProgress;
import org.project.strategy.GenreSearchStrategy;
import org.project.strategy.SearchStrategy;
import org.project.strategy.TitleSearchStrategy;
import org.project.validator.WatchProgressValidator;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class OTTService implements IOTTService {
    private static volatile OTTService instance;

    private final ContentService contentService;
    private final WatchProgressService watchProgressService;

    private OTTService() {
        this.contentService = new ContentService();
        this.watchProgressService = new WatchProgressService();
    }

    public static OTTService getInstance() {
        if (instance == null) {
            synchronized (OTTService.class) {
                if (instance == null) {
                    instance = new OTTService();
                }
            }
        }
        return instance;
    }

    @Override
    public List<Content> searchContent(String query, SearchType searchType) {
        SearchStrategy searchStrategy;
        if (searchType == SearchType.GENRE) {
            searchStrategy = new GenreSearchStrategy(contentService);
        } else if (searchType == SearchType.TITLE) {
            searchStrategy = new TitleSearchStrategy(contentService);
        } else {
            throw new InvalidInputException("Unsupported search type: " + searchType);
        }
        return searchStrategy.search(query);
    }

    @Override
    public WatchProgress startWatching(Long userId, Long contentId) {
        long now = System.currentTimeMillis();

        WatchProgress watchProgress = new WatchProgress();
        watchProgress.setUserId(userId);
        watchProgress.setContentId(contentId);
        watchProgress.setPositionSeconds(0L);
        watchProgress.setStatus(WatchStatus.IN_PROGRESS);
        watchProgress.setStartTimestamp(now);
        watchProgress.setLastTimestamp(now);

        return watchProgressService.create(watchProgress);
    }

    @Override
    public WatchProgress resumeWatching(Long userId, Long contentId) {
        WatchProgress existing = watchProgressService.getContinueWatching(userId).stream()
                .filter(wp -> wp.getContentId().equals(contentId))
                .max(Comparator.comparing(WatchProgress::getId))
                .orElse(null);

        if (existing == null) {
            return startWatching(userId, contentId);
        }

        existing.setStatus(WatchStatus.IN_PROGRESS);
        existing.setLastTimestamp(System.currentTimeMillis());

        return watchProgressService.update(existing.getId(), existing);
    }

    @Override
    public WatchProgress updateProgress(Long watchProgressId, Long positionSeconds) {
        WatchProgress watchProgress = watchProgressService.getById(watchProgressId);
        WatchProgressValidator.validateExists(watchProgress);
        WatchProgressValidator.validatePositionUpdate(positionSeconds);

        long newPosition = watchProgress.getPositionSeconds() + positionSeconds;
        watchProgress.setPositionSeconds(newPosition);
        watchProgress.setLastTimestamp(System.currentTimeMillis());

        Content content = contentService.getById(watchProgress.getContentId());
        if (content != null && content.getDuration() != null && newPosition >= content.getDuration()) {
            watchProgress.setStatus(WatchStatus.COMPLETED);
        } else {
            watchProgress.setStatus(WatchStatus.IN_PROGRESS);
        }

        return watchProgressService.update(watchProgressId, watchProgress);
    }

    @Override
    public WatchProgress stopWatching(Long watchProgressId) {
        WatchProgress watchProgress = watchProgressService.getById(watchProgressId);
        WatchProgressValidator.validateExists(watchProgress);

        watchProgress.setStopTimestamp(System.currentTimeMillis());

        return watchProgressService.update(watchProgressId, watchProgress);
    }

    @Override
    public List<WatchProgress> getContinueWatching(Long userId) {
        return watchProgressService.getContinueWatching(userId).stream()
                .filter(wp -> wp.getStatus() == WatchStatus.IN_PROGRESS)
                .collect(Collectors.toList());
    }
}
