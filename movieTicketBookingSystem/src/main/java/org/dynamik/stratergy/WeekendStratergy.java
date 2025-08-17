package org.dynamik.stratergy;

import org.dynamik.enums.PriceStratergy;

public class WeekendStratergy implements IPricingStratergy {
    @Override
    public boolean isApplicable(PriceStratergy priceStratergy) {
        return priceStratergy.equals(PriceStratergy.WEEKEND);
    }

    @Override
    public Double calculatePrice(Double price) {
        return price * 0.8;
    }
}
