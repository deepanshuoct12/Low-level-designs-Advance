package org.dynamik.stratergy.ticket;

import org.dynamik.constants.FareType;

public interface IFareStratergy {
    Boolean isApplicable(FareType fareType);
    Double getFare(String ticketId);
}
