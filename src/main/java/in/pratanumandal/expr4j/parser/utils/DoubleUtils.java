package in.pratanumandal.expr4j.parser.utils;

import in.pratanumandal.expr4j.exception.Expr4jException;

import java.util.List;

/**
 * The <code>DoubleUtils</code> class provides extra math functionality not available for {@link Double} class.
 *
 * @author Pratanu Mandal
 * @since 1.0
 *
 */
public class DoubleUtils {

    /**
     * Utility classes should not have public constructors.
     */
    private DoubleUtils() {}

    /**
     * Calculate the area hyperbolic sine.
     *
     * @param x The operand
     * @return Area hyperbolic sine of x
     */
    public static double asinh(double x) {
        return Math.log(x + Math.sqrt(x * x + 1));
    }

    /**
     * Calculate the area hyperbolic cosine.
     *
     * @param x The operand
     * @return Area hyperbolic cosine of x
     */
    public static double acosh(double x) {
        return Math.log(x + Math.sqrt(x * x - 1));
    }

    /**
     * Calculate the area hyperbolic tangent.
     *
     * @param x The operand
     * @return Area hyperbolic tangent of x
     */
    public static double atanh(double x) {
        return 0.5 * Math.log((1 + x) / (1 - x));
    }

    /**
     * Calculate the log of x to the base y.
     *
     * @param x The operand
     * @param y The base or radix
     * @return Log of x to the base y
     */
    public static double log(double x, double y) {
        return Math.log(x) / Math.log(y);
    }

    /**
     * Calculate average (mean) of a list of operands.
     *
     * @param list List of operands
     * @return Average (mean) of the list of operands
     */
    public static Double average(List<Double> list) {
        return list.stream().mapToDouble(d -> d).average().orElse(0.0);
    }

    /**
     * Calculate the factorial of an integer.
     *
     * @param n The integer
     * @return Factorial of n
     */
    private static double factorial(int n) {
        double factorial = 1.0;
        for (int i = 2; i <= n; i++) {
            factorial *= i;
        }
        return factorial;
    }

    /**
     * Calculate the factorial if it is an integer.
     *
     * @param x The operand
     * @return Factorial of x
     */
    public static double factorial(double x) {
        if (x < 0 || x != (int) x) {
            throw new Expr4jException("Cannot calculate factorial of " + x);
        }
        return factorial((int) x);
    }
    
}
