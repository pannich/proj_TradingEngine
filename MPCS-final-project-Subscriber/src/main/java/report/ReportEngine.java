// 3. Singleton Design Pattern
// https://www.geeksforgeeks.org/singleton-class-java/#
package report;

import finiterator.LinkedList;
import finiterator.StockStatsAggregate;
import stockstats.StockStats;
import stockstats.StockStatsMap;
import tradeengine.TradeEngine;

// Helper class
public class ReportEngine {

    private static ReportEngine instance;
 
    // Declaring a variable of type String
    public String s;
 
    // Constructor
    // Here we will be creating private constructor
    // restricted to this class itself
    private ReportEngine() {}
 
    // Static method to create instance of Singleton class if it hasn't already 
    public static synchronized ReportEngine getInstance()
    {
        if (instance == null)
        	instance = new ReportEngine();
 
        return instance;
    }
    
    public void printReport(TradeEngine engine) {
    	// get portfolio and iterate thru local stats
    	StockStatsMap localStats = engine.getLocalStats();
//    	Map<String, StockStats> stockStatsMap = localStats.getStatsMap();
    	LinkedList<StockStats> stockStatsMap = localStats.getStatsMap();
    	
    	StockStatsAggregate aggregate = new StockStatsAggregate(stockStatsMap);
    	finiterator.Iterator<StockStats> iterator = aggregate.createIterator();
    	
    	System.out.printf("==== %s Trade Engine Report ====\n", engine.getName());
    	
    	
    	while (iterator.hasNext()) {
    	    StockStats stockStats = iterator.next();
    	    String stockSummary = generateStockStatsSummary(stockStats);
    	    System.out.println(stockSummary);
    	}
    }
    
    public String generateStockStatsSummary(StockStats stockStats) {
        StringBuilder sb = new StringBuilder();

        if (stockStats.getStock() != null && !stockStats.getStock().isEmpty()) {
            sb.append("Stock: ").append(stockStats.getStock()).append("\n");

            if (!Double.isNaN(stockStats.getMinPrice())) {
                sb.append("Min Price: ").append(stockStats.getMinPrice()).append("\n");
            }
            if (!Double.isNaN(stockStats.getMaxPrice())) {
                sb.append("Max Price: ").append(stockStats.getMaxPrice()).append("\n");
            }
            if (!Double.isNaN(stockStats.getAveragePrice())) {
                sb.append("Average Price: ").append(stockStats.getAveragePrice()).append("\n");
            }
            if (!Double.isNaN(stockStats.getStandardDeviation())) {
                sb.append("Standard Deviation: ").append(stockStats.getStandardDeviation()).append("\n");
            }
            if (!Double.isNaN(stockStats.getVariance())) {
                sb.append("Variance: ").append(stockStats.getVariance()).append("\n");
            }

        }

        return sb.toString();
    }

    
}
