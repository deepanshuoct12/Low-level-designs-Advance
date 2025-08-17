package org.dynamik.dao;

import org.dynamik.model.Auction;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.dynamik.enums.AuctionState.ACTIVE;

public class AuctionDao implements IBaseDao<Auction, String> {
    private static final Map<String, Auction> auctions = new ConcurrentHashMap<>();

    @Override
    public Auction findById(String id) {
        return auctions.get(id);
    }

    @Override
    public Auction save(Auction auction) {
        return auctions.put(auction.getId(), auction);
    }

    @Override
    public void delete(Auction auction) {
        auctions.remove(auction.getId());
    }

    @Override
    public void deleteById(String id) {
        auctions.remove(id);
    }

    @Override
    public Auction update(Auction auction) {
        return auctions.put(auction.getId(), auction);
    }

    @Override
    public List<Auction> findAll() {
        return auctions.values().stream().toList();
    }

    public Auction findAuctionByItemId(String itemId) {
        return auctions.values().stream()
                .filter(auction -> auction.getItemId().equals(itemId) && auction.getAuctionState().equals(ACTIVE))
                .findFirst()
                .orElse(null);
    }
}
