package camelinaction;

import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import stockstats.StockStats;

public class ConsumerProcessor implements Processor {
	private Map<String, StockStats> stockStatsMap;
	
	public ConsumerProcessor(Map<String, StockStats> stockStatsMap) {
        this.stockStatsMap = stockStatsMap;
    }
	
	// 3. Message Translator + 6. Content Enricher (updateStats)
	@Override
    public void process(Exchange exchange) throws Exception {
		// Extract stock from file content
	    String[] contentArray = exchange.getIn().getBody(String.class)
	    		.substring(1, exchange.getIn().getBody(String.class).length() - 1)	//remove "[" "]"
	    		.split("\t");
	    
	    String stockName = contentArray[0]; 
	    double bidPrice = Double.parseDouble(contentArray[1]);
	    
	    // Update stock stats
	    StockStats stats = stockStatsMap.computeIfAbsent(stockName, k -> new StockStats());
        stats.updateStats(bidPrice);
	    
	
	    String json = String.format("{\"stock\": \"%s\", "
	    		+ "\"minPrice\": %.2f, "
	    		+ "\"maxPrice\": %.2f, "
	    		+ "\"averagePrice\": %.2f, "
	    		+ "\"standardDeviation\": %.2f, "
	    		+ "\"variance\": %2f}",
	            stockName, stats.getMinPrice(), stats.getMaxPrice(), stats.getAveragePrice(), stats.getStandardDeviation(), stats.getVariance());
	
	    exchange.getIn().setBody(json);
	}
}