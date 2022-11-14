package ba.unsa.etf.rpr;


/**
 * Java main class that parses the input from the console from the args parameter and performs its validation
 * @author Aldin Islamagic
 */

public class App 
{
    public static void main( String[] args )
    {
        ExpressionEvaluator x = new ExpressionEvaluator();
        System.out.println(x.evaluate("( 1 + ( ( 2 + 3 ) * ( 4 * 5 ) ) )"));
    }
}
