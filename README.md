# Intuit Build Challenge – SWE I  
**Author:** Asmita Bhardwaj  
**Language:** Java  
**IDE:** VS Code  

This repository contains my completed solutions for Assignment 1 and Assignment 2 of the Intuit Build Challenge.  
Both assignments were implemented in Java, and the code emphasizes clarity, correctness, and testability.  
Each assignment includes a JUnit test suite, setup instructions, and sample program output.

---

# Table of Contents
- [Project Structure](#project-structure)
- [Assignment 1 — Producer–Consumer Pattern](#assignment-1---producer-consumer-pattern)
  - [Overview](#overview)
  - [Setup Instructions](#setup-instructions-assignment-1)
  - [Sample Output (BlockingQueue)](#sample-output-blockingqueue-version)
  - [Sample Output (wait/notify)](#sample-output-waitnotify-version)
- [Assignment 2 — Sales Data Analysis (Java Streams)](#assignment-2--sales-data-analysis-java-streams)
  - [Overview](#overview-1)
  - [Dataset Justification](#dataset-justification)
  - [Setup Instructions](#setup-instructions-assignment-2)
  - [Sample Output](#sample-output-assignment-2)
- [Testing Information](#testing-information)
  - [Assignment 1 Tests](#assignment-1-–-concurrency-tests)
  - [Assignment 2 Tests](#assignment-2-–-unit-tests-for-all-analysis-methods)
- [Conclusion](#conclusion)

---

# Project Structure

```
IntuitBuildChallenge/
│
├── src/
│   ├── assignment1/                     # Assignment 1: Producer–Consumer implementations
│   │   ├── blockingqueue/               # BlockingQueue-based producer–consumer
│   │   │   ├── BlockingQueueMain.java   # Runs the BlockingQueue simulation
│   │   │   ├── Producer.java            # Producer using ArrayBlockingQueue
│   │   │   └── Consumer.java            # Consumer using ArrayBlockingQueue
│   │   │
│   │   └── waitnotify/                  # wait()/notify()-based producer–consumer
│   │       ├── ManualMain.java          # Runs the wait/notify simulation
│   │       ├── ManualBuffer.java        # One-slot buffer with wait/notify
│   │       ├── ManualProducer.java      # Producer using ManualBuffer
│   │       └── ManualConsumer.java      # Consumer using ManualBuffer
│   │
│   ├── assignment2/                     # Assignment 2: Sales analysis using Streams
│   │   ├── Assignment2Main.java         # Loads CSV and prints full analysis report
│   │   ├── SalesRecord.java             # Data model for one sales entry
│   │   ├── SalesLoader.java             # CSV loader and parser
│   │   ├── SalesAnalyzer.java           # Stream-based analytical operations
│
├── test/                                # JUnit 5 test suite
│   ├── assignment1/
│   │   ├── blockingqueue/BlockingQueueTests.java   # Tests for BlockingQueue version
│   │   └── waitnotify/ManualTests.java             # Tests for wait/notify version
│   │
│   └── assignment2/
│       ├── SalesRecordTests.java        # Tests for data model
│       ├── SalesLoaderTests.java        # Tests for CSV loader
│       └── SalesAnalyzerTests.java      # Tests for analysis logic
│── data/
│   └── sales.csv                        # Curated dataset used for analysis
│
└── README.md                             # Project documentation and instructions
```


Compilation output is written to: `out/`

---

# Assignment 1 - Producer Consumer Pattern

## Overview

Assignment 1 required implementing the producer–consumer pattern to demonstrate thread coordination, safe shared-resource usage, and inter-thread communication.  
I implemented **two versions** to contrast high-level and low-level synchronization approaches:

1. **BlockingQueue implementation** – uses Java’s built-in `ArrayBlockingQueue`  
2. **wait/notify implementation** – uses manual synchronized coordination  

Both versions transfer integers from a producer to a consumer while preserving order and avoiding race conditions.

---

## Setup Instructions (Assignment 1)

### **1. Compile (BlockingQueue version)**

```
javac -d out src/assignment1/blockingqueue/*.java
```
### **Run**
```
java -cp out assignment1.blockingqueue.BlockingQueueMain
```
---

### **2. Compile (wait/notify version)**
```
javac -d out src/assignment1/waitnotify/*.java
```

### **Run**
```
java -cp out assignment1.waitnotify.ManualMain
```

---

## Sample Output (BlockingQueue version)
```
Produced (blocking queue): 1
Consumed (blocking queue): 1
Produced (blocking queue): 2
Consumed (blocking queue): 2
Produced (blocking queue): 3
Consumed (blocking queue): 3
Produced (blocking queue): 4
Consumed (blocking queue): 4
Produced (blocking queue): 5
Consumed (blocking queue): 5

Final Destination List (blocking queue): [1, 2, 3, 4, 5]
BlockingQueue simulation complete.
```
## Sample Output (wait/notify version)
```
Produced (wait/notify): 1
Consumed (wait/notify): 1
Produced (wait/notify): 2
Consumed (wait/notify): 2
Produced (wait/notify): 3
Consumed (wait/notify): 3
Produced (wait/notify): 4
Consumed (wait/notify): 4
Produced (wait/notify): 5
Consumed (wait/notify): 5

Final Destination List (wait/notify): [1, 2, 3, 4, 5]
Manual wait/notify simulation complete.
```

---

# Assignment 2 — Sales Data Analysis (Java Streams)

## Overview

Assignment 2 demonstrates functional programming and use of Java Streams for:

- Aggregation  
- Grouping  
- Filtering  
- Minimum/maximum detection  
- Mapping and reduction  

The program reads a CSV file, maps each row to a `SalesRecord`, and performs multiple analytical operations.

---

## Dataset Justification

The `sales.csv` file is a small, curated dataset representing basic retail transactions.  
Each row records:

- date of sale  
- sales region  
- product category  
- product name  
- sales channel  
- units sold  
- unit price  
- discount percent  

This structure mirrors a typical transactional sales system while remaining small enough for clear, reproducible analysis.

Reasons for using a controlled dataset:

- Deterministic numbers simplify verification  
- Multiple regions and categories enable meaningful grouping  
- Discount values allow for more realistic revenue calculations  
- Clean formatting ensures predictable unit test behavior  

Each row uses the format:
```
date, region, category, product, channel, unitsSold, unitPrice, discountPercent
```

This layout supports all Stream-based calculations in the project while keeping the output readable and focused on the functional programming aspects of the assignment.

---

## Setup Instructions (Assignment 2)

### **Compile**
```
javac -d out src/assignment2/*.java
```
### **Run**
```
java -cp out assignment2.Assignment2Main
```
Ensure the CSV file remains at:
```
src/assignment2/data/sales.csv
```

---

# Sample Output (Assignment 2)

Running the program prints the following analytical report:
```
=== SALES ANALYSIS REPORT ===

Total Revenue: $13372.00
Total Units Sold: 195
Average Unit Price: $213.70

Highest Sale: Laptop (North) - $5400.00
Lowest Sale: Notebook (South) - $160.00

Revenue by Region:
    West: $1957.50
    South: $2232.00
    North: $6202.50
    East: $2980.00

Revenue by Category:
    Office: $1607.50
    Accessories: $1585.00
    Electronics: $10179.50

Revenue by Channel:
    Store: $4567.00
    Online: $8805.00

High Value Sales (> $1000): 5

=== END REPORT ===
```
This satisfies the requirement: **“Results of all analyses printed to the console.”**

---

# Testing Information

All tests use JUnit 5 and live under the `test/` directory.

I wrote tests to explicitly cover every analysis method and core behavior required by the challenge, rather than only testing a subset of the functionality.

## Assignment 1 – Concurrency Tests

Location:

- `test/assignment1/blockingqueue/BlockingQueueTests.java`
- `test/assignment1/waitnotify/ManualTests.java`

These tests focus on the behavioral guarantees of the producer–consumer implementations:

- **Correct transfer of items**  
  - `testAllItemsTransferred` (both BlockingQueue and wait/notify) starts producer and consumer threads, waits for them to finish, and asserts that the destination list exactly matches the source list (same size and same order).

- **Thread termination and deadlock avoidance**  
  - `testThreadsDoNotDeadlock` (both versions) uses a smaller dataset, waits for the producer to finish, gives the consumer time to process, then interrupts the consumer.  
  - Finally, the tests assert that neither thread is still alive, which catches deadlock or incorrect wait/notify usage.

These tests collectively verify that the synchronization logic behaves correctly under the expected workload.

---

## Assignment 2 – Unit Tests for All Analysis Methods

Location:

- `test/assignment2/SalesAnalyzerTests.java`
- `test/assignment2/SalesLoaderTests.java`
- `test/assignment2/SalesRecordTests.java`

### SalesAnalyzer (all analysis methods covered)

Each public analysis method in `SalesAnalyzer` has dedicated tests:

- `calculateTotalRevenue`  
  - Verified in `testTotalRevenue` using a small in-memory dataset with manually computed expected revenue (including discounts).

- `calculateTotalUnitsSold`  
  - Checked in `testTotalUnitsSold`, matching the sum of `unitsSold` across the sample records.

- `calculateAveragePrice`  
  - Validated in `testAveragePrice` using a hand-computed average of the unit prices.

- `findMaxSale` and `findMinSale`  
  - Covered by `testMaxSale` and `testMinSale`, which assert that the records with highest and lowest revenue match the expected products.

- `revenueByRegion`, `revenueByCategory`, `revenueByChannel`  
  - Tested in `testRevenueByRegion`, `testRevenueByCategory`, and `testRevenueByChannel`.  
  - These tests compare the grouped totals against manually calculated sums for each region, category, and channel.

- `countHighValueSales`  
  - Checked in `testHighValueSales`, which confirms the count of records whose revenue exceeds a given threshold.

- **Edge-case behavior with no data**  
  - `testEmptyDatasetHandling` calls all of the above methods on an empty list and asserts:
    - 0 for numeric aggregates,
    - empty maps for groupings,
    - `Optional.empty()` for max/min,
    - and no exceptions thrown.

Together, these tests ensure that **every analysis method** behaves correctly on both normal and boundary inputs.

### SalesLoader (CSV parsing behavior)

- `testValidCsvParsing`  
  - Ensures a well-formed CSV with two rows produces exactly two `SalesRecord` objects with correct fields.

- `testEmptyCsvReturnsEmptyList`  
  - Confirms that a file containing only the header line results in an empty list, not an error.

- `testMalformedCsvThrows`  
  - Verifies that rows with too few fields trigger an `IllegalArgumentException`, matching the validation logic in `parseLine`.

- `testInvalidNumericValuesThrow`  
  - Confirms that non-numeric values for numeric columns result in a `NumberFormatException`.

- `testTrailingCommaLineIsAcceptedByYourLoader`  
  - Documents and tests the current behavior where trailing commas are tolerated by the split logic.

These tests cover the main failure modes of CSV ingestion and prove that invalid input is handled in a controlled way.

### SalesRecord (data model)

- `testConstructorAndGetters`  
  - Confirms that all constructor parameters are stored and returned correctly from their getters.

- `testBasicRevenueMath`  
  - Manually checks the `getRevenue` calculation for a simple case with no discount.

- `testNegativeValuesAreAccepted`  
  - Verifies that the constructor does not reject negative values, documenting current behavior for edge cases.

---

In summary, **all analysis methods in Assignment 2 and all key concurrency behaviors in Assignment 1 are covered by explicit JUnit tests**, as required by the challenge. The tests are written against small, controlled datasets to keep expectations transparent and easy to verify.

---

# Conclusion

This repository provides complete solutions for both assignments in the Intuit Build Challenge.  
Assignment 1 presents two implementations of the producer–consumer pattern to highlight the difference between built-in synchronization utilities and manual coordination with wait/notify.  
Assignment 2 implements a Stream-based analysis pipeline with clear data modeling, structured CSV loading, and well-defined analytical operations.  

Every major component in both assignments is supported by JUnit tests, and all source files include explanatory comments describing design decisions and core logic. The repository also includes dataset justification, setup instructions, and sample program output, ensuring the work is easy to run, review, and evaluate.


