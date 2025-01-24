package finiterator;

import stockstats.StockStats;

public class LinkedList<T extends StockStats> {
    private Node<T> head; // head of list
    private int size;

    /* Linked list Node*/
    private static class Node<T> {
        T data;
        Node<T> next;  // default is null

        Node(T data) {
          this.data = data;
          this.next = null;
          }
    }

    // Constructor
    public LinkedList() {
        this.head = null;
        this.size = 0;
    }

    public void add(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
            size++;
        } else {
            Node<T> current = head;
            boolean updated = false;
            
            // Traverse to check if name exists
            while (current != null) {
            	if (current.data.getStock().equals(data.getStock())) {
            		current.data = data;		// update just the data
            		updated = true;
            		break;
            	}
                current = current.next;
            }
            // If no matching node is found, append a new node at the end
            if (!updated) {
                current = head;
                while (current.next != null) {
                    current = current.next;
                }
                current.next = new Node<>(data);
                size++;
                   
            }
        }
    }

    // Make LL has get method so that its generic, get item i
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }



    public boolean remove(T data) {
    if (head == null) {
        return false; // List is empty, nothing to remove
    }

    if (head.data.equals(data)) {
        head = head.next; // Remove the head of the list
        size--;
        return true;
    }

    Node<T> current = head;
    Node<T> previous = null;
    while (current != null && !current.data.equals(data)) {
        previous = current;
        current = current.next;
    }

    if (current == null) {
        return false; // Data not found in the list
    }

    // Remove the current node
    previous.next = current.next;
    size--;
    return true;
}

    public int size() {
        return size;
    }
}