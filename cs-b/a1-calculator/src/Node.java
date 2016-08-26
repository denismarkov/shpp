/**
 * Created by Denis on 23.06.2016.
 * Contain temp result of calculate and current expression value
 */
class Node {
    double tempResult;
    String restExpression;

    Node(double tempResult, String restExpression) {
        this.tempResult = tempResult;
        this.restExpression = restExpression;
    }
}
