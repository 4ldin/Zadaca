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
     * @param operators Stack of operators
     * @param operands Stack of operands
     * @return Returns the value of "a op b", throws an exception if "b = 0" or "op" is not a valid operator.
     */
    private Double applyOperatorToOperands(Stack<String> operators, Stack<Double> operands){
        String op = operators.pop();
        Double val = operands.pop();
        if(op.equals("+")) val = operands.pop() + val;
        else if(op.equals("-")) val = operands.pop() - val;
        else if(op.equals("*")) val = operands.pop() * val;
        else if(op.equals("/")) {
            if(val == 0) throw new RuntimeException("Division with 0 is not allowed!");
            val = operands.pop() / val;
        }
        else if(op.equals("sqrt")) val = Math.sqrt(val);
        return val;
    }


    /**
     * Class that converts an arithmetic expression (a string of characters) to the value that it represent.
     * @param expression Arithmetic expression that gets converted to wanted value.
     * @return Return the value of the given expression.
     */

    public Double evaluate(String expression){
        if(expression.isEmpty()) throw new RuntimeException("Expression is empty!");
        if(expression.charAt(0) != '(') throw new RuntimeException("Expression doesn't start with a left bracket");
        int currentBracket = -1, opened = 0, closed = 0;
        int numOfOperators = 0, numOfOperands = 0, numOfSpaces = 0, numOfSqrt = 0;
        boolean isSqrt = false, rightAmountOfOperands = true;
        List<Integer> numOfOperastorsInBracket = new ArrayList<>();
        List<Integer> numOfOperandsInBracket = new ArrayList<>();
        for (int i = 0; i < expression.length(); i++) {
            if (isOperator(String.valueOf(expression.charAt(i))) && String.valueOf(expression.charAt(i+1)).equals(" ")) {
                operators.push(String.valueOf(expression.charAt(i)));
                numOfOperastorsInBracket.set(currentBracket, numOfOperastorsInBracket.get(currentBracket) + 1);
                if(numOfOperastorsInBracket.get(currentBracket) > 1) throw new RuntimeException("Not right amount of operators in one bracket!");
                numOfOperators = numOfOperators + 1;
            } else if (String.valueOf(expression.charAt(i)).equals(")")) {
                if(currentBracket == -1) {
                    closed = closed + 1;
                    break;
                }
                if(operands.size() > 1) operands.push(applyOperatorToOperands(operators, operands));
                closed = closed + 1;
                numOfOperastorsInBracket.set(currentBracket, 0);
                if((numOfOperandsInBracket.get(currentBracket) == 1 || numOfOperandsInBracket.get(currentBracket) > 2) && !isSqrt) rightAmountOfOperands = false;
                else if(numOfOperandsInBracket.get(currentBracket) != 1 && isSqrt) rightAmountOfOperands = false;
                numOfOperandsInBracket.remove(currentBracket);
                currentBracket = currentBracket - 1;
                if(currentBracket != -1) numOfOperandsInBracket.set(currentBracket, numOfOperandsInBracket.get(currentBracket) + 1);
                if(isSqrt) isSqrt = false;
            }
            else if(String.valueOf(expression.charAt(i)).equals("(")){
                    currentBracket = currentBracket + 1;
                    opened = opened + 1;
                    numOfOperastorsInBracket.add(0);
                    numOfOperandsInBracket.add(0);
            }else if(expression.startsWith("sqrt", i)){
                isSqrt = true;
                operators.push("sqrt");
                i = i + 3;
                numOfSqrt = numOfSqrt + 1;
            }else if(!String.valueOf(expression.charAt(i)).equals(" ")) {
                try{
                    Double n = Double.parseDouble(String.valueOf(expression.charAt(i)));
                }catch(NumberFormatException error){
                    throw new RuntimeException("Input string cannot be parsed!");
                }
                StringBuilder num = new StringBuilder();
                while ((expression.charAt(i) >='0' && expression.charAt(i) <= '9') || expression.charAt(i) =='.') {
                    if(expression.charAt(i) !='.') num.append(expression.charAt(i));
                    i = i + 1;
                }
                operands.push(Double.parseDouble(String.valueOf(num)));
                numOfOperandsInBracket.set(currentBracket, numOfOperandsInBracket.get(currentBracket) + 1);
                numOfOperands = numOfOperands + 1;
            }
            if(String.valueOf(expression.charAt(i)).equals(" ")) numOfSpaces = numOfSpaces + 1;
        }
        if(opened != closed) throw new RuntimeException("Not equal amount of opened and closed brackets!");
        if(numOfSpaces != numOfOperands + numOfOperators + opened + closed + numOfSqrt - 1) throw new RuntimeException("Incorrect spacing!");
        if(!rightAmountOfOperands) throw new RuntimeException("Not right amount of operands in one bracket!");
        return operands.pop();
    }


}
