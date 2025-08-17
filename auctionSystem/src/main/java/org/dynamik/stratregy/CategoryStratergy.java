package org.dynamik.stratregy;

import org.dynamik.enums.AuctionFilterCriteria;
import org.dynamik.model.Auction;
import org.dynamik.model.AuctionFilterDTO;
import org.dynamik.model.Item;
import org.dynamik.service.AuctionService;
import org.dynamik.service.ItemService;

import java.util.List;

public class CategoryStratergy implements ISearchAuctionStratergy {
    private ItemService itemService = new ItemService();
    private AuctionService auctionService = new AuctionService();

    @Override
    public boolean isApplicable(AuctionFilterCriteria criteria) {
        return criteria.equals(AuctionFilterCriteria.ITEM_CATEGORY);
    }

    @Override
    public List<Auction> getAuctions(AuctionFilterDTO auctionFilterDTO) {
        List<Item> items = itemService.findItemsByCategory(auctionFilterDTO.getItemCategory());
        List<String> itemIds = items.stream().map(Item::getId).toList();
        return itemIds.stream().map(auctionService::findAuctionsByItemId).toList();
    }
}
