package org.dynamik.observer;

import org.dynamik.model.AbstractEntity;
import org.dynamik.model.Payment;

public interface IObserver {
    public void update(AbstractEntity entity);
}
