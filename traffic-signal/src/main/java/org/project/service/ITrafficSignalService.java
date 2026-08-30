package org.project.service;

import org.project.enums.Color;
import org.project.enums.Direction;

import java.util.Map;

public interface ITrafficSignalService {

    void configureTimer(String signalId, Color color, long duration);

    void startIntersection(String intersectionId);

    void stopIntersection(String intersectionId);

    Color getCurrentSignalState(String intersectionId, Direction direction);

    Map<Direction, Color> getAllSignalStates(String intersectionId);

    void forceSignalColor(String intersectionId, Direction direction, Color color);
}
