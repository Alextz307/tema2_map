package test.repository;

import main.filter.AbstractFilter;

public class NameFilter implements AbstractFilter<TestEntity> {
    private final String nameToFilter;

    public NameFilter(String nameToFilter) {
        this.nameToFilter = nameToFilter;
    }

    @Override
    public boolean accept(TestEntity item) {
        return item.getName().equals(nameToFilter);
    }
}