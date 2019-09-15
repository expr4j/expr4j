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

package tk.pratanumandal.expr4j;

import tk.pratanumandal.expr4j.shuntingyard.ShuntingYard;
import tk.pratanumandal.expr4j.shuntingyard.ShuntingYardDualStack;
import tk.pratanumandal.expr4j.shuntingyard.ShuntingYardExpressionTree;

/**
 * The <code>ExpressionEvaluator</code> class is the logical entry point to the library.<br><br>
 * It acts as a wrapper for shunting yard implementations allowing to easily choose the implementation to be used.
 * 
 * <pre>
 * {@code
 * 	// using default implementation
 * 	String expr = ...;
 * 	ExpressionEvaluator exprEval = new ExpressionEvaluator();
 * 	double result = exprEval.evaluate(expr);
 * 	
 * 	// using dual stack evaluator
 * 	String expr = ...;
 * 	ExpressionEvaluator exprEval = new ExpressionEvaluator(Evaluator.DUAL_STACK);
 * 	double result = exprEval.evaluate(expr);
 * 
 * 	// using expression tree evaluator
 * 	String expr = ...;
 * 	ExpressionEvaluator exprEval = new ExpressionEvaluator(Evaluator.EXPRESSION_TREE);
 * 	double result = exprEval.evaluate(expr);
 * }
 * </pre>
 * 
 * @author Pratanu Mandal
 * @since 0.0.2
 *
 */
public class ExpressionEvaluator {

	/**
	 * Shunting yard implementation instance to be used to evaluate expression.
	 */
	public final ShuntingYard SY;
	
	/**
	 * No-argument constructor.<br>
	 * Use Dual Stack implementation of Shunting Yard as the default implementation.
	 */
	public ExpressionEvaluator() {
		this(Evaluator.DUAL_STACK);
	}
	
	/**
	 * Parameterized constructor.<br>
	 * Use the specified Shunting Yard implementation.
	 * 
	 * @param evaluator Shunting Yard implementation
	 */
	public ExpressionEvaluator(Evaluator evaluator) {
		if (evaluator == Evaluator.DUAL_STACK) {
			SY = new ShuntingYardDualStack();
		}
		else if (evaluator == Evaluator.EXPRESSION_TREE) {
			SY = new ShuntingYardExpressionTree();
		}
		else {
			SY = null;
		}
	}
	
	/**
	 * Method to evaluate an expression.<br>
	 * This method acts as the single point of access for expression evaluation.
	 * 
	 * @param expr Expression string
	 * @return Result of expression evaluation as a double
	 */
	public double evaluate(String expr) {
		return SY.evaluate(expr);
	}
	
	/**
	 * The <code>Evaluator</code> enum represents the Shunting Yard implementation to be used for evaluating expressions.<br>
	 * It can be of two types: DUAL_STACK, and EXPRESSION_TREE.
	 * 
	 * @author Pratanu Mandal
	 * @since 0.0.2
	 *
	 */
	public enum Evaluator {
		/**
		 * Dual Stack implementation of the Shunting Yard algorithm.
		 */
		DUAL_STACK,
		
		/**
		 * Expression Tree implementation of the Shunting Yard algorithm.
		 */
		EXPRESSION_TREE
	}
	
}
