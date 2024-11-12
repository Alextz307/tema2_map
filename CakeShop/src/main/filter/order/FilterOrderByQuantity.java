package main.filter.order;

import main.domain.Order;
import main.filter.AbstractFilter;

public class FilterOrderByQuantity<ID> implements AbstractFilter<Order<ID>> {
    private final int minQuantity;
    private final int maxQuantity;

    public FilterOrderByQuantity(int minQuantity, int maxQuantity) {
        this.minQuantity = minQuantity;
        this.maxQuantity = maxQuantity;
    }

    @Override
    public boolean accept(Order<ID> order) {
        return order.getQuantity() >= minQuantity && order.getQuantity() <= maxQuantity;
    }
}
