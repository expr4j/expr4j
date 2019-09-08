package com.pratman.expr4j;

public class Operand extends Token {

	public Operand(String value) {
		super(value);
	}

	public double toDouble() {
		return Double.parseDouble(value);
	}
	
	public static boolean isOperand(String op) {
		try {
			Double.parseDouble(op);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
}
