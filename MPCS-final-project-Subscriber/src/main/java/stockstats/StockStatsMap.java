package stockstats;

import finiterator.LinkedList;
//import finiterator.StockStatsAggregate;

// To store all the stockStats in each TradeEngine
public class StockStatsMap {
    private LinkedList<StockStats> stockStatsMap = new LinkedList<StockStats>();

    public void updateStats(StockStats stockStats) {
    	stockStatsMap.add(stockStats);
    }
    
    public LinkedList<StockStats> getStatsMap() {
    	return stockStatsMap;
    }
 
}
