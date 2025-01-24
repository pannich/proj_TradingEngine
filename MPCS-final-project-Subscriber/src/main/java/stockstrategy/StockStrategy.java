// 1. Strategy Design pattern
package stockstrategy;

import com.fasterxml.jackson.databind.JsonNode;

import stockstats.StockStats;

public interface StockStrategy {
	StockStats updateStats(JsonNode statsNode);	// rootNode contains stats : stock, min, max, etc
}