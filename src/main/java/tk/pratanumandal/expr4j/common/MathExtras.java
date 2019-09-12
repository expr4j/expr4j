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
 * The <code>MathExtras</code> class provides extra math functionality not available in java.lang.Math.
 * 
 * @author Pratanu Mandal
 * @since 0.0.1
 *
 */
public class MathExtras {
	
	/**
	 * Utility classes should not have public constructors.
	 */
	private MathExtras() {}
	
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
	 * Calculate the log of x to the base b.
	 * 
	 * @param x the operand
	 * @param b the base or radix
	 * @return log of x to the base b
	 */
	public static double log(double x, double b) {
		return Math.log(x) / Math.log(b);
	}
	
	/**
	 * Calculate the factorial.
	 * 
	 * @param x the operand
	 * @return factorial of x
	 */
	public static double factorial(double x) {
		return Gamma.gamma(x);
	}

}
