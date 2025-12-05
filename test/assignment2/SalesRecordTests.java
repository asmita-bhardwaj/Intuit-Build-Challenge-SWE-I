package assignment2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the SalesRecord class.
 * 
 * These tests verify:
 *  - Constructor correctly stores all field values
 *  - Getter methods return the expected values
 *  - Basic revenue-related computations behave predictably
 *  - Edge cases such as negative values do not cause errors
 */
public class SalesRecordTests {

    /**
     * Ensures all fields given to the constructor are stored correctly.
     */
    @Test
    void testConstructorAndGetters() {
        SalesRecord r = new SalesRecord(
                "2023-01-01",
                "North",
                "Electronics",
                "Laptop",
                "Online",
                5,
                1200.0,
                10.0
        );

        assertEquals("2023-01-01", r.getDate());
        assertEquals("North", r.getRegion());
        assertEquals("Electronics", r.getCategory());
        assertEquals("Laptop", r.getProduct());
        assertEquals("Online", r.getChannel());
        assertEquals(5, r.getUnitsSold());
        assertEquals(1200.0, r.getUnitPrice());
        assertEquals(10.0, r.getDiscountPercent());
    }

    /**
     * Verifies the product of unitsSold × unitPrice is correct
     * for a simple input.
     */
    @Test
    void testBasicRevenueMath() {
        SalesRecord r = new SalesRecord(
                "2023-01-01",
                "East",
                "Office",
                "Chair",
                "Store",
                2,
                100.0,
                0.0
        );

        assertEquals(200.0, r.getUnitsSold() * r.getUnitPrice());
    }

    /**
     * Confirms that negative values do not cause exceptions.
     * The assignment does not define constraints that forbid them.
     */
    @Test
    void testNegativeValuesAreAccepted() {
        assertDoesNotThrow(() ->
                new SalesRecord("2023-01-01", "X", "Y", "Z", "Online", -5, -80.0, -1.0)
        );
    }
}