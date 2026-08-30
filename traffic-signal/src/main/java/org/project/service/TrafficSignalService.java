package org.project.service;

import org.project.constants.ErrorMessages;
import org.project.enums.Color;
import org.project.enums.Direction;
import org.project.exception.InvalidOperationException;
import org.project.exception.ResourceNotFoundException;
import org.project.model.Intersection;
import org.project.model.Light;
import org.project.model.Signal;
import org.project.model.Timer;
import org.project.observer.Subject;
import org.project.validator.TrafficSignalValidator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TrafficSignalService extends Subject implements ITrafficSignalService {

    private static final long DEFAULT_DURATION_SECONDS = 5L;

    private static volatile TrafficSignalService instance;

    private final IntersectionService intersectionService = new IntersectionService();
    private final SignalService signalService = new SignalService();
    private final TimerService timerService = new TimerService();

    private final Map<String, ScheduledExecutorService> runningIntersections = new ConcurrentHashMap<>();

    private TrafficSignalService() {
    }

    public static TrafficSignalService getInstance() {
        if (instance == null) {
            synchronized (TrafficSignalService.class) {
                if (instance == null) {
                    instance = new TrafficSignalService();
                }
            }
        }
        return instance;
    }

    @Override
    public void configureTimer(String signalId, Color color, long duration) {
        TrafficSignalValidator.validateSignalId(signalId);
        TrafficSignalValidator.validateColor(color);
        TrafficSignalValidator.validateDuration(duration);

        Timer timer = findTimer(signalId, color);
        if (timer == null) {
            timer = new Timer();
            timer.setSignalId(signalId);
            timer.setColor(color);
            timer.setDuration(duration);
            timerService.create(timer);
        } else {
            timer.setDuration(duration);
            timerService.update(timer.getId(), timer);
        }
    }

    @Override
    public void startIntersection(String intersectionId) {
        TrafficSignalValidator.validateIntersectionId(intersectionId);
        if (runningIntersections.containsKey(intersectionId)) {
            throw new InvalidOperationException(String.format(ErrorMessages.INTERSECTION_ALREADY_RUNNING, intersectionId));
        }
        Intersection intersection = getIntersectionOrThrow(intersectionId);
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(intersection.getSignals().size());
        runningIntersections.put(intersectionId, executor);
        intersection.getSignals().forEach((direction, signal) ->
                scheduleNextTransition(executor, intersectionId, direction, signal));
    }

    @Override
    public void stopIntersection(String intersectionId) {
        TrafficSignalValidator.validateIntersectionId(intersectionId);
        ScheduledExecutorService executor = runningIntersections.remove(intersectionId);
        if (executor == null) {
            throw new InvalidOperationException(String.format(ErrorMessages.INTERSECTION_NOT_RUNNING, intersectionId));
        }
        executor.shutdownNow();
    }

    @Override
    public Color getCurrentSignalState(String intersectionId, Direction direction) {
        Signal signal = getSignalOrThrow(intersectionId, direction);
        return signal.getCurrentLight() == null ? null : signal.getCurrentLight().getColor();
    }

    @Override
    public Map<Direction, Color> getAllSignalStates(String intersectionId) {
        TrafficSignalValidator.validateIntersectionId(intersectionId);
        Intersection intersection = getIntersectionOrThrow(intersectionId);
        Map<Direction, Color> states = new ConcurrentHashMap<>();
        intersection.getSignals().forEach((direction, signal) ->
                states.put(direction, signal.getCurrentLight() == null ? null : signal.getCurrentLight().getColor()));
        return states;
    }

    @Override
    public void forceSignalColor(String intersectionId, Direction direction, Color color) {
        TrafficSignalValidator.validateColor(color);
        Signal signal = getSignalOrThrow(intersectionId, direction);
        applyColor(signal, color);
        notifyObservers(intersectionId, direction, color);
    }

    private void scheduleNextTransition(ScheduledExecutorService executor, String intersectionId,
                                         Direction direction, Signal signal) {
        Color current = signal.getCurrentLight() == null ? Color.RED : signal.getCurrentLight().getColor();
        Color nextColor = nextColor(current);

        applyColor(signal, nextColor);
        notifyObservers(intersectionId, direction, nextColor);

        long delay = getDuration(signal.getId(), nextColor);
        executor.schedule(() -> scheduleNextTransition(executor, intersectionId, direction, signal),
                delay, TimeUnit.SECONDS);
    }

    private Color nextColor(Color current) {
        return switch (current) {
            case RED -> Color.GREEN;
            case GREEN -> Color.YELLOW;
            case YELLOW -> Color.RED;
        };
    }

    private void applyColor(Signal signal, Color color) {
        Light light = new Light();
        light.setColor(color);
        light.setOn(true);
        signal.setCurrentLight(light);
        signalService.update(signal.getId(), signal);
    }

    private long getDuration(String signalId, Color color) {
        Timer timer = findTimer(signalId, color);
        return timer == null ? DEFAULT_DURATION_SECONDS : timer.getDuration();
    }

    private Timer findTimer(String signalId, Color color) {
        return timerService.getAll().stream()
                .filter(timer -> timer.getSignalId().equals(signalId) && timer.getColor() == color)
                .findFirst()
                .orElse(null);
    }

    private Signal getSignalOrThrow(String intersectionId, Direction direction) {
        TrafficSignalValidator.validateIntersectionId(intersectionId);
        TrafficSignalValidator.validateDirection(direction);
        Intersection intersection = getIntersectionOrThrow(intersectionId);
        Signal signal = intersection.getSignals().get(direction);
        if (signal == null) {
            throw new ResourceNotFoundException(
                    String.format(ErrorMessages.SIGNAL_NOT_FOUND, intersectionId, direction));
        }
        return signal;
    }

    private Intersection getIntersectionOrThrow(String intersectionId) {
        Intersection intersection = intersectionService.getById(intersectionId);
        if (intersection == null) {
            throw new ResourceNotFoundException(String.format(ErrorMessages.INTERSECTION_NOT_FOUND, intersectionId));
        }
        return intersection;
    }

}
