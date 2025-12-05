package assignment2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.*;
import java.util.*;
import java.io.IOException;

/**
 * Tests for the SalesLoader class.
 *
 * These tests verify:
 *  - CSV files load correctly into SalesRecord objects
 *  - Empty files return empty lists
 *  - Malformed or invalid rows produce appropriate exceptions
 *  - Numeric parsing errors are detected
 *  - Trailing commas behave consistently with the implementation
 */
public class SalesLoaderTests {

    /** Creates a temporary CSV file containing the given content. */
    private Path writeTemp(String content) throws IOException {
        Path p = Files.createTempFile("sales_test", ".csv");
        Files.writeString(p, content);
        return p;
    }

    /**
     * Confirms that a valid CSV with two rows is parsed into
     * two SalesRecord instances with correct field values.
     */
    @Test
    void testValidCsvParsing() throws Exception {
        String csv =
                "date,region,category,product,channel,unitsSold,unitPrice,discountPercent\n" +
                "2023-01-01,North,Electronics,Laptop,Online,5,1200.00,10.0\n" +
                "2023-01-02,South,Accessories,Mouse,Store,10,20.00,0.0\n";

        Path file = writeTemp(csv);

        SalesLoader loader = new SalesLoader();
        List<SalesRecord> records = loader.load(file.toString());

        assertEquals(2, records.size());

        SalesRecord r = records.get(0);
        assertEquals("North", r.getRegion());
        assertEquals("Laptop", r.getProduct());
        assertEquals(5, r.getUnitsSold());
    }

    /**
     * A CSV containing only a header should return an empty list.
     */
    @Test
    void testEmptyCsvReturnsEmptyList() throws Exception {
        String csv = "date,region,category,product,channel,unitsSold,unitPrice,discountPercent\n";

        Path file = writeTemp(csv);

        SalesLoader loader = new SalesLoader();
        List<SalesRecord> result = loader.load(file.toString());

        assertTrue(result.isEmpty());
    }

    /**
     * Fewer than the required 8 fields should result in an exception.
     */
    @Test
    void testMalformedCsvThrows() throws Exception {
        String csv =
                "date,region,category,product,channel,unitsSold,unitPrice,discountPercent\n" +
                "2023-01-01,North,Electronics";

        Path file = writeTemp(csv);

        SalesLoader loader = new SalesLoader();

        assertThrows(IllegalArgumentException.class,
                () -> loader.load(file.toString()));
    }

    /**
     * Confirms numeric fields that cannot be parsed throw NumberFormatException.
     */
    @Test
    void testInvalidNumericValuesThrow() throws Exception {
        String csv =
                "date,region,category,product,channel,unitsSold,unitPrice,discountPercent\n" +
                "2023-01-01,North,Electronics,Laptop,Online,notANumber,1200.00,10.0";

        Path file = writeTemp(csv);

        SalesLoader loader = new SalesLoader();

        assertThrows(NumberFormatException.class,
                () -> loader.load(file.toString()));
    }

    /**
     * A trailing comma produces a ninth field, but your loader
     * does not reject this. This test verifies the current behavior.
     */
    @Test
    void testTrailingCommaLineIsAcceptedByYourLoader() throws Exception {
        String csv =
                "date,region,category,product,channel,unitsSold,unitPrice,discountPercent\n" +
                "2023-01-01,North,Electronics,Laptop,Online,5,1200.00,10.0,\n";

        Path file = writeTemp(csv);

        SalesLoader loader = new SalesLoader();

        assertDoesNotThrow(() -> loader.load(file.toString()));
    }
}