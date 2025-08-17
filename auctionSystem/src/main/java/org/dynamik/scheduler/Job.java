package org.dynamik.scheduler;

import org.dynamik.enums.AuctionState;
import org.dynamik.model.Auction;
import org.dynamik.service.AuctionService;
import org.dynamik.service.BidService;
import org.dynamik.service.UserService;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Job {

    private AuctionService auctionService = new AuctionService();
    private UserService userService = new UserService();
    private BidService bidService = new BidService();

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);


    public void start() {
        // Start immediately and run every 5 minutes
        scheduler.scheduleAtFixedRate(
                this::execute,
                0,               // initial delay
                3,              // period
                TimeUnit.MINUTES
        );
    }

    private void execute() {
        try {
            Date now = new Date();
            List<Auction> auctions = auctionService.findAuctionBeforeEndDate(now);
            auctions.forEach(auction -> {
                auction.setAuctionState(AuctionState.ENDED);
                String highestBid = auction.getHighestBid();
                String userId = bidService.findBidById(highestBid).getUserId();
                if (highestBid != null) {
                    auction.setWinner(userId);
                }

                auctionService.updateAuction(auction);
            });
            System.out.println("Job executed at: " + now);
        } catch (Exception e) {
            System.err.println("Error in job: " + e.getMessage());
        }
    }

    public void executeNow() {
        execute();
    }

    public void stop() {
        scheduler.shutdownNow();
    }
}
