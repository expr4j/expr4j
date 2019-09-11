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

package tk.pratanumandal.expr4j.token;

import tk.pratanumandal.expr4j.OperatorRepository;
import tk.pratanumandal.expr4j.exception.Expr4jException;
import tk.pratanumandal.expr4j.token.Operator.Properties.Associativity;

/**
 * The <code>Operator</code> class represents the operators, functions, variables, and constants in the expression.<br><br>
 * 
 * It provides implementation for evaluation of the operators, functions, variables, and constants.<br>
 * Functions, variables, and constants are treated as operators as well with specialized evaluation techniques in order to maintain precedence and associativity.
 * 
 * @author Pratanu Mandal
 * @since 0.0.1
 *
 */
public class Operator extends Token implements Comparable<Operator> {

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
		return OperatorRepository.getOperatorProperties(value).params;
	}
	
	/**
	 * Get associativity information of this operator.
	 * 
	 * @return Associativity of this operator
	 */
	public Associativity getAssociativity() {
		return OperatorRepository.getOperatorProperties(value).associativity;
	}
	
	/**
	 * Determine if this operator is a function or not.
	 * 
	 * @return true if this operator is a function, otherwise false
	 */
	public boolean isFunction() {
		return OperatorRepository.getOperatorProperties(value).function != null;
	}
	
	/**
	 * Determine if this operator is a variable or constant, or not.
	 * 
	 * @return true if this operator is a variable or constant, otherwise false
	 */
	public boolean isVariableOrConstant() {
		return OperatorRepository.getOperatorProperties(value) != null &&
				OperatorRepository.getOperatorProperties(value).params == 0;
	}
	
	/**
	 * Method to evaluate this operator, function, variable, or constant.<br>
	 * It takes a variable number of operands as parameter depending on the number of operands required for this operator.
	 * 
	 * @param operands Variable number of operands required by this operator
	 * @return The evaluated result as another operand
	 */
	public Operand evaluate(Operand ... operands) {
		// function
		if (this.isFunction()) {
			return OperatorRepository.getOperatorProperties(value).function.evaluate(operands);
		}
		
		// operator, variable, or constant
		switch (value) {
			case "+": return new Operand(String.valueOf(operands[0].toDouble() + operands[1].toDouble()));
			case "-": return new Operand(String.valueOf(operands[0].toDouble() - operands[1].toDouble()));
			
			case "*": return new Operand(String.valueOf(operands[0].toDouble() * operands[1].toDouble()));
			case "/": return new Operand(String.valueOf(operands[0].toDouble() / operands[1].toDouble()));
			case "%": return new Operand(String.valueOf(operands[0].toDouble() % operands[1].toDouble()));
			case "^": return new Operand(String.valueOf(Math.pow(operands[0].toDouble(), operands[1].toDouble())));
			
			case "uminus": return new Operand(String.valueOf(-operands[0].toDouble()));
			case "uplus": return new Operand(String.valueOf(+operands[0].toDouble()));
			
			case "rand": return new Operand(String.valueOf(Math.random()));
			case "pi": return new Operand(String.valueOf(Math.PI));
			case "e": return new Operand(String.valueOf(Math.E));
			
			default: throw new Expr4jException("Unsupported operator");
		}
		
	}
	
	/**
	 * Method to compare this operator to another operator on the basis of precedence.
	 */
	@Override
	public int compareTo(Operator other) {
		// compare the precedences of the two operators
		return OperatorRepository.getOperatorProperties(this.value).precedence
				- OperatorRepository.getOperatorProperties(other.value).precedence;
	}
	
	/**
	 * The <code>Properties</code> class represents the properties of an operator.<br>
	 * It contains information about the number of parameters supported, the precedence, and the associativity.<br>
	 * It also helps determine if an operator is a function or not and provides the function implementation.
	 * 
	 * @author Pratanu Mandal
	 * @since 0.0.1
	 *
	 */
	public static class Properties {

		/**
		 * Number of parameters required by this operator.
		 */
		public final int params;
		
		/**
		 * The precedence of this operator.<br>
		 * Precedence ranges from 1 to infinity, in ascending order, i.e, with 1 being lowest precedence possible.<br>
		 * Although parenthesis and comma have 0 precedence, they are a special case and are evaluated separately.
		 */
		public final int precedence;
		
		/**
		 * The associativity of this operator.
		 */
		public final Associativity associativity;
		
		/**
		 * Function implementation if this operator is a function.<br>
		 * Otherwise null.
		 */
		public final Function function;
		
		/**
		 * Parameterized constructor taking number of parameters and precedence.<br>
		 * Associativity is set to LEFT and function is set to null.
		 * 
		 * @param params Number of parameters required by this operator
		 * @param precedence The precedence value of this operator
		 */
		public Properties(int params, int precedence) {
			this(params, precedence, Associativity.LEFT, null);
		}
		
		/**
		 * Parameterized constructor taking number of parameters, precedence, and associativity.<br>
		 * Function is set to null.
		 * 
		 * @param params Number of parameters required by this operator
		 * @param precedence The precedence value of this operator
		 * @param associativity The associativity of this operator
		 */
		public Properties(int params, int precedence, Associativity associativity) {
			this(params, precedence, associativity, null);
		}
		
		/**
		 * Parameterized constructor taking number of parameters, precedence, and function.<br>
		 * If function is not null, associativity is set to NO, otherwise LEFT.
		 * 
		 * @param params Number of parameters required by this operator
		 * @param precedence The precedence value of this operator
		 * @param function The function implementation for this operator
		 */
		public Properties(int params, int precedence, Function function) {
			this(params, precedence, function != null ? Associativity.NO : Associativity.LEFT, function);
		}
		
		/**
		 * Parameterized constructor taking number of parameters, precedence, associativity, and function.<br>
		 * It is recommended not to use this constructor, instead using one of the preset constructors if possible.
		 * 
		 * @param params Number of parameters required by this operator
		 * @param precedence The precedence value of this operator
		 * @param associativity The associativity of this operator
		 * @param function The function implementation for this operator
		 */
		public Properties(int params, int precedence, Associativity associativity, Function function) {
			this.params = params;
			this.precedence = precedence;
			this.associativity = associativity;
			this.function = function;
			
			if (this.params == -1 && this.function == null) {
				throw new Expr4jException("Only functions can have variable number of parameter.");
			}
		}
		
		/**
		 * The <code>Associativity</code> enum represents the associativity property of an operator.<br>
		 * It can be of three types: LEFT associative, RIGHT associative and NO associative.
		 * 
		 * @author Pratanu Mandal
		 * @since 0.0.1
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
