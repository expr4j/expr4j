/**
 * Copyright 2019 Pratanu Mandal
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in the
 * Software without restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the
 * following conditions:
 * 
 * 		The above copyright notice and this permission notice shall be included
 * 		in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 */

package tk.pratanumandal.expr4j;

/**
 * The <code>MathExtras</code> class provides extra math functionality not available in java.lang.Math.
 * 
 * @author Pratanu Mandal
 *
 */
public class MathExtras {
	
	/**
	 * Calculate the area hyperbolic sine
	 * 
	 * @param x the operand
	 * @return area hyperbolic sine of x
	 */
	public static double asinh(double x) {
		return Math.log(x + Math.sqrt(x * x + 1));
	}
	
	/**
	 * Calculate the area hyperbolic cosine
	 * 
	 * @param x the operand
	 * @return area hyperbolic cosine of x
	 */
	public static double acosh(double x) {
		return Math.log(x + Math.sqrt(x * x - 1));
	}
	
	/**
	 * Calculate the area hyperbolic tangent
	 * 
	 * @param x the operand
	 * @return area hyperbolic tangent of x
	 */
	public static double atanh(double x) {
		return 0.5 * Math.log((1 + x) / (1 - x));
	}
	
	/**
	 * Calculate the log of x to the base b
	 * 
	 * @param x the operand
	 * @param b the base or radix
	 * @return log of x to the base b
	 */
	public static double log(double x, double b) {
		return Math.log(x) / Math.log(b);
	}

}
