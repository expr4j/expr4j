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

package tk.pratanumandal.expr4j.token;

/**
 * The <code>Function<T></code> class represents functions in the expression.
 * 
 * @author Pratanu Mandal
 * @since 1.0
 *
 * @param <T> The type of operand
 */
public class Function<T> extends Executable<T> {
	
	/**
	 * Constant to indicate that the function support variable number of parameters.
	 */
	public static final int VARIABLE_PARAMETERS = -1;
	
	/**
	 * Number of parameters.
	 */
	public final int parameters;
	
	/**
	 * Parameterized constructor.
	 * 
	 * @param label Label of the function
	 * @param parameters Number of parameters
	 * @param operation Operation performed by the function
	 */
	public Function(String label, int parameters, Operation<T> operation) {
		super(label, operation);
		this.parameters = parameters;
	}
	
	/**
	 * Parameterized constructor.<br>
	 * This constructor creates a function with variable number of parameters.
	 * 
	 * @param label Label of the function
	 * @param operation Operation performed by the function
	 */
	public Function(String label, Operation<T> operation) {
		this(label, VARIABLE_PARAMETERS, operation);
	}

	@Override
	public String toString() {
		return label;
	}

}
