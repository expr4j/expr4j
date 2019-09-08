package com.pratanumandal.expr4j;

public class MathExtras {
	
	public static double asinh(double x) {
		return Math.log(x + Math.sqrt(x * x + 1));
	}
	
	public static double acosh(double x) {
		return Math.log(x + Math.sqrt(x * x - 1));
	}
	
	public static double atanh(double x) {
		return 0.5 * Math.log((1 + x) / (1 - x));
	}
	
	public static double log(double r, double b) {
		return Math.log(r) / Math.log(b);
	}

}
