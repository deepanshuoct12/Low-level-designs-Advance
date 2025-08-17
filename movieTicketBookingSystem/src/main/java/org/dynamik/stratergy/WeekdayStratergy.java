package org.dynamik.stratergy;

import org.dynamik.enums.PriceStratergy;

public class WeekdayStratergy implements IPricingStratergy {
    @Override
    public boolean isApplicable(PriceStratergy priceStratergy) {
        return priceStratergy.equals(PriceStratergy.WEEKDAY);
    }

    @Override
    public Double calculatePrice(Double price) {
        return price;
    }
}
