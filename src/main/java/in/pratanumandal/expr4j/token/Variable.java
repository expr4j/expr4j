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

/**
 * The <code>Variable</code> class represents the variables in the expression.
 * 
 * @author Pratanu Mandal
 * @since 1.0
 *
 */
public class Variable implements Token {
	
	/**
	 * Label of the variable.
	 */
	public final String label;

	/**
	 * Parameterized constructor.
	 * 
	 * @param label Label of the variable
	 */
	public Variable(String label) {
		this.label = label;
	}

	@Override
	public String toString() {
		return label;
	}
	
}
