package stockstrategy;

import com.fasterxml.jackson.databind.JsonNode;

import stockstats.StockStats;

public class PremiumStats implements StockStrategy {
	@Override
	public StockStats updateStats(JsonNode statsNode) {
		StockStats stockStats = new StockStats();
      
        stockStats.setStock(statsNode.get("stock").asText());
        stockStats.setMinPrice(statsNode.get("minPrice").asDouble());
        stockStats.setMaxPrice(statsNode.get("maxPrice").asDouble());
        stockStats.setAveragePrice(statsNode.get("averagePrice").asDouble());
        stockStats.setStandardDeviation(statsNode.get("standardDeviation").asDouble());
        stockStats.setVariance(statsNode.get("variance").asDouble());
        
        return stockStats;
	}
}