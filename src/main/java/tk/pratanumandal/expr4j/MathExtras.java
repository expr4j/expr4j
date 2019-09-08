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
