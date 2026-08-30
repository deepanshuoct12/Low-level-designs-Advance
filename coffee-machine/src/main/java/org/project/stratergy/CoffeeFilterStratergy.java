package org.project.stratergy;

import org.project.dto.FilterRequest;
import org.project.model.Coffee;

import java.util.List;

public interface CoffeeFilterStratergy {
     List<Coffee> filter(FilterRequest filterRequest);
}
