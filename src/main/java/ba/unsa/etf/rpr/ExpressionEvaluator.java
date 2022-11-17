package ba.unsa.etf.rpr;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Class for computing the value of arithmetic expressions using Dijkstra's Algorithm for expression evaluation.
 * It is assumed that all expressions will be space separated for easier parsing of the input expression.
 * Support for operator priority is not included.
 * @author Aldin Islamagic
 * Version: 1.1
 */

public class ExpressionEvaluator {

    private final Stack<Double> operands;
    private final Stack<String> operators;

    /**
     * Constructor that creates an empty object
     */
    public ExpressionEvaluator() {
        this.operands = new Stack<>();
        this.operators = new Stack<>();
    }


    /**
     * Private class that checks if the parameter is an operator
     * @param s Given operator
     * @return Returns true if String s is an operator, otherwise it returns false.
     */
    private boolean isOperator(String s){
        return s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/");
    }

    /**
     * Private class that calculates the value of the expression
     * @param a First operand
     * @param op Type of operation
     * @param b Second operand
     * @return Returns the value of "a op b", throws an exception if "b = 0" or "op" is not a valid operator.
     */
    private Double applyOperatorToOperands(Double a, String op, Double b){
        switch (op) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            case "/":
                if (b.equals(0.)) throw new RuntimeException("Division with 0!");
                return a / b;
        }
        throw new RuntimeException("Illegal operator!");
    }


    /**
     * Class that converts an arithmetic expression (a string of characters) to the value that it represent.
     * @param expression Arithmetic expression that gets converted to wanted value.
     * @return Return the value of the given expression.
     */

    public Double evaluate(String expression){
        if(expression.isEmpty()) throw new RuntimeException("Expression is empty!");
        if(expression.charAt(0) != '(') throw new RuntimeException("Expression doesn't start with a left bracket");
        List<Integer> numOfOperInClosedBracketcs = new ArrayList<>();
        int currentBracket = -1, opened = 0, closed = 0;
        int numOfOperators = 0, numOfOperands = 0, numOfSpaces = 0;
        boolean isOpen = false, isSqrt = false;
        for (int i = 0; i < expression.length(); i++) {
            if (isOperator(String.valueOf(expression.charAt(i))) && String.valueOf(expression.charAt(i+1)).equals(" ")) {
                if(isOpen) numOfOperInClosedBracketcs.set(currentBracket, numOfOperInClosedBracketcs.get(currentBracket) + 1);
                operators.push(String.valueOf(expression.charAt(i)));
                numOfOperators = numOfOperators + 1;
            } else if (String.valueOf(expression.charAt(i)).equals(")")) {
                if(currentBracket == -1) {
                    closed = closed + 1;
                    break;
                }
                if(!isSqrt) {
                    Integer temp = numOfOperInClosedBracketcs.get(currentBracket);
                    while (temp != 0 && operands.size() > 1) {
                        Double a = operands.pop();
                        Double b = operands.pop();
                        operands.push(applyOperatorToOperands(b, operators.pop(), a));
                        temp--;
                    }
                    closed = closed + 1;
                    numOfOperInClosedBracketcs.remove(currentBracket);
                    currentBracket = currentBracket - 1;
                }else{
                    Double a = operands.pop();
                    operands.push(Math.sqrt(a));
                    isSqrt = false;
                    operators.pop();
                }
            }
            else if(String.valueOf(expression.charAt(i)).equals("(")){
                if(!isSqrt) {
                    isOpen = true;
                    currentBracket = currentBracket + 1;
                    numOfOperInClosedBracketcs.add(0);
                    opened = opened + 1;
                }
            }else if(expression.startsWith("sqrt", i)){
                operators.push("sqrt");
                isSqrt = true;
                i = i + 4;
                numOfSpaces = numOfSpaces - 2;
            }else if(!String.valueOf(expression.charAt(i)).equals(" ")) {
                StringBuilder num = new StringBuilder();
                while (!String.valueOf(expression.charAt(i)).equals(" ")) {
                    num.append(expression.charAt(i));
                    i = i + 1;
                }
                operands.push(Double.parseDouble(String.valueOf(num)));
                numOfOperands = numOfOperands + 1;
            }
            if(String.valueOf(expression.charAt(i)).equals(" ")) numOfSpaces = numOfSpaces + 1;
        }
        if(numOfOperators != numOfOperands - 1) throw new RuntimeException("Not right amount of operands or operators!");
        if(opened != closed) throw new RuntimeException("Not equal amount of opened and closed brackets!");
        if(numOfSpaces != numOfOperands + numOfOperators + opened + closed  - 1) throw new RuntimeException("Incorrect spacing!");
        return operands.pop();
    }


}
