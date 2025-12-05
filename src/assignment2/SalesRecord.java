package assignment2;

/**
 * Represents a single sales transaction loaded from the CSV dataset.
 * Each record contains product and sales information along with
 * discount values. A computed revenue property is provided based
 * on units sold, price, and discount percentage.
 */

public class SalesRecord {

    private final String date;
    private final String region;
    private final String category;
    private final String product;
    private final String channel;
    private final int unitsSold;
    private final double unitPrice;
    private final double discountPercent;

    public SalesRecord(
            String date,
            String region,
            String category,
            String product,
            String channel,
            int unitsSold,
            double unitPrice,
            double discountPercent
    ) {
        this.date = date;
        this.region = region;
        this.category = category;
        this.product = product;
        this.channel = channel;
        this.unitsSold = unitsSold;
        this.unitPrice = unitPrice;
        this.discountPercent = discountPercent;
    }

    // Simple getters
    public String getDate() { return date; }
    public String getRegion() { return region; }
    public String getCategory() { return category; }
    public String getProduct() { return product; }
    public String getChannel() { return channel; }
    public int getUnitsSold() { return unitsSold; }
    public double getUnitPrice() { return unitPrice; }
    public double getDiscountPercent() { return discountPercent; }

    // Computes final revenue after discount
    public double getRevenue() {
        double multiplier = 1 - (discountPercent / 100.0);
        return unitsSold * unitPrice * multiplier;
    }

    @Override
    public String toString() {
        return String.format(
                "SalesRecord[%s, %s, %s, %s, %s, units=%d, price=%.2f, discount=%.2f]",
                date, region, category, product, channel,
                unitsSold, unitPrice, discountPercent
        );
    }
}