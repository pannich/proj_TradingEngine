package finiterator;

public interface Aggregate<T> {
    Iterator<T> createIterator();
}