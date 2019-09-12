/**
 * Copyright 2019 Pratanu Mandal
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
