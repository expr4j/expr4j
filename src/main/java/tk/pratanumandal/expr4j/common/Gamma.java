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

package tk.pratanumandal.expr4j.common;

/**
 * The <code>Gamma</code> class provides an implementation of the Gamma function for calculating factorial of double values.
 * 
 * @author Pratanu Mandal
 * @since 0.0.2
 * 
 */
public class Gamma {

	/**
	 * Utility classes should not have public constructors.
	 */
	private Gamma() {}
	
	/**
	 * Lanczos Gamma Function approximation - N (number of coefficients - 1)
	 */
	private static final int LANCZOS_N = 6;
	
	/**
	 * Lanczos Gamma Function approximation - Coefficients
	 */
	private static final double[] LANCZOS_COEFF = {1.000000000190015, 76.18009172947146, -86.50532032941677, 24.01409824083091, -1.231739572450155, 0.1208650973866179E-2, -0.5395239384953E-5};
	
	/**
	 * Lanczos Gamma Function approximation - small gamma
	 */
	private static final double LANCZOS_SMALL_GAMMA = 5.0;
	
	/**
	 * Gamma function. Lanczos approximation (6 terms).
	 * 
	 * @param x the value for which gamma is to be calculated
	 * @return the gamma of x
	 */
	public static double gamma(double x) {
		double xcopy = x;
		double first = x + LANCZOS_SMALL_GAMMA + 0.5;
		double second = LANCZOS_COEFF[0];
		double fg = 0.0;

		if (x >= 0.0) {
			if (x >= 1.0 && x - (int) x == 0.0) {
				fg = Gamma.factorial((int) x - 1);
			} else {
				first = Math.pow(first, x + 0.5) * Math.exp(-first);
				for (int i = 1; i <= LANCZOS_N; i++) {
					second += LANCZOS_COEFF[i] / ++xcopy;
				}
				fg = first * Math.sqrt(2.0 * Math.PI) * second / x;
			}
		} else {
			fg = -Math.PI / (x * gamma(-x) * Math.sin(Math.PI * x));
		}
		return fg;
	}
	
	/**
	 * factorial of n
	 *
	 * @return factorial returned as double but is, numerically, an integer.
	 * Numerical rounding may makes this an approximation after n = 21
	 */
	private static double factorial(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("n has to be non-negative.");
		}

		double f = 1.0;
		for (int i = 2; i <= n; i++) {
			f *= i;
		}

		return f;
	}
	
}
