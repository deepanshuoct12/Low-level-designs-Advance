package org.dynamik.dao;

import org.dynamik.model.Bid;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BidDao implements IBaseDao<Bid, String> {
    private static final Map<String, Bid> bids = new ConcurrentHashMap<>();

    @Override
    public Bid findById(String id) {
        return bids.get(id);
    }

    @Override
    public Bid save(Bid bid) {
        return bids.put(bid.getId(), bid);
    }

    @Override
    public void delete(Bid bid) {
        bids.remove(bid.getId());
    }

    @Override
    public void deleteById(String id) {
        bids.remove(id);
    }

    @Override
    public Bid update(Bid bid) {
        return bids.put(bid.getId(), bid);
    }

    @Override
    public List<Bid> findAll() {
        return bids.values().stream().toList();
    }

    public List<Bid> findBidsByAuctionId(String auctionId) {
        return bids.values().stream()
                .filter(b -> b.getAuctionId().equals(auctionId))
                .toList();
    }
}
