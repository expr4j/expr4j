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
 * The <code>Branch&lt;T&gt;</code> class represents branches in the expression.
 * 
 * @author Pratanu Mandal
 * @since 1.0
 *
 * @param <T> The type of operand
 */
public class Branch<T> implements Token {

	/**
	 * Constant to indicate that the branch supports variable number of parameters.
	 */
	public static final int VARIABLE_PARAMETERS = -1;

	/**
	 * Label of the branch.
	 */
	public final String label;

	/**
	 * Number of parameters.
	 */
	public final int parameters;

	/**
	 * Choice evaluated by the branch.
	 */
	public final Choice<T> choice;

	/**
	 * Parameterized constructor.
	 *
	 * @param label Label of the branch
	 * @param parameters Number of parameters
	 * @param choice Choice evaluated by the branch
	 */
	public Branch(String label, int parameters, Choice<T> choice) {
		this.label = label;
		this.parameters = parameters;
		this.choice = choice;

		if (this.parameters != Branch.VARIABLE_PARAMETERS && this.parameters < 3) {
			throw new Expr4jException("Invalid number of parameters: " + this.parameters);
		}
	}

	/**
	 * Parameterized constructor.<br>
	 * This constructor creates a branch with variable number of parameters.
	 *
	 * @param label Label of the branch
	 * @param choice Choice evaluated by the branch
	 */
	public Branch(String label, Choice<T> choice) {
		this(label, VARIABLE_PARAMETERS, choice);
	}

	/**
	 * Evaluate the branch.
	 *
	 * @param operand The operand
	 * @return Evaluated result
	 */
	public int evaluate(T operand) {
		return this.choice.choose(operand);
	}

	@Override
	public String toString() {
		return "Branch{" +
				"label='" + label + '\'' +
				", parameters=" + parameters +
				'}';
	}

}
