package assignment2;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Provides analytical operations on a collection of SalesRecord objects.
 */
public class SalesAnalyzer {

    // Sums revenue across all records
    public double calculateTotalRevenue(List<SalesRecord> records) {
        return records.stream()
                .mapToDouble(SalesRecord::getRevenue)
                .sum();
    }

    // Sums unitsSold across the dataset
    public int calculateTotalUnitsSold(List<SalesRecord> records) {
        return records.stream()
                .mapToInt(SalesRecord::getUnitsSold)
                .sum();
    }

    // Computes the average of product unit prices
    public double calculateAveragePrice(List<SalesRecord> records) {
        return records.stream()
                .mapToDouble(SalesRecord::getUnitPrice)
                .average()
                .orElse(0.0);
    }

    // Largest revenue record
    public Optional<SalesRecord> findMaxSale(List<SalesRecord> records) {
        return records.stream()
                .max(Comparator.comparing(SalesRecord::getRevenue));
    }

    // Smallest revenue record
    public Optional<SalesRecord> findMinSale(List<SalesRecord> records) {
        return records.stream()
                .min(Comparator.comparing(SalesRecord::getRevenue));
    }

    // Groups revenue by region
    public Map<String, Double> revenueByRegion(List<SalesRecord> records) {
        return records.stream()
                .collect(Collectors.groupingBy(
                        SalesRecord::getRegion,
                        Collectors.summingDouble(SalesRecord::getRevenue)
                ));
    }

    // Groups revenue by category
    public Map<String, Double> revenueByCategory(List<SalesRecord> records) {
        return records.stream()
                .collect(Collectors.groupingBy(
                        SalesRecord::getCategory,
                        Collectors.summingDouble(SalesRecord::getRevenue)
                ));
    }

    // Groups revenue by sales channel
    public Map<String, Double> revenueByChannel(List<SalesRecord> records) {
        return records.stream()
                .collect(Collectors.groupingBy(
                        SalesRecord::getChannel,
                        Collectors.summingDouble(SalesRecord::getRevenue)
                ));
    }

    // Counts records with revenue above a threshold
    public long countHighValueSales(List<SalesRecord> records, double threshold) {
        return records.stream()
                .filter(r -> r.getRevenue() > threshold)
                .count();
    }
}