package test.service;

import main.domain.BirthdayCake;
import main.repository.memory.InMemoryBirthdayCakeRepository;
import main.service.BirthdayCakeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class BirthdayCakeServiceTest {

    private InMemoryBirthdayCakeRepository repository;
    private BirthdayCakeService<Long> birthdayCakeService;

    @BeforeEach
    void setUp() {
        repository = new InMemoryBirthdayCakeRepository();
        birthdayCakeService = new BirthdayCakeService<>(repository);
    }

    @Test
    void addCake_ShouldAddCake_WhenValid() {
        BirthdayCake<Long> cake = new BirthdayCake<>(1L, "Deluxe", "Chocolate", 25.0, 3);
        Long cakeId = birthdayCakeService.addCake(cake);
        assertEquals(cake.getId(), cakeId);
        assertTrue(repository.findById(cakeId).isPresent());
    }

    @Test
    void getAllCakes_ShouldReturnAllCakes() {
        BirthdayCake<Long> cake1 = new BirthdayCake<>(1L, "Deluxe", "Chocolate", 25.0, 3);
        BirthdayCake<Long> cake2 = new BirthdayCake<>(2L, "Classic", "Vanilla", 15.0, 2);
        repository.add(cake1);
        repository.add(cake2);

        Iterable<BirthdayCake<Long>> result = birthdayCakeService.getAllCakes();
        List<BirthdayCake<Long>> cakeList = new ArrayList<>();
        result.forEach(cakeList::add);

        assertEquals(2, cakeList.size());
    }

    @Test
    void getAllCakes_ShouldReturnEmptyList_WhenNoCakes() {
        Iterable<BirthdayCake<Long>> result = birthdayCakeService.getAllCakes();
        assertFalse(result.iterator().hasNext(), "getAllCakes should return an empty iterable if there are no cakes");
    }

    @Test
    void getCakeById_ShouldReturnCake_WhenExists() {
        Long cakeId = 1L;
        BirthdayCake<Long> cake = new BirthdayCake<>(cakeId, "Strawberry Delight", "Strawberry", 30.0, 4);
        repository.add(cake);

        Optional<BirthdayCake<Long>> result = birthdayCakeService.getCakeById(cakeId);
        assertTrue(result.isPresent());
        assertEquals(cake, result.get());
    }

    @Test
    void getCakeById_ShouldReturnEmpty_WhenCakeDoesNotExist() {
        Long cakeId = 1L;
        Optional<BirthdayCake<Long>> result = birthdayCakeService.getCakeById(cakeId);
        assertFalse(result.isPresent(), "getCakeById should return an empty Optional if the cake does not exist");
    }

    @Test
    void updateCake_ShouldUpdateCake_WhenValid() {
        Long cakeId = 1L;
        BirthdayCake<Long> cake = new BirthdayCake<>(cakeId, "Lemon Fresh", "Lemon", 22.0, 2);
        repository.add(cake);

        BirthdayCake<Long> updatedCake = new BirthdayCake<>(cakeId, "Lemon Fresh", "Lemon", 24.0, 2);
        birthdayCakeService.updateCake(updatedCake);
        assertEquals(24.0, repository.findById(cakeId).get().getPrice());
    }

    @Test
    void deleteCake_ShouldDeleteCake_WhenExists() {
        Long cakeId = 1L;
        BirthdayCake<Long> cake = new BirthdayCake<>(cakeId, "Red Velvet", "Red Velvet", 28.0, 3);
        repository.add(cake);

        birthdayCakeService.deleteCake(cakeId);
        assertFalse(repository.findById(cakeId).isPresent());
    }

    @Test
    void deleteCake_ShouldThrowException_WhenCakeDoesNotExist() {
        Long cakeId = 1L;

        assertThrows(IllegalArgumentException.class, () -> birthdayCakeService.deleteCake(cakeId));
    }

    @Test
    void filterByFlavor_ShouldReturnCakes_WithDesiredFlavor() {
        String flavor = "Vanilla";
        BirthdayCake<Long> cake = new BirthdayCake<>(1L, "Classic Vanilla", "Vanilla", 20.0, 2);
        repository.add(cake);

        Iterable<BirthdayCake<Long>> result = birthdayCakeService.filterByFlavor(flavor);
        result.forEach(c -> assertEquals(flavor, c.getFlavor()));
    }

    @Test
    void filterByFlavor_ShouldReturnEmptyList_WhenNoMatchingFlavor() {
        String flavor = "Matcha";
        BirthdayCake<Long> cake = new BirthdayCake<>(1L, "Classic Vanilla", "Vanilla", 20.0, 2);
        repository.add(cake);

        Iterable<BirthdayCake<Long>> result = birthdayCakeService.filterByFlavor(flavor);
        assertFalse(result.iterator().hasNext(), "filterByFlavor should return an empty iterable if no cakes match the flavor");
    }

    @Test
    void filterByPriceRange_ShouldReturnCakes_InPriceRange() {
        BirthdayCake<Long> cake1 = new BirthdayCake<>(1L, "Classic Chocolate", "Chocolate", 20.0, 2);
        BirthdayCake<Long> cake2 = new BirthdayCake<>(2L, "Simple Vanilla", "Vanilla", 15.0, 1);
        repository.add(cake1);
        repository.add(cake2);

        Iterable<BirthdayCake<Long>> result = birthdayCakeService.filterByPriceRange(10.0, 30.0);
        result.forEach(cake -> assertTrue(cake.getPrice() >= 10.0 && cake.getPrice() <= 30.0));
    }

    @Test
    void filterByPriceRange_ShouldReturnEmptyList_WhenNoCakesInRange() {
        BirthdayCake<Long> cake = new BirthdayCake<>(1L, "Expensive Cake", "Vanilla", 50.0, 1);
        repository.add(cake);

        Iterable<BirthdayCake<Long>> result = birthdayCakeService.filterByPriceRange(10.0, 30.0);
        assertFalse(result.iterator().hasNext(), "filterByPriceRange should return an empty iterable if no cakes fall within the range");
    }

    @Test
    void filterByPriceRange_ShouldThrowException_WhenMinPriceIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> birthdayCakeService.filterByPriceRange(-1.0, 10.0));
    }

    @Test
    void filterByPriceRange_ShouldThrowException_WhenMaxPriceLessThanMinPrice() {
        assertThrows(IllegalArgumentException.class, () -> birthdayCakeService.filterByPriceRange(15.0, 10.0));
    }
}
