package org.project.model;

import org.project.enums.Color;
import org.project.enums.Direction;
import org.project.observer.IObserver;

public class DisplayScreen extends BaseEntity implements IObserver {

    @Override
    public void onSignalChange(String intersectionId, Direction direction, Color color) {
        System.out.println("Display[" + getId() + "] Intersection " + intersectionId
                + " -> " + direction + " is now " + color);
    }
}
