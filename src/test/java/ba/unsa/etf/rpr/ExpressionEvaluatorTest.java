package ba.unsa.etf.rpr;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import static org.junit.jupiter.api.Assertions.*;

/**
 *  Tests class for method evaluate
 * @author Aldin Islamagic
 */

class ExpressionEvaluatorTest {

    /**
     * Basic result accuracy test
     */

    @Test
    void evaluateTest_1(){
        assertEquals(101.0, new ExpressionEvaluator().evaluate("( 1 + ( ( 2 + 3 ) * ( 4 * 5 ) ) )"));
    }

    /**
     * Test using sqrt operator
     */
    @Test
    void evaluateTest_2(){
        assertEquals(14, Math.round(new ExpressionEvaluator().evaluate("( 2 + sqrt( 16 ) * ( 6 / sqrt( 4 ) ) )")));
    }

    /**
     * Test using real numbers
     */
    @Test
    void evaluateTest_3(){
        assertEquals(7.678,(double) Math.round(new ExpressionEvaluator().evaluate("( 2.356 + 3.439 * ( 5.54 / 3.58 ) )") * 1000) / 1000);
    }

    /**
     * Test with negative value
     */
    @Test
    void evaluateTest_4(){
        assertEquals(-1.0,(double) Math.round(new ExpressionEvaluator().evaluate("( -3 + 2 )")));
    }

    /**
     * Testing if every bracket is closed
     */
    @Test
    void exception_1(){
        Assertions.assertThrows(RuntimeException.class, new Executable() {
            @Override
            public void execute() {
                new ExpressionEvaluator().evaluate("( ( 3 + 2 )");
            }
        });
    }

    /**
     * Testing right amount of operators and operands
     */
    @Test
    void exception_2(){
        Assertions.assertThrows(RuntimeException.class, new Executable() {
            @Override
            public void execute() {
                new ExpressionEvaluator().evaluate("( 3 + 2 + )");
            }
        });
    }

    /**
     * Testing spacing (negative numbers are allowed)
     */
    @Test
    void exception_3(){
        Assertions.assertThrows(RuntimeException.class, new Executable() {
            @Override
            public void execute() {
                new ExpressionEvaluator().evaluate("( 3 + 2)");
            }
        });
    }

}