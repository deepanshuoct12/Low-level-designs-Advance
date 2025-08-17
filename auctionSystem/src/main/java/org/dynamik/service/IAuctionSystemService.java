package org.dynamik.service;

import org.dynamik.enums.AuctionFilterCriteria;
import org.dynamik.model.Auction;
import org.dynamik.model.AuctionFilterDTO;
import org.dynamik.model.User;

import java.util.List;

public interface IAuctionSystemService {
    void registerItemForAuction(String itemId, String userId, String auctionId);
    List<Auction> getAuctions(AuctionFilterDTO auctionFilterDTO, AuctionFilterCriteria auctionFilterCriteria);
    void placeBid(Long amount, String userId, String auctionId); // notify observers whenever higjhest bid is placed
    User getWinner(String auctionId);
     void cancelAuction(String auctionId);
     List<Auction> finActiveAuctions();
}
