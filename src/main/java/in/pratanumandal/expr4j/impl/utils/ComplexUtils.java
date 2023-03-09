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

package in.pratanumandal.expr4j.impl.utils;

import org.apache.commons.numbers.complex.Complex;

import java.util.List;
import java.util.Objects;

/**
 * The <code>ComplexUtils</code> class provides extra math functionality not available for {@link Complex} class.
 *
 * @author Pratanu Mandal
 * @since 1.0
 *
 */
public class ComplexUtils {

    /**
     * Utility classes should not have public constructors.
     */
    private ComplexUtils() {}

    /**
     * Calculate the area hyperbolic sine.
     *
     * @param x The operand
     * @return Area hyperbolic sine of x
     */
    public static Complex asinh(Complex x) {
        return (x.add((x.multiply(x).add(Complex.ONE))
                .sqrt()))
                .log();
    }

    /**
     * Calculate the area hyperbolic cosine.
     *
     * @param x The operand
     * @return Area hyperbolic cosine of x
     */
    public static Complex acosh(Complex x) {
        return (x.add((x.add(Complex.ONE).sqrt())
                .multiply(x.subtract(Complex.ONE).sqrt())))
                .log();
    }

    /**
     * Calculate the area hyperbolic tangent.
     *
     * @param x The operand
     * @return Area hyperbolic tangent of x
     */
    public static Complex atanh(Complex x) {
        return ((Complex.ONE.add(x).log())
                .subtract(Complex.ONE.subtract(x).log()))
                .divide(2);
    }

    /**
     * Convert operand from radians to degrees.
     *
     * @param x The operand in radians
     * @return The operand in degrees
     */
    public static Complex toDegrees(Complex x) {
        return Complex.ofCartesian(Math.toDegrees(x.getReal()), Math.toDegrees(x.getImaginary()));
    }

    /**
     * Convert operand from degrees to radians.
     *
     * @param x The operand in degrees
     * @return The operand in radians
     */
    public static Complex toRadians(Complex x) {
        return Complex.ofCartesian(Math.toRadians(x.getReal()), Math.toRadians(x.getImaginary()));
    }

    /**
     * Calculate the log of x to the base 10.
     *
     * @param x The operand
     * @return Log of x to the base 10
     */
    public static Complex log10(Complex x) {
        return log(x, Complex.ofCartesian(10, 0));
    }

    /**
     * Calculate the log of x to the base y.
     *
     * @param x The operand
     * @param y The base or radix
     * @return Log of x to the base y
     */
    public static Complex log(Complex x, Complex y) {
        return x.log().divide(y.log());
    }

    /**
     * Calculate the cube root of x.
     *
     * @param x The operand
     * @return Cube root of x
     */
    public static Complex cbrt(Complex x) {
        return x.pow(1.0 / 3.0);
    }

    /**
     * Find the maximum of a list of operands.
     *
     * @param complexList List of operands
     * @return Maximum of list of operands
     */
    public static Complex max(List<Complex> complexList) {
        Complex max = null;
        for (Complex complex : complexList) {
            if (max == null || complex.abs() > max.abs()) {
                max = complex;
            }
        }
        return max;
    }

    /**
     * Find the minimum of a list of operands.
     *
     * @param complexList List of operands
     * @return Minimum of list of operands
     */
    public static Complex min(List<Complex> complexList) {
        Complex min = null;
        for (Complex complex : complexList) {
            if (min == null || complex.abs() < min.abs()) {
                min = complex;
            }
        }
        return min;
    }

    /**
     * Calculate average (mean) of a list of operands.
     *
     * @param list List of operands
     * @return Average (mean) of the list of operands
     */
    public static Complex average(List<Complex> list) {
        Complex sum = list.stream()
                .map(Objects::requireNonNull)
                .reduce(Complex.ZERO, Complex::add);
        return sum.divide(Complex.ofCartesian(list.size(), 0));
    }

}
