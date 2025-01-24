package stockstats;

public class StockStats {
    private String stock;
    private double minPrice = Double.NaN;
    private double maxPrice = Double.NaN;
    private double averagePrice = Double.NaN;
    private double standardDeviation = Double.NaN;
    private double variance = Double.NaN;

    // Getters and setters
    public String getStock() { return stock; }
    public void setStock(String stock) { this.stock = stock; }

    public double getMinPrice() { return minPrice; }
    public void setMinPrice(double minPrice) { this.minPrice = minPrice; }

    public double getMaxPrice() { return maxPrice; }
    public void setMaxPrice(double maxPrice) { this.maxPrice = maxPrice; }

    public double getAveragePrice() { return averagePrice; }
    public void setAveragePrice(double averagePrice) { this.averagePrice = averagePrice; }

    public double getStandardDeviation() { return standardDeviation; }
    public void setStandardDeviation(double standardDeviation) { this.standardDeviation = standardDeviation; }

    public double getVariance() { return variance; }
    public void setVariance(double variance) { this.variance = variance; }
}
