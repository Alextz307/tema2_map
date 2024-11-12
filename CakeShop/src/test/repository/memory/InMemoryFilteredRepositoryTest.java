package test.repository.memory;

import main.filter.AbstractFilter;
import main.repository.memory.InMemoryFilteredRepository;
import main.repository.memory.InMemoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import test.repository.NameFilter;
import test.repository.TestEntity;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryFilteredRepositoryTest {

    private InMemoryRepository<Integer, TestEntity> baseRepository;
    private InMemoryFilteredRepository<Integer, TestEntity> filteredRepository;

    @BeforeEach
    void setUp() {
        baseRepository = new InMemoryRepository<>();
        AbstractFilter<TestEntity> filter = new NameFilter("Accepted Name");
        filteredRepository = new InMemoryFilteredRepository<>(baseRepository, filter);
    }

    @Test
    void testFindAllWithFilter() {
        TestEntity entity1 = new TestEntity(1, "Accepted Name");
        TestEntity entity2 = new TestEntity(2, "Rejected Name");

        baseRepository.add(entity1);
        baseRepository.add(entity2);

        Iterable<TestEntity> filteredItems = filteredRepository.findAll();
        long count = filteredItems.spliterator().getExactSizeIfKnown();

        assertEquals(1, count);
        assertTrue(filteredItems.iterator().hasNext());
        assertEquals("Accepted Name", filteredItems.iterator().next().getName());
    }

    @Test
    void testFindAllWithNoMatchingFilter() {
        TestEntity entity1 = new TestEntity("Rejected Name 1");
        TestEntity entity2 = new TestEntity("Rejected Name 2");

        baseRepository.add(entity1);
        baseRepository.add(entity2);

        Iterable<TestEntity> filteredItems = filteredRepository.findAll();
        assertFalse(filteredItems.iterator().hasNext());
    }
}