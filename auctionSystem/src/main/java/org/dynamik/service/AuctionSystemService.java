package org.dynamik.service;

import org.dynamik.enums.AuctionFilterCriteria;
import org.dynamik.enums.AuctionState;
import org.dynamik.model.*;
import org.dynamik.stratregy.ISearchAuctionStratergy;
import org.dynamik.stratregy.ItemNameStratergy;
import org.dynamik.stratregy.CategoryStratergy;
import org.dynamik.stratregy.PriceStratergy;

import java.util.*;
import java.util.stream.Collectors;

public class AuctionSystemService implements IAuctionSystemService {
    // Singleton instance
    private static volatile AuctionSystemService instance;
    
    // Service dependencies
    private final AuctionService auctionService;
    private final BidService bidService;
    private final ItemService itemService;
    private final UserService userService;
    private final Map<AuctionFilterCriteria, ISearchAuctionStratergy> searchStrategies;


    // Private constructor to prevent instantiation
    private AuctionSystemService() {
        this.auctionService = new AuctionService();
        this.bidService = new BidService();
        this.itemService = new ItemService();
        this.userService = new UserService();
        
        // Initialize search strategies
        this.searchStrategies = new EnumMap<>(AuctionFilterCriteria.class);
        this.searchStrategies.put(AuctionFilterCriteria.ITEM_CATEGORY, new CategoryStratergy());
        this.searchStrategies.put(AuctionFilterCriteria.ITEM_NAME, new ItemNameStratergy());
        this.searchStrategies.put(AuctionFilterCriteria.PRICE, new PriceStratergy());
    }

    // Thread-safe singleton instance getter
    public static AuctionSystemService getInstance() {
        if (instance == null) {
            synchronized (AuctionSystemService.class) {
                if (instance == null) {
                    instance = new AuctionSystemService();
                }
            }
        }
        return instance;
    }

    @Override
    public void registerItemForAuction(String itemId, String userId, String auctionId) {
        // Get the auction that was already created
        Auction auction = auctionService.findAuctionById(auctionId);
        if (auction == null) {
            throw new IllegalArgumentException("Auction not found");
        }
        
        // Update auction details
        auction.setItemId(itemId);
        auction.setAuctionState(AuctionState.ACTIVE);
        auction.setEndTime(new Date(System.currentTimeMillis() + (24 * 60 * 60 * 1000))); // 24 hours from now
        
        auctionService.updateAuction(auction);
    }

    @Override
    public List<Auction> getAuctions(AuctionFilterDTO filter, AuctionFilterCriteria criteria) {
        // Get strategy based on criteria
        ISearchAuctionStratergy strategy = searchStrategies.get(criteria);
        if (strategy == null) {
            throw new IllegalArgumentException("Invalid auction filter criteria");
        }
        
        // Delegate to the appropriate strategy
        return strategy.getAuctions(filter);
    }

    @Override
    public void placeBid(Long amount, String userId, String auctionId) {
        Auction auction = auctionService.findAuctionById(auctionId);
        if (auction == null || auction.getAuctionState() != AuctionState.ACTIVE) {
            throw new IllegalStateException("Auction is not active or doesn't exist");
        }

        if (auction.getEndTime().before(new Date())) {
            auction.setAuctionState(AuctionState.ENDED);
             auctionService.updateAuction(auction);
            throw new IllegalStateException("Auction has already ended");
        }

        // Get the user who is placing the bid
        User user = userService.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        // Create and save the bid
        Bid bid = new Bid();
        String bidId = UUID.randomUUID().toString();
        bid.setId(bidId);
        bid.setUserId(userId);
        bid.setAuctionId(auctionId);
        bid.setAmount(amount); // Simple increment for demo
        bidService.placeBid(bid);

        Long highestBid = auction.getHighestBid() != null ? bidService.findBidById(auction.getHighestBid()).getAmount() : amount; // if 1st bid then highestBid = amount

        // Update auction with new highest bid
        if (auction.getHighestBid() == null || amount > highestBid) {
            auction.setHighestBid(bidId);
            // Notify all observers (bidders) about the new bid
            auction.notifyObservers(bid);
           // auction.setWinner(userId);
        }

        auctionService.updateAuction(auction);
    }

    @Override
    public User getWinner(String auctionId) {
        Auction auction = auctionService.findAuctionById(auctionId);
        if (auction == null) {
            throw new IllegalArgumentException("Auction not found");
        }

        if (auction.getAuctionState() != AuctionState.ENDED) {
            throw new IllegalStateException("Auction is still active");
        }

        // Get the user who placed the highest bid
        String winnerId = auctionService.findAuctionById(auctionId).getWinner();
        return userService.findById(winnerId);
    }


    @Override
    public void cancelAuction(String auctionId) {
        Auction auction = auctionService.findAuctionById(auctionId);
        if (auction == null) {
            throw new IllegalArgumentException("Auction not found");
        }
        
        if (auction.getAuctionState() == AuctionState.ENDED) {
            throw new IllegalStateException("Cannot cancel an already ended auction");
        }
        
        auction.setAuctionState(AuctionState.ENDED);
        auctionService.updateAuction(auction);
        
        // Notify all bidders that the auction was cancelled
       // auction.notifyObservers(new Bid(null, auctionId, null, "Auction has been cancelled"));
    }

    @Override
    public List<Auction> finActiveAuctions() {
        return auctionService.findActiveAuctions();
    }
}
