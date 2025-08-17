package org.dynamik.stratergy;

import org.dynamik.enums.PriceStratergy;

public interface
IPricingStratergy {
    public boolean isApplicable(PriceStratergy priceStratergy);
    public Double calculatePrice(Double price);
}
