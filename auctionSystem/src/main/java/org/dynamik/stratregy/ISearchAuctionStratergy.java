package org.dynamik.stratregy;

import org.dynamik.enums.AuctionFilterCriteria;
import org.dynamik.model.Auction;
import org.dynamik.model.AuctionFilterDTO;

import java.util.List;

public interface ISearchAuctionStratergy {
    boolean isApplicable(AuctionFilterCriteria criteria);
    List<Auction> getAuctions(AuctionFilterDTO auctionFilterDTO);
}
