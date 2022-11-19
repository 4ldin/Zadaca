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
        assertEquals(48, Math.round(new ExpressionEvaluator().evaluate("( sqrt ( 16 ) * ( 6 / sqrt ( 4 ) ) )")));
    }
    @Test
    void evaluateTest_3(){
        assertEquals(347, Math.round(new ExpressionEvaluator().evaluate("( sqrt ( 64 ) * ( sqrt ( 2 ) + 4 ) )")));
    }

    /**
     * Test using real numbers
     */
    @Test
    void evaluateTest_4(){
        assertEquals(2357.547,(double) Math.round(new ExpressionEvaluator().evaluate("( 2.356 + ( 5.54 / 3.58 ) )") * 1000) / 1000);
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
     * Testing right amount of operators
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
     * Testing spacing
     */
    @Test
    void exception_3(){
        Assertions.assertThrows(RuntimeException.class, new Executable() {
            @Override
            public void execute() {
                new ExpressionEvaluator().evaluate("( sqrt( 16 ) * ( 6 / sqrt ( 4 ) ) )");
            }
        });
    }

    /**
     * Testing if there is more operands than allowed
     */
    @Test
    void exception_4(){
        Assertions.assertThrows(RuntimeException.class, new Executable() {
            @Override
            public void execute() {
                new ExpressionEvaluator().evaluate("( 1 + ( ( 2 + 3 3 ) * ( 4 * 5 ) ) )");
            }
        });
    }

    /**
     * Checking unknown operators
     */
    @Test
    void exception_5(){
        Assertions.assertThrows(RuntimeException.class, new Executable() {
            @Override
            public void execute() {
                new ExpressionEvaluator().evaluate("( 1 + ( ( 2 + abs ) * ( 4 * 5 ) ) )");
            }
        });
    }

    /**
     * Checking if sqrt has more than one operand
     */
    @Test
    void exception_6(){
        Assertions.assertThrows(RuntimeException.class, new Executable() {
            @Override
            public void execute() {
                new ExpressionEvaluator().evaluate("( sqrt ( 2 + 3) )");
            }
        });
    }

}