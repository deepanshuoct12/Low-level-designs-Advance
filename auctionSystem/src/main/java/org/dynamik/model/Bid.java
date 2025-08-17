package org.dynamik.model;

import lombok.Data;

@Data
public class Bid {
    private String id;
    private String userId;
    private String auctionId;
    private Long  amount;
}
