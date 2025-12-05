package assignment2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

/**
 * Tests for the SalesAnalyzer class.
 *
 * These tests check:
 *  - Revenue calculations
 *  - Unit counting
 *  - Average price computation
 *  - Identification of highest/lowest sales
 *  - Revenue grouping by region, category, and channel
 *  - Behavior on empty datasets
 */
public class SalesAnalyzerTests {

    /** Helper method supplying a small fixed dataset used in several tests. */
    private List<SalesRecord> sample() {
        return Arrays.asList(
                new SalesRecord("2023-01-01", "North", "Electronics", "Laptop",  "Online", 5, 1200.0, 10.0),
                new SalesRecord("2023-01-02", "South", "Accessories", "Mouse",   "Store", 10, 20.0, 0.0),
                new SalesRecord("2023-01-03", "West",  "Electronics", "Monitor", "Online", 3, 300.0, 0.0),
                new SalesRecord("2023-01-04", "South", "Office",      "Desk",    "Store", 8, 100.0, 5.0)
        );
    }

    /**
     * Verifies that total revenue matches a manually computed value.
     */
    @Test
    void testTotalRevenue() {
        SalesAnalyzer analyzer = new SalesAnalyzer();

        // Manual breakdown:
        // Laptop: 5 × 1200 × 0.90 = 5400
        // Mouse: 10 × 20 = 200
        // Monitor: 3 × 300 = 900
        // Desk: 8 × 100 × 0.95 = 760
        double expected = 5400 + 200 + 900 + 760;

        assertEquals(expected, analyzer.calculateTotalRevenue(sample()), 0.001);
    }

    /**
     * Ensures all units across the dataset are summed correctly.
     */
    @Test
    void testTotalUnitsSold() {
        SalesAnalyzer analyzer = new SalesAnalyzer();
        assertEquals(5 + 10 + 3 + 8, analyzer.calculateTotalUnitsSold(sample()));
    }

    /**
     * Checks that the average price of all products is calculated correctly.
     */
    @Test
    void testAveragePrice() {
        SalesAnalyzer analyzer = new SalesAnalyzer();
        double expected = (1200 + 20 + 300 + 100) / 4.0;
        assertEquals(expected, analyzer.calculateAveragePrice(sample()), 0.001);
    }

    /**
     * Ensures the maximum-revenue sale is correctly identified.
     */
    @Test
    void testMaxSale() {
        SalesAnalyzer analyzer = new SalesAnalyzer();
        Optional<SalesRecord> max = analyzer.findMaxSale(sample());

        assertTrue(max.isPresent());
        assertEquals("Laptop", max.get().getProduct());
    }

    /**
     * Ensures the minimum-revenue sale is identified.
     */
    @Test
    void testMinSale() {
        SalesAnalyzer analyzer = new SalesAnalyzer();
        Optional<SalesRecord> min = analyzer.findMinSale(sample());

        assertTrue(min.isPresent());
        assertEquals("Mouse", min.get().getProduct());
    }

    /**
     * Confirms revenue aggregation by region.
     */
    @Test
    void testRevenueByRegion() {
        SalesAnalyzer analyzer = new SalesAnalyzer();
        Map<String, Double> map = analyzer.revenueByRegion(sample());

        assertEquals(5400, map.get("North"), 0.001);
        assertEquals(200 + 760, map.get("South"), 0.001);
        assertEquals(900, map.get("West"), 0.001);
    }

    /**
     * Confirms revenue aggregation by category.
     */
    @Test
    void testRevenueByCategory() {
        SalesAnalyzer analyzer = new SalesAnalyzer();
        Map<String, Double> map = analyzer.revenueByCategory(sample());

        assertEquals(5400 + 900, map.get("Electronics"), 0.001);
        assertEquals(200, map.get("Accessories"), 0.001);
        assertEquals(760, map.get("Office"), 0.001);
    }

    /**
     * Ensures revenue is grouped correctly by sales channel.
     */
    @Test
    void testRevenueByChannel() {
        SalesAnalyzer analyzer = new SalesAnalyzer();
        Map<String, Double> map = analyzer.revenueByChannel(sample());

        double online = 5400 + 900;
        double store = 200 + 760;

        assertEquals(online, map.get("Online"), 0.001);
        assertEquals(store, map.get("Store"), 0.001);
    }

    /**
     * Counts how many records exceed a given revenue threshold.
     */
    @Test
    void testHighValueSales() {
        SalesAnalyzer analyzer = new SalesAnalyzer();
        long count = analyzer.countHighValueSales(sample(), 1000.0);

        assertEquals(1, count); // Only Laptop qualifies
    }

    /**
     * Verifies correct behavior when given an empty dataset.
     */
    @Test
    void testEmptyDatasetHandling() {
        SalesAnalyzer analyzer = new SalesAnalyzer();
        List<SalesRecord> empty = Collections.emptyList();

        assertEquals(0, analyzer.calculateTotalRevenue(empty));
        assertEquals(0, analyzer.calculateTotalUnitsSold(empty));
        assertEquals(0, analyzer.calculateAveragePrice(empty));

        assertTrue(analyzer.revenueByCategory(empty).isEmpty());
        assertTrue(analyzer.revenueByRegion(empty).isEmpty());
        assertTrue(analyzer.revenueByChannel(empty).isEmpty());

        assertTrue(analyzer.findMaxSale(empty).isEmpty());
        assertTrue(analyzer.findMinSale(empty).isEmpty());
    }
}