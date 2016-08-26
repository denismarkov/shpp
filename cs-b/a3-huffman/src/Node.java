import java.io.Serializable;

/**
 * Created by Denis on 05.08.2016.
 */
public class Node implements Comparable<Node>, Serializable {
    transient int frequency;
    byte key;
    Node child1;
    Node child2;

    public Node(int frequency, byte key, Node child1, Node child2) {
        this.frequency = frequency;
        this.key = key;
        this.child1 = child1;
        this.child2 = child2;
    }

    public Node(int frequency, Node child1, Node child2) {
        this.frequency = frequency;
        this.child1 = child1;
        this.child2 = child2;
    }

    @Override
    public int compareTo(Node n) {
        return Integer.compare(this.frequency, n.frequency);
    }
}
