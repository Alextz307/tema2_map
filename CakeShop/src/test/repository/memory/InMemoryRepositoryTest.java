package test.repository.memory;

import main.repository.memory.InMemoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import test.repository.TestEntity;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryRepositoryTest {

    private InMemoryRepository<Integer, TestEntity> repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryRepository<>();
    }

    @Test
    void testAdd() {
        TestEntity entity = new TestEntity(1, "Test Entity");
        Integer id = repository.add(entity);

        assertNotNull(id);
        assertEquals(1, id);
        assertTrue(repository.findById(id).isPresent());
        assertEquals("Test Entity", repository.findById(id).get().getName());
    }

    @Test
    void testFindAll() {
        TestEntity entity1 = new TestEntity(1, "Entity 1");
        TestEntity entity2 = new TestEntity(2, "Entity 2");

        repository.add(entity1);
        repository.add(entity2);

        Iterable<TestEntity> entities = repository.findAll();
        ArrayList<TestEntity> entityList = new ArrayList<>();
        entities.forEach(entityList::add);

        assertEquals(2, entityList.size());
    }

    @Test
    void testFindById() {
        TestEntity entity = new TestEntity(1, "Test Entity");
        Integer id = repository.add(entity);

        Optional<TestEntity> foundEntity = repository.findById(id);
        assertTrue(foundEntity.isPresent());
        assertEquals("Test Entity", foundEntity.get().getName());
    }

    @Test
    void testModify() {
        TestEntity entity = new TestEntity(1, "Original Name");
        Integer id = repository.add(entity);

        TestEntity updatedEntity = new TestEntity(1, "Updated Name");
        updatedEntity.setId(id);
        repository.modify(updatedEntity);

        Optional<TestEntity> foundEntity = repository.findById(id);
        assertTrue(foundEntity.isPresent());
        assertEquals("Updated Name", foundEntity.get().getName());
    }

    @Test
    void testDelete() {
        TestEntity entity = new TestEntity(1, "Test Entity");
        Integer id = repository.add(entity);

        repository.delete(id);

        Optional<TestEntity> foundEntity = repository.findById(id);
        assertFalse(foundEntity.isPresent());
    }
}
