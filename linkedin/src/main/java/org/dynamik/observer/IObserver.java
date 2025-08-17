package org.dynamik.observer;

import org.dynamik.model.Notification;

public interface IObserver {
    void update(Notification notification);
}
