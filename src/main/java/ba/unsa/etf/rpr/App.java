package ba.unsa.etf.rpr;


/**
 * Java main class that parses the input from the console from the args parameter and performs its validation
 * @author Aldin Islamagic
 */

public class App 
{
    private static String input;

    public static void main( String[] args )
    {
        String expression = "";
        for(String s : args){
            expression = expression + s;
        }
       ExpressionEvaluator evaluator = new ExpressionEvaluator();
       try{
            System.out.println(evaluator.evaluate(expression));
        }catch(RuntimeException e){
            System.out.println(e.getMessage());
        }

    }
}
