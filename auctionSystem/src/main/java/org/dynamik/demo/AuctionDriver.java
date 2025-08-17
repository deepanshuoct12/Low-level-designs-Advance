package org.dynamik.demo;

import org.dynamik.enums.AuctionFilterCriteria;
import org.dynamik.enums.AuctionState;
import org.dynamik.enums.ItemCategory;
import org.dynamik.model.*;
import org.dynamik.scheduler.Job;
import org.dynamik.service.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class AuctionDriver {
    private final AuctionSystemService auctionSystem;
    private final AuctionService auctionService;
    private final UserService userService;
    private final ItemService itemService;
    private final BidService bidService;

    public AuctionDriver() {
        this.auctionSystem = AuctionSystemService.getInstance();
        this.auctionService = new AuctionService();
        this.userService = new UserService();
        this.itemService = new ItemService();
        this.bidService = new BidService();
    }

    public void runDemo() {
        System.out.println("=== Starting Auction System Demo ===\n");

        // 1. Create users
        System.out.println("1. Creating users...");
        String sellerId = createUser("John Doe", "john@example.com");
        String bidder1Id = createUser("Alice", "alice@example.com");
        String bidder2Id = createUser("Bob", "bob@example.com");
        System.out.println("Users created successfully!\n");

        // 2. Create items
        System.out.println("2. Creating items...");
        String item1Id = createItem("Vintage Camera", "Antique camera from 1950s", ItemCategory.ELECTRONICS, sellerId);
        String item2Id = createItem("Smartphone", "Latest model smartphone", ItemCategory.ELECTRONICS, sellerId);
        System.out.println("Items created successfully!\n");

        // 3. Create and register auctions
        System.out.println("3. Creating auctions...");
        String auction1Id = createAndRegisterAuction(item1Id, sellerId, 100L);
        String auction2Id = createAndRegisterAuction(item2Id, sellerId, 500L);
        System.out.println("Auctions created and registered successfully!\n");

        // 4. Display active auctions
        System.out.println("4. Current active auctions:");
        displayActiveAuctions();
        System.out.println();

        // 5. Place bids
        System.out.println("5. Placing bids...");
        placeBid(auction1Id, bidder1Id, 120.0);  // Alice bids $120
        placeBid(auction1Id, bidder2Id, 150.0);  // Bob bids $150
        placeBid(auction1Id, bidder1Id, 200.0);  // Alice outbids with $200
        System.out.println();

        // 6. Display auction status
        System.out.println("6. Current auction status:");
        displayAuctionStatus(auction1Id);
        System.out.println();

        Auction auction1 = auctionService.findAuctionById(auction1Id);
        auction1.setEndTime(new Date());
        // execute job
        Job job = new Job();
        job.executeNow();

        // 7. Simulate auction end and find winner
        System.out.println("7. Simulating auction end...");
        simulateAuctionEnd(auction1Id);
        System.out.println();

        // 8. Display winner
        System.out.println("8. Auction results:");
        displayAuctionWinner(auction1Id);
        System.out.println("\n=== Demo Completed Successfully ===");
    }

    private String createUser(String name, String email) {
        User user = new User();
        String userId = UUID.randomUUID().toString();
        user.setId(userId);
        user.setUsername(name);
        user.setEmail(email);
        userService.registerUser(user);
        return userId;
    }

    private String createItem(String name, String description, ItemCategory category, String userId) {
        Item item = new Item();
        String itemId = UUID.randomUUID().toString();
        item.setId(itemId);
        item.setName(name);
        item.setDescription(description);
        item.setCategory(category);
        item.setUserId(userId);
        itemService.addItem(item);
        return itemId;
    }

    private String createAndRegisterAuction(String itemId, String sellerId, Long startingPrice) {
        String auctionId = UUID.randomUUID().toString();
        Auction auction = new Auction();
        auction.setId(auctionId);
     //   auction.setSellerId(sellerId);
        auction.setStartingPrice(startingPrice);
   //     auction.setCurrentPrice(startingPrice);
        auction.setAuctionState(AuctionState.PENDING);
      //  auction.setStartTime(new Date());
        auction.setEndTime(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000)); // 24 hours from now
        auctionService.createAuction(auction);
        
        // Register the item for auction
        auctionSystem.registerItemForAuction(itemId, sellerId, auctionId);
        return auctionId;
    }

    private void placeBid(String auctionId, String bidderId, double amount) {
        try {
            auctionSystem.placeBid((long) amount, bidderId, auctionId);
            System.out.printf("- %s placed a bid of $%.2f on auction %s%n", 
                userService.findById(bidderId).getUsername(), amount, auctionId);
        } catch (Exception e) {
            System.out.printf("! Bid failed: %s%n", e.getMessage());
        }
    }

    private void displayActiveAuctions() {
        AuctionFilterDTO filter = new AuctionFilterDTO();
        List<Auction> activeAuctions = auctionService.findActiveAuctions(); //auctionSystem.getAuctions(filter, AuctionFilterCriteria.ALL);
        
        if (activeAuctions.isEmpty()) {
            System.out.println("No active auctions found.");
            return;
        }

        for (Auction auction : activeAuctions) {
            Item item = itemService.findItemById(auction.getItemId());
            System.out.printf("- %s (Starting Price: $%d, Status: %s)%n",
                item.getName(), auction.getStartingPrice(), auction.getAuctionState());
        }
    }

    private void displayAuctionStatus(String auctionId) {
        Auction auction = auctionService.findAuctionById(auctionId);
        Item item = itemService.findItemById(auction.getItemId());
        
        System.out.printf("Auction: %s%n", item.getName());
        System.out.printf("Status: %s%n", auction.getAuctionState());
        System.out.printf("Starting Price: $%d%n", auction.getStartingPrice());
        
        List<Bid> bids = bidService.findBidsByAuctionId(auctionId);
        if (!bids.isEmpty()) {
            System.out.println("Bid History:");
            bids.forEach(bid -> {
                String bidderName = userService.findById(bid.getUserId()).getUsername();
//                System.out.printf("- %s bid $%.2f at %s%n",
//                    bidderName, (double)bid.getAmount(), bid.getBidTime());

                System.out.printf("- %s bid $%d%n",
                        bidderName, bid.getAmount());
            });
        } else {
            System.out.println("No bids placed yet.");
        }
    }

    private void simulateAuctionEnd(String auctionId) {
        Auction auction = auctionService.findAuctionById(auctionId);
        auction.setEndTime(new Date());
        auction.setAuctionState(AuctionState.ENDED);
        auctionService.updateAuction(auction);
        System.out.println("Auction has ended!");
    }

    private void displayAuctionWinner(String auctionId) {
        try {
            User winner = auctionSystem.getWinner(auctionId);
            Auction auction = auctionService.findAuctionById(auctionId);
            Item item = itemService.findItemById(auction.getItemId());
            Long amount = bidService.findBidById(auction.getHighestBid()).getAmount();

            if (winner != null) {
                System.out.printf("The winner of '%s' is %s with a bid of $%.2f!%n",
                    item.getName(), winner.getUsername(), amount);
            } else {
                System.out.println("No winner - no valid bids were placed for this auction.");
            }
        } catch (Exception e) {
            System.out.println("Error determining winner: " + e.getMessage());
        }
    }

}
