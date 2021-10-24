/**
 * Copyright 2021 Pratanu Mandal
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

package in.pratanumandal.expr4j.token;

/**
 * The <code>Operator<T></code> class represents operators in the expression.
 * 
 * @author Pratanu Mandal
 * @since 1.0
 *
 * @param <T> The type of operand
 */
public class Operator<T> extends Executable<T> implements Comparable<Operator<T>> {
	
	public static final String UNARY_PLUS = "uplus";
	public static final String UNARY_MINUS = "uminus";
	public static final String IMPLICIT_MULTIPLICATION = "imult";
	
	/**
	 * The type of operator.
	 */
	public final OperatorType operatorType;
	
	/**
	 * The precedence of this operator.<br>
	 * Precedence ranges from 1 to MAX_INT, in ascending order, i.e, with 1 being lowest precedence possible.
	 */
	public final int precedence;

	/**
	 * Parameterized constructor.
	 * 
	 * @param label Label of the operator
	 * @param operatorType Type of the operator
	 * @param precedence Precedence of the operator
	 * @param operation Operation performed by the operator
	 */
	public Operator(String label, OperatorType operatorType, int precedence, Operation<T> operation) {
		super(label, operation);
		this.operatorType = operatorType;
		this.precedence = precedence;
	}

	/**
	 * Method to compare this operator to another operator on the basis of precedence.
	 */
	@Override
	public int compareTo(Operator<T> other) {
		// compare the precedences and associativity of the two operators
		return (this.precedence == other.precedence) ?
				(this.operatorType == OperatorType.INFIX || this.operatorType == OperatorType.SUFFIX ? 1 : -1)
				: this.precedence - other.precedence;
	}

	@Override
	public String toString() {
		return label;
	}
	
	/**
	 * The <code>OperatorType</code> enum represents the type of an operator.<br>
	 * It can be of four types: PREFIX, SUFFIX, INFIX, and INFIX_RTL.<br><br>
	 * 
	 * PREFIX - must be applied before an operand, right associative.<br>
	 * SUFFIX - must be applied after an operand, left associative.<br>
	 * INFIX - must be applied in between two operands, left associative.<br>
	 * INFIX_RTL - must be applied in between two operands, right associative.
	 * 
	 * @author Pratanu Mandal
	 * @since 1.0
	 *
	 */
	public static enum OperatorType {
		PREFIX,
		
		SUFFIX,
		
		INFIX, 
		
		INFIX_RTL
	}
	
}
