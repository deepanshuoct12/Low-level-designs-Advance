package org.dynamik.model;

import lombok.Data;
import org.dynamik.enums.AuctionState;
import org.dynamik.observer.Subject;

import java.util.Date;

@Data
public class Auction extends Subject {
    private String id;
    private String itemId;
    private String  winner;
    private Date endTime;
    private String highestBid;
    private Long startingPrice;
    private AuctionState auctionState;
}
