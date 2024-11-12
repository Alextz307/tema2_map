package test.domain;

import main.domain.BirthdayCake;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BirthdayCakeTest {

    @Test
    public void testConstructorWithAllFields() {
        BirthdayCake<Integer> cake = new BirthdayCake<>(1, "Chocolate Delight", "Chocolate", 20.5, 3);

        assertEquals(1, cake.getId());
        assertEquals("Chocolate Delight", cake.getName());
        assertEquals("Chocolate", cake.getFlavor());
        assertEquals(20.5, cake.getPrice());
        assertEquals(3, cake.getLayers());
    }

    @Test
    public void testConstructorWithoutId() {
        BirthdayCake<String> cake = new BirthdayCake<>("Vanilla Bliss", "Vanilla", 15.0, 2);

        assertNull(cake.getId());
        assertEquals("Vanilla Bliss", cake.getName());
        assertEquals("Vanilla", cake.getFlavor());
        assertEquals(15.0, cake.getPrice());
        assertEquals(2, cake.getLayers());
    }

    @Test
    public void testSetId() {
        BirthdayCake<Integer> cake = new BirthdayCake<>(null, "Red Velvet", "Red Velvet", 25.0, 4);

        assertNull(cake.getId());
        cake.setId(5);
        assertEquals(5, cake.getId());
    }

    @Test
    public void testToString() {
        BirthdayCake<Integer> cake = new BirthdayCake<>(10, "Strawberry Dream", "Strawberry", 30.0, 5);

        String expectedString = "10,Strawberry Dream,Strawberry,30.0,5";
        assertEquals(expectedString, cake.toString());
    }

    @Test
    public void testNegativePrice() {
        BirthdayCake<Integer> cake = new BirthdayCake<>(11, "Lemon Tart", "Lemon", -5.0, 1);
        assertEquals(-5.0, cake.getPrice());
    }

    @Test
    public void testZeroLayers() {
        BirthdayCake<Integer> cake = new BirthdayCake<>(12, "Plain Cake", "Plain", 0.0, 0);
        assertEquals(0, cake.getLayers());
    }

    @Test
    public void testEmptyNameAndFlavor() {
        BirthdayCake<Integer> cake = new BirthdayCake<>(13, "", "", 10.0, 1);

        assertEquals("", cake.getName());
        assertEquals("", cake.getFlavor());
        assertEquals(10.0, cake.getPrice());
        assertEquals(1, cake.getLayers());
    }

    @Test
    public void testNullValuesInConstructor() {
        BirthdayCake<Integer> cake = new BirthdayCake<>(null, null, null, 0.0, 0);

        assertNull(cake.getId());
        assertNull(cake.getName());
        assertNull(cake.getFlavor());
        assertEquals(0.0, cake.getPrice());
        assertEquals(0, cake.getLayers());
    }
}
