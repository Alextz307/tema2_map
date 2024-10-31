package main.service;

import main.domain.BirthdayCake;
import main.filter.FilterBirthdayCakeByFlavor;
import main.filter.FilterBirthdayCakeByPriceRange;
import main.repository.IRepository;
import main.repository.InMemoryFilteredRepository;

import java.util.Optional;

public class BirthdayCakeService<ID> {
    private final IRepository<ID, BirthdayCake<ID>> repository;

    public BirthdayCakeService(IRepository<ID, BirthdayCake<ID>> repository) {
        this.repository = repository;
    }

    public ID addCake(BirthdayCake<ID> cake) {
        validateCake(cake);
        return repository.add(cake);
    }

    public Iterable<BirthdayCake<ID>> getAllCakes() {
        return repository.findAll();
    }

    public Optional<BirthdayCake<ID>> getCakeById(ID id) {
        return repository.findById(id);
    }

    public void updateCake(BirthdayCake<ID> cake) {
        validateCake(cake);
        repository.modify(cake);
    }

    public void deleteCake(ID id) {
        if (repository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("Cake with ID " + id + " does not exist.");
        }

        repository.delete(id);
    }

    public Iterable<BirthdayCake<ID>> filterByFlavor(String desiredFlavor) {
        FilterBirthdayCakeByFlavor<ID> flavorFilter = new FilterBirthdayCakeByFlavor<>(desiredFlavor);

        InMemoryFilteredRepository<ID, BirthdayCake<ID>> filteredRepository =
                new InMemoryFilteredRepository<>(repository, flavorFilter);

        return filteredRepository.findAll();
    }

    public Iterable<BirthdayCake<ID>> filterByPriceRange(double minPrice, double maxPrice) {
        FilterBirthdayCakeByPriceRange<ID> priceFilter = new FilterBirthdayCakeByPriceRange<>(minPrice, maxPrice);

        InMemoryFilteredRepository<ID, BirthdayCake<ID>> filteredRepository =
                new InMemoryFilteredRepository<>(repository, priceFilter);

        return filteredRepository.findAll();
    }

    private void validateCake(BirthdayCake<ID> cake) {
        if (cake.getName() == null || cake.getName().isEmpty()) {
            throw new IllegalArgumentException("Cake name cannot be empty.");
        }

        if (cake.getFlavor() == null || cake.getFlavor().isEmpty()) {
            throw new IllegalArgumentException("Cake flavor cannot be empty.");
        }

        if (cake.getPrice() <= 0) {
            throw new IllegalArgumentException("Cake price must be positive.");
        }

        if (cake.getLayers() <= 0) {
            throw new IllegalArgumentException("Cake must have at least 1 layer.");
        }
    }
}
