package org.dynamik.stratergy;

import org.dynamik.enums.Category;
import org.dynamik.model.Item;

import java.util.List;


public interface IFilterStratergy {
    Boolean isApplicable(Category category);
    List<Item> getItems(Category category);
}
