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

import java.util.List;

/**
 * The <code>Executable<T></code> class represents tokens that can be executed in the expression.<br>
 * Executables can be functions and operators.
 * 
 * @author Pratanu Mandal
 * @since 1.0
 *
 * @param <T> The type of operand
 */
public abstract class Executable<T> implements Token {
	
	/**
	 * Label of the executable.
	 */
	public final String label;
	
	/**
	 * Operation performed by the executable.
	 */
	public final Operation<T> operation;

	/**
	 * Parameterized constructor.
	 * 
	 * @param label Label of the executable
	 * @param operation Operation performed by the executable
	 */
	public Executable(String label, Operation<T> operation) {
		this.label = label;
		this.operation = operation;
	}
	
	/**
	 * Evaluate the executable.
	 * 
	 * @param operands List of operands
	 * @return Evaluated result
	 */
	public T evaluate(List<T> operands) {
		return this.operation.execute(operands);
	}
	
	/**
	 * The <code>Operation<T></code> functional interface represents an operation that can be executed.
	 * 
	 * @author Pratanu Mandal
	 * @since 1.0
	 *
	 * @param <T> The type of operand
	 */
	@FunctionalInterface
	public interface Operation<T> {
		
		/**
		 * Execute the operation.
		 * 
		 * @param operands List of operands
		 * @return Evaluated result
		 */
		public abstract T execute(List<T> operands);

	}

}
