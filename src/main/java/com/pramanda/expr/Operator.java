package com.pramanda.expr;

import java.util.HashMap;
import java.util.Map;

public class Operator extends Token implements Comparable<Operator> {
	
	protected static final Map<String, Properties> OPERATORS;
	
	static {
		OPERATORS = new HashMap<>();
		
		// priority is in increasing order from 0 to infinity
		
		// parenthesis - 0
		OPERATORS.put("(", new Properties(1, 0));
		OPERATORS.put(")", new Properties(1, 0));
		
		// binary - 1
		OPERATORS.put("+", new Properties(2, 1));
		OPERATORS.put("-", new Properties(2, 1));
		
		// binary - 2
		OPERATORS.put("*", new Properties(2, 2));
		OPERATORS.put("/", new Properties(2, 2));
		OPERATORS.put("%", new Properties(2, 2));
		
		// binary - 3
		OPERATORS.put("^", new Properties(2, 3));
		
		// unary - 4
		OPERATORS.put("sin", new Properties(1, 4));
		OPERATORS.put("cos", new Properties(1, 4));
		OPERATORS.put("tan", new Properties(1, 4));
	}

	public Operator(String value) {
		super(value);
	}
	
	public int getOperandCount() {
		return OPERATORS.get(value).params;
	}

	@Override
	public int compareTo(Operator other) {
		// compare the precedences of the two operators
		return OPERATORS.get(this.value).precendence - OPERATORS.get(other.value).precendence;
	}
	
	public static boolean isOperator(String op) {
		return OPERATORS.containsKey(op);
	}
	
	public Operand evaluate(Operand ... operands) {
		switch (value) {
			case "+": return new Operand(String.valueOf(operands[0].toDouble() + operands[1].toDouble()));
			case "-": return new Operand(String.valueOf(operands[0].toDouble() - operands[1].toDouble()));
			case "*": return new Operand(String.valueOf(operands[0].toDouble() * operands[1].toDouble()));
			case "/": return new Operand(String.valueOf(operands[0].toDouble() / operands[1].toDouble()));
			case "%": return new Operand(String.valueOf(operands[0].toDouble() % operands[1].toDouble()));
			case "^": return new Operand(String.valueOf(Math.pow(operands[0].toDouble(), operands[1].toDouble())));
			case "sin": return new Operand(String.valueOf(Math.sin(operands[0].toDouble())));
			case "cos": return new Operand(String.valueOf(Math.cos(operands[0].toDouble())));
			case "tan": return new Operand(String.valueOf(Math.tan(operands[0].toDouble())));
			default: throw new RuntimeException("Unsupported operator");
		}
	}
	
	protected static class Properties {

		protected int params;
		protected int precendence;
		
		protected Properties(int params, int precendence) {
			this.params = params;
			this.precendence = precendence;
		}
		
	}
	
}
