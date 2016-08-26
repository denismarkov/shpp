import java.util.Comparator;

/**
 * Created by Denis on 18.07.2016.
 * Compare Node frequency for right put it to priority que
 */
public class nodeComparator implements Comparator<Node> {
    @Override
    public int compare(Node n1, Node n2) {
        return n1.frequency - n2.frequency;
    }
}
