package main.service;

import main.domain.BirthdayCake;
import main.filter.cake.FilterBirthdayCakeByFlavor;
import main.filter.cake.FilterBirthdayCakeByPriceRange;
import main.repository.IRepository;
import main.repository.memory.InMemoryFilteredRepository;
import main.validators.CakeValidator;

import java.util.Optional;

public class BirthdayCakeService<ID> {
    private final IRepository<ID, BirthdayCake<ID>> repository;
    private final CakeValidator<ID> cakeValidator;

    public BirthdayCakeService(IRepository<ID, BirthdayCake<ID>> repository) {
        this.repository = repository;
        this.cakeValidator = new CakeValidator<>();
    }

    public ID addCake(BirthdayCake<ID> cake) {
        cakeValidator.validate(cake);
        return repository.add(cake);
    }

    public Iterable<BirthdayCake<ID>> getAllCakes() {
        return repository.findAll();
    }

    public Optional<BirthdayCake<ID>> getCakeById(ID id) {
        return repository.findById(id);
    }

    public void updateCake(BirthdayCake<ID> cake) {
        cakeValidator.validate(cake);
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
        validatePriceRange(minPrice, maxPrice);

        FilterBirthdayCakeByPriceRange<ID> priceFilter = new FilterBirthdayCakeByPriceRange<>(minPrice, maxPrice);
        InMemoryFilteredRepository<ID, BirthdayCake<ID>> filteredRepository =
                new InMemoryFilteredRepository<>(repository, priceFilter);

        return filteredRepository.findAll();
    }

    private void validatePriceRange(double minPrice, double maxPrice) {
        if (minPrice < 0) {
            throw new IllegalArgumentException("Minimum price cannot be negative.");
        }

        if (maxPrice < minPrice) {
            throw new IllegalArgumentException("Maximum price must be greater than or equal to the minimum price.");
        }
    }
}
