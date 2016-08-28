package com.dmarkov.shpp.csb.task1.Main;

/**
 * Created by Denis on 23.06.2016.
 * Parse expression and calculate it in right math order procedures,
 * start at operation with height priority - parse num, calculate function, etc
 */
class CalculateExpression {
    private String expression = "";
    private MyHashMap<String, Double> variables;

    CalculateExpression(String expression, MyHashMap<String, Double> variables) {
        this.expression = expression;
        this.variables = variables;
    }

    /*
    Call calculatePlusMinus - method with lower priority of math operation. This method before
    calculate + and - call calculateMulDiv method< that found and calculate more priority operation and
    in result, first of all calculate numbers, then functions...
     */
    double calculate() throws Exception {
//        Remove all spaces from expression to correct parse
        expression = expression.replaceAll(" ", "");
        Node result = calculatePlusMinus(expression);
        if (!result.restExpression.isEmpty()) {
            // if cant full parse expression - print exception
            throw new Exception("Error: can't full parse rest: " + result.restExpression);
        }
        return result.tempResult;
    }

    /*
    If not operation with height priority in expression - calculate + and -
    @param String expression
    @return Node with result and rest of expression (or null, if parse expression success)
     */
    private Node calculatePlusMinus(String expression) throws Exception {
        Node current = calculateMulDiv(expression);
        double tempResult = current.tempResult;

        while (current.restExpression.length() > 0) {
            if (!(current.restExpression.charAt(0) == '+' || current.restExpression.charAt(0) == '-')) break;

            char sign = current.restExpression.charAt(0);
            String next = current.restExpression.substring(1);

            current = calculateMulDiv(next);
            if (sign == '+') {
                tempResult += current.tempResult;
            } else {
                tempResult -= current.tempResult;
            }
        }
        return new Node(tempResult, current.restExpression);
    }

    /* If not operation with height priority in expression - calculate * and /
    @param String expression
    @return Node with result and rest of expression
     */
    private Node calculateMulDiv(String expression) throws Exception {
        Node current = calculateExpressionInBracket(expression);
        double tempResult = current.tempResult;
        while (true) {
            if (current.restExpression.length() == 0) {
                return current;
            }
            char sign = current.restExpression.charAt(0);
            if ((sign != '*' && sign != '/')) return current;
            String next = current.restExpression.substring(1);
            Node rightExpressionPart = calculateExpressionInBracket(next);

            if (sign == '*') {
                tempResult *= rightExpressionPart.tempResult;
            } else {
                tempResult /= rightExpressionPart.tempResult;
            }

            current = new Node(tempResult, rightExpressionPart.restExpression);
        }

    }

    /* Remove bracket in expression and calculate expression between it
    @param String expression
    @return Node with result and rest of expression
     */
    private Node calculateExpressionInBracket(String expression) throws Exception {
        if (expression.charAt(0) == '(') {
            Node n = calculatePlusMinus(expression.substring(1));
            if (!n.restExpression.isEmpty() && n.restExpression.charAt(0) == ')') {
                if (n.restExpression.length() < 2) {
                    n.restExpression = "";
                } else {
                    n.restExpression = n.restExpression.substring(1);
                }
                if (!n.restExpression.isEmpty() && n.restExpression.charAt(0) == '^') {
                    String powExpression = Double.toString(n.tempResult) + n.restExpression;
                    String powResult = processPowerFunction(powExpression, powExpression.indexOf('^'));
                    return calculatePlusMinus(powResult);
                }
            } else {
                throw new Exception("Error: not close bracket");
            }
            return n;
        }
        return calculateFunctionsAndVariables(expression);
    }

    /* If not operation with height priority in expression - calculate function and get variables value
    @param String expression
    @return Node with result and rest of expression
     */
    private Node calculateFunctionsAndVariables(String expression) throws Exception {
        int currentCharIndex = 0;
        while (currentCharIndex < expression.length()) {
            char currentChar = expression.charAt(currentCharIndex);
            if (currentChar == '+' || currentChar == '-' || currentChar == ')') {
                break;
            }
            if (currentChar == '^') {
                expression = processPowerFunction(expression, currentCharIndex);
                break;
            }
            currentCharIndex++;
        }
        String function = "";
        int i = 0;
        while (i < expression.length() && (Character.isLetter(expression.charAt(i)) || (Character.isDigit(expression.charAt(i)) && i > 0))) {
            function += expression.charAt(i);
            i++;
        }
        if (!function.isEmpty()) {
            if (expression.length() > i && expression.charAt(i) == '(') {
                Node n = calculateExpressionInBracket(expression.substring(function.length()));
                return processFunction(function, n);
            } else {
                return new Node(getVariable(function), expression.substring(function.length()));
            }
        }
        return processNum(expression);
    }

    /*
    Get in param expression and index of '^' in it, find left and right part of power expression, calculate them,
    calculate power of left part to right part and replace part of expression with power to result of power,
    return expression string with calculated power part
    @param String expression
    @param int currentCharIndex - index of '^'
    @return String expression - expression whit calculated power
     */
    private String processPowerFunction(String expression, int currentCharIndex) throws Exception {
        int bracketCount = 0;
        char currentChar;
        int leftPartStartIndex = currentCharIndex - 1;
        while (leftPartStartIndex > 0) {
            currentChar = expression.charAt(leftPartStartIndex);
            if (currentChar == ')') {
                bracketCount++;
            }
            if (currentChar == '(') {
                bracketCount--;
            }
            if (bracketCount <= 0 && !(Character.isDigit(currentChar) || Character.isLetter(currentChar) || currentChar == '.')) {
                leftPartStartIndex++;
                break;
            }
            leftPartStartIndex--;
        }
        String beforeLeftPartExpression = "";
        String leftPartExpression;

        if (!(leftPartStartIndex == 0)) {
            beforeLeftPartExpression = expression.substring(0, leftPartStartIndex);
            leftPartExpression = expression.substring(leftPartStartIndex, currentCharIndex);
        } else {
            beforeLeftPartExpression = "";
            leftPartExpression = expression.substring(0, currentCharIndex);
        }
        Node left;
        if (leftPartExpression.charAt(0) == '(') {
            left = calculateExpressionInBracket(leftPartExpression);
        } else {
            left = calculateMulDiv(leftPartExpression);
        }
        int rightPartEndIndex = currentCharIndex + 1;
        while (rightPartEndIndex < expression.length()) {
            currentChar = expression.charAt(rightPartEndIndex);
            if (currentChar == '(') {
                bracketCount++;
            }
            if (currentChar == ')') {
                bracketCount--;
            }
            if (bracketCount <= 0 && !(Character.isDigit(currentChar) || Character.isLetter(currentChar) || currentChar == '.')) {
                rightPartEndIndex--;
                break;
            }
            rightPartEndIndex++;
        }
        String afterRightPartExpression = "";
        if (rightPartEndIndex != expression.length()) {
            afterRightPartExpression = expression.substring(rightPartEndIndex + 1);
        }
        String rightPartExpression;
        if ((currentCharIndex + 1) != rightPartEndIndex) {
            rightPartExpression = expression.substring(currentCharIndex + 1, rightPartEndIndex);
        } else {
            rightPartExpression = expression.charAt(currentCharIndex + 1) + "";
        }
        Node right;
        if (rightPartExpression.charAt(0) == '(') {
            right = calculateExpressionInBracket(rightPartExpression);
        } else {
            right = calculateMulDiv(rightPartExpression);
        }
        String powResult = Double.toString(Math.pow(left.tempResult, right.tempResult));
        return beforeLeftPartExpression + powResult + afterRightPartExpression;
    }

    /* Calculate function
    @param String function
    @param Node n - current node with rest of expression and temp result
    @return Node with result and rest of expression
     */
    private Node processFunction(String function, Node n) throws Exception {
        if (function.equals("sqrt")) {
            return new Node(Math.sqrt(n.tempResult), n.restExpression);
        }
        if (function.equals("sin")) {
            return new Node(Math.sin(Math.toRadians(n.tempResult)), n.restExpression);
        }
        if (function.equals("cos")) {
            return new Node(Math.cos(Math.toRadians(n.tempResult)), n.restExpression);
        }
        if (function.equals("tan")) {
            return new Node(Math.tan(Math.toRadians(n.tempResult)), n.restExpression);
        } else {
            throw new Exception("function \"" + function + "\" is not defined");
        }
    }

    /* Get variable value
    @param String variable name
    @return Node with result and rest of expression
     */
    private double getVariable(String variableName) throws Exception {
        if (!variables.containsKey(variableName)) {
            throw new Exception("Error: Try to get unexpected variable \"" + variableName + "\"");
        }
        return variables.get(variableName);
    }

    /* Get num values and work with negative numbers
    @param String expression
    @return Node with result and rest of expression
     */
    private Node processNum(String expression) throws Exception {
        int i = 0;
        int dotCount = 0;
        boolean negative = false;
        if (expression.charAt(0) == '-') {
            negative = true;
            expression = expression.substring(1);
        }
        while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
            if (expression.charAt(i) == '.' && ++dotCount > 1) {
                throw new Exception("not valid number '" + expression.substring(0, i + 1) + "'");
            }
            i++;
        }
        if (i == 0) {
            throw new Exception("can't get valid number in '" + expression + "'");
        }

        double num = Double.parseDouble(expression.substring(0, i));
        if (negative) num = -num;
        expression = expression.substring(i);

        return new Node(num, expression);
    }

}
