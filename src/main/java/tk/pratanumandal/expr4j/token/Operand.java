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
 * The <code>Operand</code> class represents the operands in the expression.<br>
 * It acts as a wrapper for double value operands.
 * 
 * @author Pratanu Mandal
 * @since 0.0.1
 *
 */
public class Operand<T> implements Token {
	
	public final T value;

	/**
	 * Parameterized constructor.
	 * 
	 * @param value
	 */
	public Operand(T value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value.toString();
	}
	
}
