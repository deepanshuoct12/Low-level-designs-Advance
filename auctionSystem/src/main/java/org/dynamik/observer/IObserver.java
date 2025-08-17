package org.dynamik.observer;

import org.dynamik.model.Bid;

public interface IObserver {
    void update(Bid bid);
}
