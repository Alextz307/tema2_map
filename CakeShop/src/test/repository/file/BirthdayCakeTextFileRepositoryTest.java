package test.repository.file;

import main.domain.BirthdayCake;
import main.repository.file.text.BirthdayCakeTextFileRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BirthdayCakeTextFileRepositoryTest {
    private static final String FILENAME = "test_cakes.txt";
    private BirthdayCakeTextFileRepository repository;

    @BeforeEach
    public void setUp() {
        repository = new BirthdayCakeTextFileRepository(FILENAME);
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
        BirthdayCake<Integer> cake = new BirthdayCake<>(1, "Chocolate Dream", "Chocolate", 15.99, 3);

        repository.add(cake);
        List<BirthdayCake<Integer>> cakes = (List<BirthdayCake<Integer>>) repository.findAll();

        assertEquals(1, cakes.size(), "Should have one cake after adding");
        assertEquals(cake.getName(), cakes.getFirst().getName(), "The cake name should match the added cake");
        assertEquals(cake.getFlavor(), cakes.getFirst().getFlavor(), "The cake flavor should match the added cake");
        assertEquals(cake.getPrice(), cakes.getFirst().getPrice(), "The cake price should match the added cake");
        assertEquals(cake.getLayers(), cakes.getFirst().getLayers(), "The number of layers should match the added cake");
    }
}
