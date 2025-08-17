package org.dynamik.observer;

import org.dynamik.model.Message;

public interface IObserver {
    void update(Message message);
}
