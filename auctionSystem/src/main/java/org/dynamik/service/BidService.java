package org.dynamik.service;

import org.dynamik.dao.BidDao;
import org.dynamik.model.Bid;

import java.util.List;

public class BidService {
    private BidDao bidDao;

    public BidService() {
        bidDao = new BidDao();
    }

    public void placeBid(Bid bid) {
        bidDao.save(bid);
    }

    public void updateBid(Bid bid) {
        bidDao.update(bid);
    }

    public void deleteBid(Bid bid) {
        bidDao.delete(bid);
    }

    public void deleteBidById(String id) {
        bidDao.deleteById(id);
    }

    public Bid findBidById(String id) {
        return bidDao.findById(id);
    }

    public List<Bid> findAllBids() {
        return bidDao.findAll();
    }

    public List<Bid> findBidsByAuctionId(String auctionId) {
        return bidDao.findBidsByAuctionId(auctionId);
    }
}
