/**
 * Copyright 2023 Pratanu Mandal
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

import in.pratanumandal.expr4j.exception.Expr4jException;

/**
 * The <code>Operator&lt;T&gt;</code> class represents operators in the expression.
 * 
 * @author Pratanu Mandal
 * @since 1.0
 *
 * @param <T> The type of operand
 */
public class Operator<T> extends Executable<T> implements Comparable<Operator<T>> {
	
	/**
	 * The type of operator.
	 */
	public final OperatorType type;
	
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
		this.type = operatorType;
		this.precedence = precedence;
		
		if (this.precedence < 1) {
			throw new Expr4jException("Invalid precedence: " + this.precedence);
		}
	}

	/**
	 * Method to compare this operator to another operator on the basis of precedence.
	 */
	@Override
	public int compareTo(Operator<T> other) {
		// compare the precedences and associativity of the two operators
		return (this.precedence == other.precedence) ?
				(this.type == OperatorType.INFIX || this.type == OperatorType.POSTFIX ? 1 : -1)
				: other.precedence - this.precedence;
	}

	@Override
	public String toString() {
		return label;
	}

}
