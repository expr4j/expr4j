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
 * The <code>Operand&lt;T&gt;</code> class represents operands in the expression.<br>
 * It acts as a wrapper for value of type <code>T</code>.
 * 
 * @author Pratanu Mandal
 * @since 1.0
 *
 * @param <T> The type of operand
 */
public class Operand<T> implements Token {
	
	/**
	 * Value of the operand.
	 */
	public final T value;

	/**
	 * Parameterized constructor.
	 * 
	 * @param value Value of the operand
	 */
	public Operand(T value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value.toString();
	}
	
}
