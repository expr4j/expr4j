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
 * The <code>Separator</code> class represents the separators in the expression.<br>
 * 
 * @author Pratanu Mandal
 * @since 1.0
 *
 */
public enum Separator implements Token {

	/** Open bracket */
	OPEN_BRACKET("("),

	/** Close bracket */
	CLOSE_BRACKET(")"),

	/** Comma */
	COMMA(",");
	
	/**
	 * Label of the separator.
	 */
	public final String label;

	/**
	 * Parameterized constructor.
	 *
	 * @param label Label of the separator.
	 */
	Separator(String label) {
		this.label = label;
	}

	/**
	 * Get the separator label.
	 *
	 * @return The label
	 */
	public String getLabel() {
		return label;
	}

	@Override
	public String toString() {
		return label;
	}

	/**
	 * Get the separator with specified label.
	 *
	 * @param label The label
	 * @return The separator
	 */
	public static Separator getSeparator(String label) {
		switch (label) {
			case "(": return OPEN_BRACKET;
			case ")": return CLOSE_BRACKET;
			case ",": return COMMA;
			default: throw new Expr4jException("Invalid separator");
		}
	}

}
