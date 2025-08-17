package org.dynamik.model;

import lombok.Data;

import java.util.Map;

@Data
public class PortFolio extends AbstractEntity {
    private String accountId;
    private Map<String, Long> holdings;  // stockId, quantity
}
