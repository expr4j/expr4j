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

package tk.pratanumandal.expr4j.token;

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
public class Operator<T> extends Executable<T> implements Comparable<Operator<T>> {
	
	public static final String UNARY_PLUS = "uplus";
	public static final String UNARY_MINUS = "uminus";
	
	/**
	 * Number of parameters required by this operator.
	 */
	public final OperatorType operatorType;
	
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
	
	public Operator(String label, int precedence, Operation<T> operation) {
		this(label, OperatorType.INFIX, precedence, Associativity.LEFT, operation);
	}
	
	public Operator(String label, OperatorType operatorType, int precedence, Operation<T> operation) {
		this(label, operatorType, precedence, operatorType == OperatorType.PREFIX ? Associativity.RIGHT : Associativity.LEFT, operation);
	}

	/**
	 * Parameterized constructor.
	 * 
	 * @param label
	 * @param operatorType
	 * @param precedence
	 * @param associativity
	 */
	public Operator(String label, OperatorType operatorType, int precedence, Associativity associativity, Operation<T> operation) {
		super(label, operation);
		this.operatorType = operatorType;
		this.precedence = precedence;
		this.associativity = associativity;
	}

	/**
	 * Method to compare this operator to another operator on the basis of precedence.
	 */
	@Override
	public int compareTo(Operator<T> other) {
		// compare the precedences and associativity of the two operators
		return (this.precedence == other.precedence) ?
				(this.associativity == Associativity.LEFT ? 1 : (this.associativity == Associativity.RIGHT ? -1 : 0))
				: this.precedence - other.precedence;
	}
	
	//public abstract Operand<T> evaluate(List<Operand<T>> operands);

	@Override
	public String toString() {
		return label;
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
		NONE
	}
	
	/**
	 * The <code>Associativity</code> enum represents the associativity property of an operator.<br>
	 * It can be of three types: LEFT associative, RIGHT associative and NO associative.
	 * 
	 * @author Pratanu Mandal
	 * @since 0.0.1
	 *
	 */
	public static enum OperatorType {
		PREFIX,
		
		SUFFIX,
		
		INFIX
	}
	
}
