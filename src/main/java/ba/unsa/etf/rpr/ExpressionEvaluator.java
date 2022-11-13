package ba.unsa.etf.rpr;

import java.util.Stack;

/**
 * Class for computing the value of arithmetic expressions using Dijkstra's Algorithm for expression evaluation
 * It is assumed that all expressions will be space separated for easier parsing of the input expression.
 * @author Aldin Islamagic
 * Version: 1.0
 */

public class ExpressionEvaluator {

    private Stack<Double> operands;
    private Stack<String> operators;

    /** Constructor that creates an empty object */
    public ExpressionEvaluator() {
        this.operands = new Stack<>();
        this.operators = new Stack<>();
    }

    /** Class that converts an arithmetic expression(a string of characters) to the value that it represent */

    public Double evaluate(String expression){
        return null;
    };

}
