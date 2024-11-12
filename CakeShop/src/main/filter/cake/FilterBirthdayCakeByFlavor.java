package main.filter.cake;

import main.domain.BirthdayCake;
import main.filter.AbstractFilter;

public class FilterBirthdayCakeByFlavor<ID> implements AbstractFilter<BirthdayCake<ID>> {
    private final String desiredFlavor;

    public FilterBirthdayCakeByFlavor(String desiredFlavor) {
        this.desiredFlavor = desiredFlavor;
    }

    @Override
    public boolean accept(BirthdayCake<ID> cake) {
        return cake.getFlavor().equalsIgnoreCase(desiredFlavor);
    }
}
