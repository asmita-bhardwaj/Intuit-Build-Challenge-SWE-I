package assignment2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Loads sales data from a CSV file and converts each row into a SalesRecord.
 */
public class SalesLoader {

    public List<SalesRecord> load(String filePath) {
        try (Stream<String> lines = Files.lines(Path.of(filePath))) {

            return lines
                    .skip(1)        // Skip header row
                    .filter(s -> !s.trim().isEmpty())
                    .map(this::parseLine)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new RuntimeException("Failed to read CSV file: " + filePath, e);
        }
    }

    // Converts a CSV line into a SalesRecord
    private SalesRecord parseLine(String line) {
        String[] parts = line.trim().split(",");

        if (parts.length < 8) {
            throw new IllegalArgumentException("Invalid CSV row (expected at least 8 fields): " + line);
        }

        String date = parts[0].trim();
        String region = parts[1].trim();
        String category = parts[2].trim();
        String product = parts[3].trim();
        String channel = parts[4].trim();

        int unitsSold = Integer.parseInt(parts[5].trim());
        double unitPrice = Double.parseDouble(parts[6].trim());
        double discountPercent = Double.parseDouble(parts[7].trim());

        return new SalesRecord(
                date, region, category, product, channel,
                unitsSold, unitPrice, discountPercent
        );
    }
}