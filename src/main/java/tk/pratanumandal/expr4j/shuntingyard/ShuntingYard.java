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

package tk.pratanumandal.expr4j.shuntingyard;

/**
 * The <code>ShuntingYard</code> abstract class provides a blueprint for ShuntingYard implementations.<br><br>
 * It provides only one method evaluate() which must be overridden by any implementing class.<br>
 * This evaluate() method should act as the single point of access for the implementation.
 * 
 * @author Pratanu Mandal
 * @since 0.0.1
 *
 */
public abstract class ShuntingYard {
	
	/**
	 * No-Argument Constructor.
	 */
	public ShuntingYard() {}
	
	/**
	 * Method to evaluate an expression.<br>
	 * This method should act as the single point of access for the implementation.
	 * 
	 * @param expr Expression string
	 * @return Result of expression evaluation as a double
	 */
	public abstract double evaluate(String expr);

}
