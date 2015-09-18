import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private static final String DEQUE_IS_EMPTY = "Deque is empty";
    private static final String ZERO = "0";
    private static final String ONE = "1";
    private static final String TWO = "2";
    private static final String THREE = "3";
    private static final String ITEM_IS_NULL = "Item is null";

    //head of the queue - first
    private Node<Item> head;
    // tail of the queue - last;
    private Node<Item> tail;
    private int size;

    public Deque() {
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if(item == null){
            throw new NullPointerException(ITEM_IS_NULL);
        }
        if (isEmpty()) {
            head = tail = new Node<>(item, null, null);
        } else {
            head.next = new Node<>(item, head, null);
            head = head.next;
        }
        size++;

    }

    public void addLast(Item item) {
        if(item == null){
            throw new NullPointerException(ITEM_IS_NULL);
        }
        if (isEmpty()) {
            head = tail = new Node<>(item, null, null);
        } else {
            Node<Item> old = tail;
            this.tail = new Node<>(item, null, old);
            old.prev = tail;
        }
        size++;
    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException(DEQUE_IS_EMPTY);
        }
        Item item = head.item;
        if (head.prev != null) {
            head.prev.next = null;
        }
        head = head.prev;
        size--;
        return item;
    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException(DEQUE_IS_EMPTY);
        }
        Item item = tail.item;
        if (tail.next != null){
            tail.next.prev = null;
        }
        tail = tail.next;
        size--;
        return item;

    }

    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator<>(head);
    }

    private class Node<Item> {

        final Item item;
        Node<Item> prev;
        Node<Item> next;

        public Node(Item item, Node<Item> prev, Node<Item> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    private class DequeIterator<Item> implements Iterator<Item> {

        Node<Item> current;

        public DequeIterator(Node<Item> head) {
            current = head;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (current == null) {
                throw new NoSuchElementException(DEQUE_IS_EMPTY);
            }
            Item item = current.item;
            current = current.prev;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {

        Deque<String> stringDeque = new Deque<>();
        assert stringDeque.isEmpty();

        try {
            stringDeque.removeFirst();
        } catch (Exception e) {
            assert NoSuchElementException.class.isAssignableFrom(e.getClass());
            assert DEQUE_IS_EMPTY.equals(e.getMessage());
        }

        try {
            stringDeque.removeLast();
        } catch (Exception e) {
            assert NoSuchElementException.class.isAssignableFrom(e.getClass());
            assert DEQUE_IS_EMPTY.equals(e.getMessage());
        }

        try {
            stringDeque.addLast(null);
        } catch (Exception e) {
            assert NullPointerException.class.isAssignableFrom(e.getClass());
            assert ITEM_IS_NULL.equals(e.getMessage());
        }

        try {
            stringDeque.addFirst(null);
        } catch (Exception e) {
            assert NullPointerException.class.isAssignableFrom(e.getClass());
            assert ITEM_IS_NULL.equals(e.getMessage());
        }

        stringDeque.addLast(ZERO);
        assert stringDeque.iterator().hasNext();
        stringDeque.removeLast();
        assert !stringDeque.iterator().hasNext();
        assert stringDeque.size() == 0;

        stringDeque.addFirst(ZERO);
        assert stringDeque.size() == 1;
        String last = stringDeque.removeLast();
        assert ZERO.equals(last);
        assert stringDeque.isEmpty();

        stringDeque.addLast(ZERO);
        assert stringDeque.size() == 1;
        String first = stringDeque.removeFirst();
        assert ZERO.equals(first);
        assert stringDeque.isEmpty();

        stringDeque.addLast(TWO);
        stringDeque.addLast(THREE);
        stringDeque.addFirst(ONE);
        stringDeque.addFirst(ZERO);

        assert stringDeque.size() == 4;

        Iterator<String> iterator = stringDeque.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            System.out.println(next);
        }

        try {
            iterator.next();
        } catch (Exception e) {
            assert NoSuchElementException.class.isAssignableFrom(e.getClass());
            assert DEQUE_IS_EMPTY.equals(e.getMessage());
        }

        try {
            iterator.remove();
        } catch (Exception e) {
            assert UnsupportedOperationException.class.isAssignableFrom(e.getClass());
        }
    }
}
