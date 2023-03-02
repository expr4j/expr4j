package in.pratanumandal.expr4j.parser.utils;

import in.pratanumandal.expr4j.exception.Expr4jException;

/**
 * The <code>DoubleUtils</code> class provides extra math functionality not available in java.lang.Math.
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
     * @param x the operand
     * @return area hyperbolic sine of x
     */
    public static double asinh(double x) {
        return Math.log(x + Math.sqrt(x * x + 1));
    }

    /**
     * Calculate the area hyperbolic cosine.
     *
     * @param x the operand
     * @return area hyperbolic cosine of x
     */
    public static double acosh(double x) {
        return Math.log(x + Math.sqrt(x * x - 1));
    }

    /**
     * Calculate the area hyperbolic tangent.
     *
     * @param x the operand
     * @return area hyperbolic tangent of x
     */
    public static double atanh(double x) {
        return 0.5 * Math.log((1 + x) / (1 - x));
    }

    /**
     * Calculate the log of x to the base y.
     *
     * @param x the operand
     * @param y the base or radix
     * @return log of x to the base y
     */
    public static double log(double x, double y) {
        return Math.log(x) / Math.log(y);
    }

    /**
     * Calculate the factorial of an integer.
     *
     * @param n the integer
     * @return factorial of n
     */
    private static double factorial(int n) {
        double factorial = 1.0;
        for (int i = 2; i <= n; i++) {
            factorial *= i;
        }
        return factorial;
    }

    /**
     * Calculate the factorial.
     *
     * @param x the operand
     * @return factorial of x
     */
    public static double factorial(double x) {
        if (x < 0 || x != (int) x) {
            throw new Expr4jException("Cannot calculate factorial of " + x);
        }
        return factorial((int) x);
    }
    
}
