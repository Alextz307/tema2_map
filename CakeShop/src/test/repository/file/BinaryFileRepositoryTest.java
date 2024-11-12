package test.repository.file;

import main.repository.file.binary.BinaryFileRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import test.repository.TestEntity;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BinaryFileRepositoryTest {
    private static final String FILENAME = "test_binary_file.bin";
    private BinaryFileRepository<Long, TestEntity> repository;

    @BeforeEach
    public void setUp() {
        repository = new BinaryFileRepository<>(FILENAME);
    }

    @AfterEach
    public void tearDown() {
        File file = new File(FILENAME);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testAdd() {
        TestEntity entity = new TestEntity("Test Name");
        entity.setId(1L);

        repository = new BinaryFileRepository<>(FILENAME);
        repository.add(entity);
        List<TestEntity> entities = (List<TestEntity>) repository.findAll();

        assertEquals(1, entities.size(), "Should have one entity after reading from file");
        assertEquals(entity.getId(), entities.getFirst().getId(), "The ID should match the added entity");
        assertEquals(entity.getName(), entities.getFirst().getName(), "The name should match the added entity");
    }

    @Test
    public void testReadFromFileWithEmptyFile() {
        repository = new BinaryFileRepository<>(FILENAME);

        List<TestEntity> entities = (List<TestEntity>) repository.findAll();
        assertTrue(entities.isEmpty(), "The repository should be empty when reading from an empty file");
    }
}
