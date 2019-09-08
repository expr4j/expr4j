package tk.pratanumandal.expr4j;

import java.util.HashMap;
import java.util.Map;

import tk.pratanumandal.expr4j.Operator.Properties.Associativity;

/**
 * The <code>Operator</code> class represents the operators, functions, variables, and constants in the expression.<br><br>
 * 
 * It provides implementation for evaluation of the operators, functions, variables, and constants.<br>
 * Functions, variables, and constants are treated as operators as well with specialized evaluation techniques in order to maintain precedence and associativity.
 * 
 * @author Pratanu Mandal
 *
 */
public class Operator extends Token implements Comparable<Operator> {
	
	/**
	 * A map of all operators and functions supported.
	 */
	protected static final Map<String, Properties> OPERATORS;
	
	static {
		OPERATORS = new HashMap<>();
		
		// priority is in increasing order from 0 to infinity
		
		// parenthesis - 0
		OPERATORS.put("(", new Properties(1, 0));
		OPERATORS.put(")", new Properties(1, 0));
		
		// no argument - 0
		OPERATORS.put(",", new Properties(0, 0));
		
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
		OPERATORS.put("uminus", new Properties(1, 4, Associativity.NO));
		OPERATORS.put("uplus", new Properties(1, 4, Associativity.NO));
		
		OPERATORS.put("abs", new Properties(1, 4, true));
		
		OPERATORS.put("sin", new Properties(1, 4, true));
		OPERATORS.put("cos", new Properties(1, 4, true));
		OPERATORS.put("tan", new Properties(1, 4, true));
		
		OPERATORS.put("asin", new Properties(1, 4, true));
		OPERATORS.put("acos", new Properties(1, 4, true));
		OPERATORS.put("atan", new Properties(1, 4, true));
		
		OPERATORS.put("sinh", new Properties(1, 4, true));
		OPERATORS.put("cosh", new Properties(1, 4, true));
		OPERATORS.put("tanh", new Properties(1, 4, true));
		
		OPERATORS.put("asinh", new Properties(1, 4, true));
		OPERATORS.put("acosh", new Properties(1, 4, true));
		OPERATORS.put("atanh", new Properties(1, 4, true));
		
		OPERATORS.put("deg", new Properties(1, 4, true));
		OPERATORS.put("rad", new Properties(1, 4, true));
		
		OPERATORS.put("round", new Properties(1, 4, true));
		OPERATORS.put("floor", new Properties(1, 4, true));
		OPERATORS.put("ceil", new Properties(1, 4, true));
		
		OPERATORS.put("ln", new Properties(1, 4, true));
		OPERATORS.put("log10", new Properties(1, 4, true));
		OPERATORS.put("log", new Properties(2, 4, true));
		
		OPERATORS.put("sqrt", new Properties(1, 4, true));
		OPERATORS.put("cbrt", new Properties(1, 4, true));
		
		// zero argument - 5
		OPERATORS.put("rand", new Properties(0, 5, Associativity.NO));
		OPERATORS.put("pi", new Properties(0, 5, Associativity.NO));
		OPERATORS.put("e", new Properties(0, 5, Associativity.NO));
	}

	/**
	 * Parameterized constructor.
	 * 
	 * @param value The operator, function, variable, or constant as a string
	 */
	public Operator(String value) {
		super(value);
	}
	
	/**
	 * Get count of operands supported by this operator.
	 * 
	 * @return number of operands supported by this operator
	 */
	public int getOperandCount() {
		return OPERATORS.get(value).params;
	}
	
	/**
	 * Get associativity information of this operator.
	 * 
	 * @return Associativity of this operator
	 */
	public Associativity getAssociativity() {
		return OPERATORS.get(value).associativity;
	}
	
	/**
	 * Determine if this operator is a function or not.
	 * 
	 * @return true if this operator is a function, otherwise false
	 */
	public boolean isFunction() {
		return OPERATORS.get(value).function;
	}
	
	/**
	 * Utility method to determine if a string is a supported operator.
	 * 
	 * @param op The string to check
	 * @return true if the string is a supported operator, false otherwise
	 */
	public static boolean isOperator(String op) {
		return OPERATORS.containsKey(op);
	}
	
	/**
	 * Utility method to determine if a string is a supported function.
	 * 
	 * @param op The string to check
	 * @return true if the string is a supported function, false otherwise
	 */
	public static boolean isFunction(String op) {
		return OPERATORS.get(op).function;
	}
	
	/**
	 * Method to evaluate this operator, function, variable, or constant.<br>
	 * It takes a variable number of operands as parameter depending on the number of operands required for this operator.
	 * 
	 * @param operands Variable number of operands required by this operator
	 * @return The evaluated result as another operand
	 */
	public Operand evaluate(Operand ... operands) {
		switch (value) {
			case "+": return new Operand(String.valueOf(operands[0].toDouble() + operands[1].toDouble()));
			case "-": return new Operand(String.valueOf(operands[0].toDouble() - operands[1].toDouble()));
			
			case "*": return new Operand(String.valueOf(operands[0].toDouble() * operands[1].toDouble()));
			case "/": return new Operand(String.valueOf(operands[0].toDouble() / operands[1].toDouble()));
			case "%": return new Operand(String.valueOf(operands[0].toDouble() % operands[1].toDouble()));
			case "^": return new Operand(String.valueOf(Math.pow(operands[0].toDouble(), operands[1].toDouble())));
			
			case "uminus": return new Operand(String.valueOf(-operands[0].toDouble()));
			case "uplus": return new Operand(String.valueOf(+operands[0].toDouble()));
			
			case "abs": return new Operand(String.valueOf(Math.abs(operands[0].toDouble())));
			
			case "sin": return new Operand(String.valueOf(Math.sin(operands[0].toDouble())));
			case "cos": return new Operand(String.valueOf(Math.cos(operands[0].toDouble())));
			case "tan": return new Operand(String.valueOf(Math.tan(operands[0].toDouble())));
			
			case "asin": return new Operand(String.valueOf(Math.asin(operands[0].toDouble())));
			case "acos": return new Operand(String.valueOf(Math.acos(operands[0].toDouble())));
			case "atan": return new Operand(String.valueOf(Math.atan(operands[0].toDouble())));
			
			case "sinh": return new Operand(String.valueOf(Math.sinh(operands[0].toDouble())));
			case "cosh": return new Operand(String.valueOf(Math.cosh(operands[0].toDouble())));
			case "tanh": return new Operand(String.valueOf(Math.tanh(operands[0].toDouble())));
			
			case "asinh": return new Operand(String.valueOf(MathExtras.asinh(operands[0].toDouble())));
			case "acosh": return new Operand(String.valueOf(MathExtras.acosh(operands[0].toDouble())));
			case "atanh": return new Operand(String.valueOf(MathExtras.atanh(operands[0].toDouble())));
			
			case "deg": return new Operand(String.valueOf(Math.toDegrees(operands[0].toDouble())));
			case "rad": return new Operand(String.valueOf(Math.toRadians(operands[0].toDouble())));
			
			case "round": return new Operand(String.valueOf(Math.round(operands[0].toDouble())));
			case "floor": return new Operand(String.valueOf(Math.floor(operands[0].toDouble())));
			case "ceil": return new Operand(String.valueOf(Math.ceil(operands[0].toDouble())));
			
			case "ln": return new Operand(String.valueOf(Math.log(operands[0].toDouble())));
			case "log10": return new Operand(String.valueOf(Math.log10(operands[0].toDouble())));
			case "log": return new Operand(String.valueOf(MathExtras.log(operands[0].toDouble(), operands[1].toDouble())));
			
			case "sqrt": return new Operand(String.valueOf(Math.sqrt(operands[0].toDouble())));
			case "cbrt": return new Operand(String.valueOf(Math.cbrt(operands[0].toDouble())));
			
			case "rand": return new Operand(String.valueOf(Math.random()));
			case "pi": return new Operand(String.valueOf(Math.PI));
			case "e": return new Operand(String.valueOf(Math.E));
			
			default: throw new RuntimeException("Unsupported operator");
		}
	}
	
	/**
	 * Method to compare this operator to another operator on the basis of precedence.
	 */
	@Override
	public int compareTo(Operator other) {
		// compare the precedences of the two operators
		return OPERATORS.get(this.value).precedence - OPERATORS.get(other.value).precedence;
	}
	
	/**
	 * The <code>Properties</code> class represents the properties of an operator.<br>
	 * It contains information about the number of parameters supported, the precedence, and the associativity.<br>
	 * It also helps determine if an operator is a function or not.
	 * 
	 * @author Pratanu Mandal
	 *
	 */
	public static class Properties {

		/**
		 * Number of parameters required by this operator.
		 */
		protected int params;
		
		/**
		 * The precedence of this operator.<br>
		 * Precedence ranges from 1 to infinity, in ascending order, i.e, with 1 being lowest precedence possible.<br>
		 * Although parenthesis and comma have 0 precedence, they are a special case and are evaluated separately.
		 */
		protected int precedence;
		
		/**
		 * The associativity of this operator.
		 */
		protected Associativity associativity;
		
		/**
		 * Boolean flag to determine if this operator is a function or not.<br>
		 * True if this operator is a function, false otherwise.
		 */
		protected boolean function;
		
		/**
		 * Parameterized constructor taking number of parameters and precedence.<br>
		 * Associativity is set to LEFT and function flag is set to false.
		 * 
		 * @param params Number if parameters required by this operator
		 * @param precedence The precedence value of this operator
		 */
		protected Properties(int params, int precedence) {
			this(params, precedence, Associativity.LEFT, false);
		}
		
		/**
		 * Parameterized constructor taking number of parameters, precedence, and associativity.<br>
		 * Function flag is set to false.
		 * 
		 * @param params Number if parameters required by this operator
		 * @param precedence The precedence value of this operator
		 * @param associativity The associativity of this operator
		 */
		protected Properties(int params, int precedence, Associativity associativity) {
			this(params, precedence, associativity, false);
		}
		
		/**
		 * Parameterized constructor taking number of parameters, precedence, and function flag.<br>
		 * If function flag is true, associativity is set to NO, otherwise LEFT.
		 * 
		 * @param params Number if parameters required by this operator
		 * @param precedence The precedence value of this operator
		 * @param function The function flag of this operator (true if this operator is a function, false otherwise)
		 */
		protected Properties(int params, int precedence, boolean function) {
			this(params, precedence, function ? Associativity.NO : Associativity.LEFT, function);
		}
		
		/**
		 * Parameterized constructor taking number of parameters, precedence, associativity, and function flag.<br>
		 * It is recommended not to use this constructor, instead using one of the preset constructors if possible.
		 * 
		 * @param params Number if parameters required by this operator
		 * @param precedence The precedence value of this operator
		 * @param associativity The associativity of this operator
		 * @param function The function flag of this operator (true if this operator is a function, false otherwise)
		 */
		protected Properties(int params, int precedence, Associativity associativity, boolean function) {
			this.params = params;
			this.precedence = precedence;
			this.associativity = associativity;
			this.function = function;
		}
		
		/**
		 * The <code>Associativity</code> enum represents the associativity property of an operator.<br>
		 * It can be of three types: LEFT associative, RIGHT associative and NO associative.
		 * 
		 * @author Pratanu Mandal
		 *
		 */
		public static enum Associativity {
			/**
			 * Left associativity.
			 */
			LEFT,
			
			/**
			 * Right associativity.
			 */
			RIGHT,
			
			/**
			 * No associativity.
			 */
			NO
		}
		
	}
	
}
