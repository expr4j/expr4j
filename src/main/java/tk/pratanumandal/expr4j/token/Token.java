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
 * The <code>Token</code> class represents any token in expressions.<br><br>
 * A token is the smallest indivisible unit of any expression.<br>
 * Tokens can be operands, operators, functions, variables, or constants.
 * 
 * @author Pratanu Mandal
 * @since 0.0.1
 *
 */
public abstract class Token {

	/**
	 * The string value of the token.
	 */
	public String value;

	/**
	 * Parameterized constructor.
	 * 
	 * @param value The value of the token as a String
	 */
	public Token(String value) {
		super();
		this.value = value;
	}
	
	/**
	 * Method to convert token object to string.
	 * 
	 * @return The string value of this token
	 */
	@Override
	public String toString() {
		return value;
	}
	
}
