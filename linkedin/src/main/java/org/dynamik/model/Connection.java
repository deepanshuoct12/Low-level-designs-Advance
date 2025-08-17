package org.dynamik.model;

import org.dynamik.enums.ConnectionRequestState;

import lombok.Data;

@Data
public class Connection extends AbstractEntity {
    private String userId; // sender
    private String connectionId;  //  recciever
    private ConnectionRequestState state;
}
