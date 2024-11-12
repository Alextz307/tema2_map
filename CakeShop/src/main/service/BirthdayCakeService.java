package main.service;

import main.domain.BirthdayCake;
import main.filter.cake.FilterBirthdayCakeByFlavor;
import main.filter.cake.FilterBirthdayCakeByPriceRange;
import main.repository.IRepository;
import main.repository.memory.InMemoryFilteredRepository;
import main.validators.CakeValidator;

import java.util.List;
import java.util.Optional;

public class BirthdayCakeService {
    private final IRepository<Integer, BirthdayCake<Integer>> repository;
    private final CakeValidator<Integer> cakeValidator;
    private Integer currentCakeId;

    public BirthdayCakeService(IRepository<Integer, BirthdayCake<Integer>> repository) {
        this.repository = repository;
        this.currentCakeId = ((List<BirthdayCake<Integer>>)repository.findAll()).size() + 1;
        this.cakeValidator = new CakeValidator<>();
    }

    public Integer addCake(BirthdayCake<Integer> cake) {
        cakeValidator.validate(cake);
        cake.setId(currentCakeId++);
        repository.add(cake);
        return cake.getId();
    }

    public Iterable<BirthdayCake<Integer>> getAllCakes() {
        return repository.findAll();
    }

    public Optional<BirthdayCake<Integer>> getCakeById(Integer id) {
        return repository.findById(id);
    }

    public void updateCake(BirthdayCake<Integer> cake) {
        cakeValidator.validate(cake);
        
        if (repository.findById(cake.getId()).isEmpty()) {
            throw new IllegalArgumentException("Cake with ID " + cake.getId() + " does not exist.");
        }

        repository.modify(cake);
    }

    public void deleteCake(Integer id) {
        if (repository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("Cake with ID " + id + " does not exist.");
        }

        repository.delete(id);
    }

    public Iterable<BirthdayCake<Integer>> filterByFlavor(String desiredFlavor) {
        FilterBirthdayCakeByFlavor<Integer> flavorFilter = new FilterBirthdayCakeByFlavor<>(desiredFlavor);
        InMemoryFilteredRepository<Integer, BirthdayCake<Integer>> filteredRepository =
                new InMemoryFilteredRepository<>(repository, flavorFilter);

        return filteredRepository.findAll();
    }

    public Iterable<BirthdayCake<Integer>> filterByPriceRange(double minPrice, double maxPrice) {
        validatePriceRange(minPrice, maxPrice);

        FilterBirthdayCakeByPriceRange<Integer> priceFilter = new FilterBirthdayCakeByPriceRange<>(minPrice, maxPrice);
        InMemoryFilteredRepository<Integer, BirthdayCake<Integer>> filteredRepository =
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
