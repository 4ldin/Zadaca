package ba.unsa.etf.rpr;
import java.util.Scanner;

/**
 * Java main class that parses the input from the console from the args parameter and performs its validation
 * @author Aldin Islamagic
 */

public class App 
{
    private static String input;
    public static void main( String[] args )
    {
        String input = args[0];
        ExpressionEvaluator evaluator = new ExpressionEvaluator();
        try{
            System.out.println(evaluator.evaluate(input));
        }catch(RuntimeException e){
            System.out.println(e.getMessage());
        }
    }
}
