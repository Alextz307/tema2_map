package main.filter;

import main.domain.BirthdayCake;

public class FilterBirthdayCakeByPriceRange<ID> implements AbstractFilter<BirthdayCake<ID>> {
    private final double minPrice;
    private final double maxPrice;

    public FilterBirthdayCakeByPriceRange(double minPrice, double maxPrice) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    @Override
    public boolean accept(BirthdayCake<ID> cake) {
        return cake.getPrice() >= minPrice && cake.getPrice() <= maxPrice;
    }
}
