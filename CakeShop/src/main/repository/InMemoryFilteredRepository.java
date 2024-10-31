package main.repository;

import main.domain.Identifiable;
import main.filter.AbstractFilter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryFilteredRepository<ID, T extends Identifiable<ID>> extends InMemoryRepository<ID, T> {
    private final IRepository<ID, T> repository;
    private final AbstractFilter<T> filter;

    public InMemoryFilteredRepository(IRepository<ID, T> repository, AbstractFilter<T> filter) {
        this.repository = repository;
        this.filter = filter;
    }

    @Override
    public Iterable<T> findAll() {
        List<T> filteredItems = new ArrayList<>();

        for (T item : this.repository.findAll()) {
            if (filter.accept(item)) {
                filteredItems.add(item);
            }
        }

        return filteredItems;
    }
}
