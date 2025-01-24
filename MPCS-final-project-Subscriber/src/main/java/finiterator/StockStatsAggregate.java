//package finiterator;
//
//import java.util.Map;
//
//import fininstruments.*;
//import stockstats.StockStats;
//
//// Concrete Aggregate i.e. a portfolio
//public class StockStatsAggregate implements Aggregate<StockStats> {
//    private Map<String, StockStats> stockStatsMap;   // example children is a LinkedList
//
//    // constructor acquiring the collection
//    public StockStatsAggregate(Map<String, StockStats> stockStatsMap) {
//        this.stockStatsMap = stockStatsMap;
//    }
//
//    @Override
//    public Iterator<StockStats> createIterator() {
//        return new CustomIterator(stockStatsMap);      // creating an Iterator, passing down CustomAggregate object
//    }
//}



package finiterator;

import stockstats.StockStats;

// Concrete Aggregate i.e. a portfolio
public class StockStatsAggregate implements Aggregate<StockStats> {
    private LinkedList<StockStats> children;   // example children is a LinkedList

    // constructor returning ArrayList items
    public StockStatsAggregate(LinkedList<StockStats> children) {
        this.children = children;
    }

    @Override
    public Iterator<StockStats> createIterator() {
        return new CustomIterator(this);      // creating an Iterator, passing down CustomAggregate obejct
    }

    public int getCount() {
        return children.size();
    }

    public StockStats get(int i) {
        return children.get(i);
    }
}