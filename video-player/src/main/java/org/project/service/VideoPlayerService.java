package org.project.service;

import org.project.constants.ExceptionMessages;
import org.project.enums.FilterContentTerm;
import org.project.enums.Genre;
import org.project.enums.WatchState;
import org.project.exception.ContentNotFoundException;
import org.project.exception.UnsupportedFilterTermException;
import org.project.exception.ValidationException;
import org.project.exception.WatchNotFoundException;
import org.project.model.Content;
import org.project.model.Feed;
import org.project.model.Watch;
import org.project.strategy.GenreFilterStrategy;
import org.project.strategy.IContentFilterStrategy;
import org.project.strategy.LanguageFilterStrategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class VideoPlayerService implements IVideoPlayerService {
    private static VideoPlayerService instance;
    private final FeedService feedService;
    private final ContentService contentService;
    private final WatchService watchService;
    private final Map<FilterContentTerm, IContentFilterStrategy> strategyMap;

    private VideoPlayerService() {
        this.feedService = new FeedService();
        this.contentService = new ContentService();
        this.watchService = new WatchService();
        this.strategyMap = new HashMap<>();
        strategyMap.put(FilterContentTerm.GENRE, new GenreFilterStrategy(contentService, Genre.ACTION));
        strategyMap.put(FilterContentTerm.LANGUAGE, new LanguageFilterStrategy(contentService, "en"));
    }

    public static VideoPlayerService getInstance() {
        if (instance == null) {
            synchronized (VideoPlayerService.class) {
                if (instance == null) {
                    instance = new VideoPlayerService();
                }
            }

        }

        return instance;
    }

    @Override
    public List<Content> getFeed(Long userId) {
        if (userId == null) {
            throw new ValidationException(ExceptionMessages.USER_ID_REQUIRED);
        }

        if (userId <= 0) {
            throw new ValidationException(ExceptionMessages.INVALID_USER_ID);
        }

        List<Feed> feeds = feedService.getFeedByUserId(userId);

        return feeds.stream()
                .map(feed -> contentService.getById(feed.getContentId()))
                .filter(content -> content != null)
                .collect(Collectors.toList());
    }

    @Override
    public List<Content> filterContent(FilterContentTerm filterContentTerm) {
        IContentFilterStrategy strategy = strategyMap.get(filterContentTerm);

        if (strategy == null) {
            throw new UnsupportedFilterTermException(String.format(ExceptionMessages.UNSUPPORTED_FILTER_TERM, filterContentTerm));
        }

        return strategy.getContents(filterContentTerm);
    }

    @Override
    public Watch watchContent(Long userId, Long contentId) {
        if (userId == null) {
            throw new ValidationException(ExceptionMessages.USER_ID_REQUIRED);
        }

        if (contentId == null) {
            throw new ValidationException(ExceptionMessages.CONTENT_ID_REQUIRED);
        }

        Content content = contentService.getById(contentId);
        if (content == null) {
            throw new ContentNotFoundException(String.format(ExceptionMessages.CONTENT_NOT_FOUND, contentId));
        }

        Watch watch = new Watch();
        watch.setUserId(userId);
        watch.setContentId(contentId);
        watch.setWatchedDuration(0L);
        watch.setLastWatchedPosition(0L);
        watch.setState(WatchState.INIT);
        watch.onCreate();

        return watchService.create(watch);
    }

    @Override
    public Watch updateProgress(Long userId, Long contentId, Long watchedDuration, Long lastWatchedPosition) {
        if (userId == null) {
            throw new ValidationException(ExceptionMessages.USER_ID_REQUIRED);
        }
        if (contentId == null) {
            throw new ValidationException(ExceptionMessages.CONTENT_ID_REQUIRED);
        }

        if (watchedDuration == null) {
            throw new ValidationException(ExceptionMessages.WATCHED_DURATION_REQUIRED);
        }

        if (lastWatchedPosition == null) {
            throw new ValidationException(ExceptionMessages.LAST_WATCHED_POSITION_REQUIRED);
        }

        Watch watch = watchService.getByUserIdAndContentId(userId, contentId);
        if (watch == null) {
            throw new WatchNotFoundException(String.format(ExceptionMessages.WATCH_NOT_FOUND, userId, contentId));
        }
        
        Content content = contentService.getById(contentId);
        if (content == null) {
            throw new ContentNotFoundException(String.format(ExceptionMessages.CONTENT_NOT_FOUND, contentId));
        }
        
        watch.setWatchedDuration(watchedDuration);
        watch.setLastWatchedPosition(lastWatchedPosition);
        
        if (content.getDuration() != null && watchedDuration >= content.getDuration()) {
            watch.setState(WatchState.COMPLETED);
        } else {
            watch.setState(WatchState.IN_PROGRESS);
        }
        watch.onUpdate();

        return watchService.update(watch.getId(), watch);
    }

    @Override
    public Watch resumeContent(Long userId, Long contentId) {
        if (userId == null) {
            throw new ValidationException(ExceptionMessages.USER_ID_REQUIRED);
        }

        if (contentId == null) {
            throw new ValidationException(ExceptionMessages.CONTENT_ID_REQUIRED);
        }

        Watch watch = watchService.getByUserIdAndContentId(userId, contentId);
        if (watch == null) {
            throw new WatchNotFoundException(String.format(ExceptionMessages.WATCH_NOT_FOUND, userId, contentId));
        }
        return watch;
    }
}
