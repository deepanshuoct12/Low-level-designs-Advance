package org.dynamik.service;

import org.dynamik.dao.AuctionDao;
import org.dynamik.enums.AuctionFilterCriteria;
import org.dynamik.enums.AuctionState;
import org.dynamik.model.Auction;
import org.dynamik.model.AuctionFilterDTO;

import java.util.Date;
import java.util.List;

public class AuctionService {
    private AuctionDao auctionDao;

    public AuctionService() {
        auctionDao = new AuctionDao();
    }

    public void createAuction(Auction auction) {
        auctionDao.save(auction);
    }

    public void updateAuction(Auction auction) {
        auctionDao.update(auction);
    }

    public void cancelAuction(Auction auction) {
        auctionDao.delete(auction);
    }

    public void cancelAuctionById(String id) {
        auctionDao.deleteById(id);
    }

    public Auction findAuctionById(String id) {
        return auctionDao.findById(id);
    }

    public List<Auction> findAllAuctions() {
        return auctionDao.findAll();
    }

    public List<Auction> getAuctions(AuctionFilterDTO auctionFilterDTO, AuctionFilterCriteria auctionFilterCriteria) {
        // Implementation will depend on the strategy pattern being used for filtering
        // This is a placeholder implementation that returns all auctions
        return findAllAuctions();
    }

    public Auction findAuctionsByItemId(String itemId) {
        return auctionDao.findAuctionByItemId(itemId);
    }

    public List<Auction> findAuctionInPriceRange(Long minPrice, Long maxPrice) {
        return findAllAuctions().stream()
                .filter(auction -> auction.getStartingPrice() >= minPrice && auction.getStartingPrice() <= maxPrice)
                .toList();
    }

    public List<Auction> findAuctionBeforeEndDate(Date endDate) {
        return findAllAuctions().stream()
                .filter(auction -> auction.getEndTime().before(endDate))
                .toList();
    }

    public List<Auction> findActiveAuctions() {
        return findAllAuctions().stream()
                .filter(auction -> auction.getAuctionState().equals(AuctionState.ACTIVE))
                .toList();
    }
}
