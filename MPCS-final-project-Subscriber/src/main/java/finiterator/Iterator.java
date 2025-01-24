// 4. Iterator Design Pattern
package finiterator;

public interface Iterator<T> {
    boolean hasNext();
    T next();
}