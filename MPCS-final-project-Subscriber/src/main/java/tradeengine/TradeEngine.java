// 2. Template Design pattern
package tradeengine;

import org.apache.camel.CamelContext;

import com.fasterxml.jackson.databind.JsonNode;

import stockstats.StockStats;
import stockstats.StockStatsMap;
import stockstrategy.StockStrategy;

public abstract class TradeEngine {
	private String name;
	protected CamelContext context;
	public StockStatsMap localStats;
	public StockStrategy stockstrategy;

    public TradeEngine(String name, CamelContext context, StockStrategy stockstrategy){
    	this.name = name;
    	this.context = context;
    	this.localStats = new StockStatsMap();
    	this.stockstrategy = stockstrategy;
    }  
    
    public void printDetails() {
    	System.out.println("This is TradeEngine Name: " + name);
    }
    
    public StockStatsMap getLocalStats() {
    	return localStats;
    }
    
    public String getName() {
    	return name;
    }
    
    // ---- strategy ----
    public StockStats updateStockStats(JsonNode statsNode) {
    	return stockstrategy.updateStats(statsNode);
    }
    
    // ---- abstract method ----
    public abstract void setupRoutes();
        
}