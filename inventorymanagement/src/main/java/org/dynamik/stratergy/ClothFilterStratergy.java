package org.dynamik.stratergy;

import org.dynamik.enums.Category;
import org.dynamik.model.Item;
import org.dynamik.service.ItemService;

import java.util.List;

import static org.dynamik.enums.Category.CLOTHES;

public class ClothFilterStratergy implements IFilterStratergy{
    private ItemService itemService = new ItemService();

    @Override
    public Boolean isApplicable(Category category) {
        return CLOTHES.equals(category);
    }

    @Override
    public List<Item> getItems(Category category) {
        return itemService.getItemsByCategory(category);
    }
}
