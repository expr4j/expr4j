package com.pramanda.expr;

import java.util.HashMap;
import java.util.Map;

import com.pramanda.expr.Operator.Properties.Associativity;

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
		OPERATORS.put("sin", new Properties(1, 4, Associativity.NO));
		OPERATORS.put("cos", new Properties(1, 4, Associativity.NO));
		OPERATORS.put("tan", new Properties(1, 4, Associativity.NO));
		OPERATORS.put("round", new Properties(1, 4, Associativity.NO));
		OPERATORS.put("floor", new Properties(1, 4, Associativity.NO));
		OPERATORS.put("ceil", new Properties(1, 4, Associativity.NO));
		
		// zero argument - 5
		OPERATORS.put("rand", new Properties(0, 5, Associativity.NO));
	}

	public Operator(String value) {
		super(value);
	}
	
	public int getOperandCount() {
		return OPERATORS.get(value).params;
	}
	
	public Associativity getAssociativity() {
		return OPERATORS.get(value).associativity;
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
			case "rand": return new Operand(String.valueOf(Math.random()));
			case "+": return new Operand(String.valueOf(operands[0].toDouble() + operands[1].toDouble()));
			case "-": return new Operand(String.valueOf(operands[0].toDouble() - operands[1].toDouble()));
			case "*": return new Operand(String.valueOf(operands[0].toDouble() * operands[1].toDouble()));
			case "/": return new Operand(String.valueOf(operands[0].toDouble() / operands[1].toDouble()));
			case "%": return new Operand(String.valueOf(operands[0].toDouble() % operands[1].toDouble()));
			case "^": return new Operand(String.valueOf(Math.pow(operands[0].toDouble(), operands[1].toDouble())));
			case "sin": return new Operand(String.valueOf(Math.sin(operands[0].toDouble())));
			case "cos": return new Operand(String.valueOf(Math.cos(operands[0].toDouble())));
			case "tan": return new Operand(String.valueOf(Math.tan(operands[0].toDouble())));
			case "round": return new Operand(String.valueOf(Math.round(operands[0].toDouble())));
			case "floor": return new Operand(String.valueOf(Math.floor(operands[0].toDouble())));
			case "ceil": return new Operand(String.valueOf(Math.ceil(operands[0].toDouble())));
			default: throw new RuntimeException("Unsupported operator");
		}
	}
	
	public static class Properties {

		protected int params;
		protected int precendence;
		protected Associativity associativity;
		
		protected Properties(int params, int precendence) {
			this(params, precendence, Associativity.LEFT);
		}
		
		protected Properties(int params, int precendence, Associativity associativity) {
			this.params = params;
			this.precendence = precendence;
			this.associativity = associativity;
		}
		
		public static enum Associativity {
			LEFT, RIGHT, NO
		}
		
	}
	
}
