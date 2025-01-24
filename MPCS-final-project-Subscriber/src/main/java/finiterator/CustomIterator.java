//package finiterator;
//
//import java.util.Map;
//import stockstats.StockStats;
//
//// Concrete Iterator
//public class CustomIterator implements finiterator.Iterator<StockStats> {
//    private java.util.Iterator<StockStats> valuesIterator;
//
//    // constructor
//    public CustomIterator(Map<String, StockStats> stockStatsMap) {
//        this.valuesIterator = stockStatsMap.values().iterator();
//    }
//
//    @Override
//    public boolean hasNext() {
//        return valuesIterator.hasNext();
//    }
//
//    @Override
//    public StockStats next() {
//    	return valuesIterator.next();
//    }
//}


package finiterator;

import java.util.NoSuchElementException;
import stockstats.StockStats;

// Concrete Iterator
public class CustomIterator implements Iterator<StockStats> {
    private StockStatsAggregate aggregate;        // my collection ie. arrayList
    private int currentIndex = 0;

    // constructor
    public CustomIterator(StockStatsAggregate aggregate) {
        this.aggregate = aggregate;
    }

    @Override
    public boolean hasNext() {
        return currentIndex < aggregate.getCount();
    }

    @Override
    public StockStats next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return aggregate.get(currentIndex++);
    }
}