package org.project.observer;

import org.project.enums.Color;
import org.project.enums.Direction;

public interface IObserver {
    void onSignalChange(String intersectionId, Direction direction, Color color);
}
