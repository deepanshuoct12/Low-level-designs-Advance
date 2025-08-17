package org.dynamik.model;


import lombok.Data;
import org.dynamik.enums.ItemCategory;

@Data
public class AuctionFilterDTO {
    private ItemCategory itemCategory;
    private String itemName;
    private Long minPrice;
    private Long maxPrice;
}
