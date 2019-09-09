/**
 * Copyright 2019 Pratanu Mandal
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in the
 * Software without restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the
 * following conditions:
 * 
 * 		The above copyright notice and this permission notice shall be included
 * 		in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
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
	 *
	 */
	public enum Evaluator {
		DUAL_STACK, EXPRESSION_TREE
	}
	
}
