package org.dynamik.stratregy;

import org.dynamik.enums.AuctionFilterCriteria;
import org.dynamik.model.Auction;
import org.dynamik.model.AuctionFilterDTO;
import org.dynamik.service.AuctionService;
import org.dynamik.service.ItemService;

import java.util.List;

public class PriceStratergy implements ISearchAuctionStratergy {
    private AuctionService auctionService = new AuctionService();

    @Override
    public boolean isApplicable(AuctionFilterCriteria criteria) {
        return criteria.equals(AuctionFilterCriteria.PRICE);
    }

    @Override
    public List<Auction> getAuctions(AuctionFilterDTO auctionFilterDTO) {
        return auctionService.findAuctionInPriceRange(auctionFilterDTO.getMinPrice(), auctionFilterDTO.getMaxPrice());
    }
}
