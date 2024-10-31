package main.filter;

import main.domain.Order;

public class FilterOrderByStatus<ID> implements AbstractFilter<Order<ID>> {
    private final String desiredStatus;

    public FilterOrderByStatus(String desiredStatus) {
        this.desiredStatus = desiredStatus;
    }

    @Override
    public boolean accept(Order<ID> order) {
        return order.getStatus().equalsIgnoreCase(desiredStatus);
    }
}
