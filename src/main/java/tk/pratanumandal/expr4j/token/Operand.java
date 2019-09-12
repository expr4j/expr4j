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
public class Operand extends Token {

	/**
	 * Parameterized constructor.
	 * 
	 * @param value The double value operand as a String
	 */
	public Operand(String value) {
		super(value);
	}
	
	/**
	 * Parameterized constructor.
	 * 
	 * @param value The double value operand
	 */
	public Operand(double value) {
		super(String.valueOf(value));
	}

	/**
	 * Convert operand String to double.
	 * 
	 * @return the double value of the operand
	 */
	public double toDouble() {
		return Double.parseDouble(value);
	}
	
	/**
	 * Utility method to determine if a string is an operand.
	 * 
	 * @param op Operand value as a string
	 * @return true if op is an operand, otherwise false
	 */
	public static boolean isOperand(String op) {
		try {
			Double.parseDouble(op);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
}
