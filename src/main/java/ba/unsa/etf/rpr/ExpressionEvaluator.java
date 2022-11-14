package ba.unsa.etf.rpr;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Class for computing the value of arithmetic expressions using Dijkstra's Algorithm for expression evaluation.
 * It is assumed that all expressions will be space separated for easier parsing of the input expression.
 * Support for operator priority is not included.
 * @author Aldin Islamagic
 * Version: 1.0
 */

public class ExpressionEvaluator {

    private Stack<Double> operands;
    private Stack<String> operators;

    /**
     * Constructor that creates an empty object
     */
    public ExpressionEvaluator() {
        this.operands = new Stack<>();
        this.operators = new Stack<>();
    }

    /**
     * Private class that checks if the parameter is an operator
     */
    private boolean isOperator(String s){
        return s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/");
    }

    /**
     * Private class that calculates the value of the expression
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
     * Class that converts an arithmetic expression (a string of characters) to the value that it represent
     */

    public Double evaluate(String expression){
        int opened = 0;
        int closed = 0;
        if(expression.isEmpty()) throw new RuntimeException("Expression is empty!");
        if(expression.charAt(0) != '(') throw new RuntimeException("Expression doesn't start with a left bracket");
        String[] array = expression.split(" ");
        List<Integer> numOfOperInClosedBracketcs = new ArrayList<>();
        int currentBracket = -1;
        boolean isOpen = false;
        for (String s : array) {
            if (isOperator(s)) {
                if(isOpen) numOfOperInClosedBracketcs.set(currentBracket, numOfOperInClosedBracketcs.get(currentBracket) + 1);
                operators.push(s);
            } else if (s.equals(")")) {
                Integer temp = numOfOperInClosedBracketcs.get(currentBracket);
                while(temp!= 0) {
                    Double a = operands.pop();
                    Double b = operands.pop();
                    operands.push(applyOperatorToOperands(a, operators.pop(), b));
                    temp--;
                }
                closed = closed + 1;
                numOfOperInClosedBracketcs.remove(currentBracket);
                currentBracket = currentBracket - 1;
            }
            else if(s.equals("(")){
                isOpen = true;
                currentBracket = currentBracket + 1;
                numOfOperInClosedBracketcs.add(0);
                opened = opened + 1;
            }else {
                operands.push(Double.parseDouble(s));
            }
        }
        if(opened != closed) throw new RuntimeException("Not equal amount of opened and closed brackets!");
        return operands.pop();
    }


}
