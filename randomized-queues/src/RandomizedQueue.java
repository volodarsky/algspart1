import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private static final String ITEM_IS_NULL = "Item is null";
    private Item[] items;
    private int size;

    public RandomizedQueue() {
        items = (Item[]) new Object[2];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException(ITEM_IS_NULL);
        }
        if (size == items.length) {
            increaseQueueTwice();
        }
        items[size] = item;
        size++;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int index = StdRandom.uniform(size);
        Item item = items[index];
        if (index + 1 < size) {
            System.arraycopy(items, index + 1, items, index, size - index - 1);
        }
        size--;
        if (4 * size <= arraySize()) {
            decreaseQueueTwice();
        }
        return item;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return items[StdRandom.uniform(size)];
    }

    private void increaseQueueTwice() {
        int length = items.length;
        Item[] q = (Item[]) new Object[length * 2];
        System.arraycopy(items, 0, q, 0, length);
        items = q;
    }

    private void decreaseQueueTwice() {
        int length = items.length;
        if (length > 2) {
            Item[] q = (Item[]) new Object[length / 2];
            System.arraycopy(items, 0, q, 0, size());
            items = q;
        }
    }

    private int arraySize() {
        return items.length;
    }

    @Override
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator<>(this);
    }

    private class RandomizedQueueIterator<Item> implements Iterator<Item> {

        private final RandomizedQueue<Item> queue;

        public RandomizedQueueIterator(RandomizedQueue<Item> parentQueue) {
            this.queue = new RandomizedQueue<>();
            this.queue.size = parentQueue.size;
            this.queue.items = (Item[]) new Object[parentQueue.size];
            System.arraycopy(parentQueue.items, 0, this.queue.items, 0, parentQueue.size);
        }

        @Override
        public boolean hasNext() {
            return !queue.isEmpty();
        }

        @Override
        public Item next() {
            if (queue.isEmpty()) {
                throw new NoSuchElementException();
            }
            return queue.dequeue();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {

        RandomizedQueue<String> queue = new RandomizedQueue<>();
        assert queue.isEmpty();
        assert queue.size() == 0;
        assert queue.arraySize() == 2;

        try {
            queue.enqueue(null);
        } catch (Exception e) {
            assert NullPointerException.class.isAssignableFrom(e.getClass());
            assert ITEM_IS_NULL.equals(e.getMessage());
            assert queue.isEmpty();
            assert queue.size() == 0;
            assert queue.arraySize() == 2;
        }

        try {
            queue.dequeue();
        } catch (Exception e) {
            NoSuchElementException.class.isAssignableFrom(e.getClass());
        }

        try {
            queue.sample();
        } catch (Exception e) {
            NoSuchElementException.class.isAssignableFrom(e.getClass());
        }

        queue.enqueue("one");
        assert !queue.isEmpty();
        assert queue.size() == 1;
        assert queue.arraySize() == 2;

        queue.enqueue("two");
        assert !queue.isEmpty();
        assert queue.size() == 2;
        assert queue.arraySize() == 2;

        queue.enqueue("three");
        assert !queue.isEmpty();
        assert queue.size() == 3;
        assert queue.arraySize() == 4;

        queue.enqueue("four");
        assert !queue.isEmpty();
        assert queue.size() == 4;
        assert queue.arraySize() == 4;

        queue.enqueue("five");
        assert !queue.isEmpty();
        assert queue.size() == 5;
        assert queue.arraySize() == 8;

        for (int i = 0; i < queue.size(); i++) {
            System.out.println(queue.sample());
        }
        assert !queue.isEmpty();
        assert queue.size() == 5;
        assert queue.arraySize() == 8;

        queue.dequeue();
        assert !queue.isEmpty();
        assert queue.size() == 4;
        assert queue.arraySize() == 8;

        queue.dequeue();
        queue.sample();
        assert !queue.isEmpty();
        assert queue.size() == 3;
        assert queue.arraySize() == 8;

        queue.sample();
        queue.dequeue();
        assert !queue.isEmpty();
        assert queue.size() == 2;
        assert queue.arraySize() == 4;

        queue.sample();
        queue.dequeue();
        assert !queue.isEmpty();
        assert queue.size() == 1;
        assert queue.arraySize() == 2;

        queue.dequeue();
        assert queue.isEmpty();
        assert queue.size() == 0;
        assert queue.arraySize() == 2;

        queue.enqueue("one");
        assert !queue.isEmpty();
        assert queue.size() == 1;
        assert queue.arraySize() == 2;

        queue.enqueue("two");
        assert !queue.isEmpty();
        assert queue.size() == 2;
        assert queue.arraySize() == 2;

        queue.enqueue("three");
        assert !queue.isEmpty();
        assert queue.size() == 3;
        assert queue.arraySize() == 4;

    }

}
