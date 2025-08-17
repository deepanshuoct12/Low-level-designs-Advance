package org.dynamik.model;

import lombok.Data;
import org.dynamik.observer.Subject;

@Data
public class Stock extends AbstractEntity implements Subject {
    private String name;
    private Long price;
}
