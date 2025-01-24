package stockstats; 

// Take in bidPrice and update the stockstats accordingly
public class StockStats {
    private double minPrice = Double.MAX_VALUE;
    private double maxPrice = Double.MIN_VALUE;
    private double totalPrice = 0;
    private double totalSquaredPrice = 0;
    private int count = 0;

    public void updateStats(double price) {
        if (price < minPrice) minPrice = price;
        if (price > maxPrice) maxPrice = price;
        totalPrice += price;
        totalSquaredPrice += price * price;  // For Variance
        count++;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public double getAveragePrice() {
        return count > 0 ? totalPrice / count : 0;
    }
    
    public double getVariance() {
        if (count > 1) {
            double mean = getAveragePrice();
            return (totalSquaredPrice - 2 * mean * totalPrice + count * mean * mean) / count;
        } else {
            return 0;  // Variance is not defined for one or zero samples
        }
    }

    public double getStandardDeviation() {
    	double sd = Math.sqrt(getVariance());
        return Double.isNaN(sd) ? null : sd;  // Return null if NaN
    }
}
