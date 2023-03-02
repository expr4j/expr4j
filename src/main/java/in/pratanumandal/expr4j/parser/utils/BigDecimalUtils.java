/**
 * Copyright 2023 Pratanu Mandal
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package in.pratanumandal.expr4j.parser.utils;

import ch.obermuhlner.math.big.BigDecimalMath;
import in.pratanumandal.expr4j.exception.Expr4jException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.List;
import java.util.Objects;

/**
 * These are the utility functions to manipulate {@link BigDecimal}.
 *
 * @author Pratanu Mandal
 */
public class BigDecimalUtils {

    /**
     * Utility classes should not have public constructors.
     */
    private BigDecimalUtils() {}

    /**
     * Compare two BigDecimal instances with a specified precision.
     *
     * @param x first BigDecimal instance
     * @param y second BigDecimal instance
     * @param precision the precision
     * @return a negative integer, zero, or a positive integer as the first instance is less than, equal to, or greater than the second instance
     */
    public static int compare(BigDecimal x, BigDecimal y, int precision) {
        BigDecimal epsilon = BigDecimal.ONE.movePointLeft(precision);
        BigDecimal absDelta = x.subtract(y).abs();
        int result = absDelta.compareTo(epsilon);
        return result <= 0 ? 0 : x.compareTo(y);
    }

    /**
     * Check if two BigDecimal instances are equal with a specified precision.
     *
     * @param x first BigDecimal instance
     * @param y second BigDecimal instance
     * @param precision the precision
     * @return True if both instances are equal, false otherwise
     */
    public static boolean equals(BigDecimal x, BigDecimal y, int precision) {
        return compare(x, y, precision) == 0;
    }

    /**
     * Calculate the log of x to the base y.
     *
     * @param x the operand
     * @param y the base or radix
     * @param mathContext math context
     * @return log of x to the base y
     */
    public static BigDecimal log(BigDecimal x, BigDecimal y, MathContext mathContext) {
        return BigDecimalMath.log(x, mathContext)
                .divide(BigDecimalMath.log(y, mathContext), mathContext);
    }

    /**
     * Calculate the cube root of x.
     *
     * @param x the operand
     * @param mathContext math context
     * @return cube root of x
     */
    public static BigDecimal cbrt(BigDecimal x, MathContext mathContext) {
        return BigDecimalMath.pow(x,
                BigDecimal.ONE.divide(new BigDecimal(3), mathContext),
                mathContext);
    }

    /**
     * Calculate average (mean) of a list of BigDecimal instances.
     *
     * @param list list of BigDecimal instances
     * @param mathContext math context
     * @return Average (mean) of the list of BigDecimal instances
     */
    public static BigDecimal average(List<BigDecimal> list, MathContext mathContext) {
        BigDecimal sum = list.stream()
                .map(Objects::requireNonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return sum.divide(new BigDecimal(list.size()), mathContext);
    }

    /**
     * Calculate the factorial of an integer.
     *
     * @param n the integer
     * @return factorial of n
     */
    private static BigDecimal factorial(BigInteger n) {
        BigInteger factorial = BigInteger.ONE;
        while (n.compareTo(BigInteger.ONE) > 0) {
            factorial = factorial.multiply(n);
            n = n.subtract(BigInteger.ONE);
        }
        return new BigDecimal(factorial);
    }

    /**
     * Calculate the factorial.
     *
     * @param x the operand
     * @return factorial of x
     */
    public static BigDecimal factorial(BigDecimal x) {
        if (x == null || x.compareTo(BigDecimal.ZERO) < 0 || !isInteger(x)) {
            throw new Expr4jException("Cannot calculate factorial of " + x);
        }
        return factorial(x.toBigInteger());
    }

    /**
     * Check if a BigDecimal instance is an integer.
     *
     * @param x BigDecimal instance
     * @return True if the instance is an integer, false otherwise
     */
    public static boolean isInteger(BigDecimal x) {
        return x.stripTrailingZeros().scale() <= 0;
    }

}
