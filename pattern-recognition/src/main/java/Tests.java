import java.util.Iterator;

import edu.princeton.cs.algs4.MaxPQ;

/**
 * Created by vladimir.romanov on 25.07.2015.
 */
public class Tests {

    public static void main(String[] args) {
        MaxPQ<Integer> pq = new MaxPQ<>();
        //98 95 69 80 58 35 21 53 37 50
        pq.insert(98);
        pq.insert(95);
        pq.insert(69);
        pq.insert(80);
        pq.insert(58);
        pq.insert(35);
        pq.insert(21);
        pq.insert(53);
        pq.insert(37);
        pq.insert(50);

        Iterator<Integer> iterator = pq.iterator();
        while (iterator.hasNext()) {
            Integer next = iterator.next();
            System.out.print(next + " ");
        }
        System.out.println();


        //BST<Comparable, Object> bst = new BST<>();

    }
}
