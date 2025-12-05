package assignment2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * Entry point for the sales analysis program.
 * Loads the dataset, runs analysis, and prints results.
 */
public class Assignment2Main {

    public static void main(String[] args) {

        // Resolve CSV path
        Path filePath = Paths.get("data/sales.csv").toAbsolutePath();

        if (!Files.exists(filePath)) {
            System.err.println("CSV file not found at: " + filePath);
            return;
        }

        // Load records
        SalesLoader loader = new SalesLoader();
        List<SalesRecord> records = loader.load(filePath.toString());

        SalesAnalyzer analyzer = new SalesAnalyzer();

        System.out.println("=== SALES ANALYSIS REPORT ===\n");

        double totalRevenue = analyzer.calculateTotalRevenue(records);
        int totalUnits = analyzer.calculateTotalUnitsSold(records);
        double avgPrice = analyzer.calculateAveragePrice(records);

        System.out.printf("Total Revenue: $%.2f%n", totalRevenue);
        System.out.printf("Total Units Sold: %d%n", totalUnits);
        System.out.printf("Average Unit Price: $%.2f%n%n", avgPrice);

        analyzer.findMaxSale(records).ifPresent(max ->
                System.out.printf("Highest Sale: %s (%s) - $%.2f%n",
                        max.getProduct(), max.getRegion(), max.getRevenue())
        );

        analyzer.findMinSale(records).ifPresent(min ->
                System.out.printf("Lowest Sale: %s (%s) - $%.2f%n%n",
                        min.getProduct(), min.getRegion(), min.getRevenue())
        );

        System.out.println("Revenue by Region:");
        printMap(analyzer.revenueByRegion(records));

        System.out.println("\nRevenue by Category:");
        printMap(analyzer.revenueByCategory(records));

        System.out.println("\nRevenue by Channel:");
        printMap(analyzer.revenueByChannel(records));

        long highValueCount = analyzer.countHighValueSales(records, 1000.0);
        System.out.printf("%nHigh Value Sales (> $1000): %d%n", highValueCount);

        System.out.println("\n=== END REPORT ===");
    }

    // Helper method to print grouped revenue values
    private static void printMap(Map<String, Double> map) {
        map.forEach((key, value) ->
                System.out.printf("  %s: $%.2f%n", key, value)
        );
    }
}