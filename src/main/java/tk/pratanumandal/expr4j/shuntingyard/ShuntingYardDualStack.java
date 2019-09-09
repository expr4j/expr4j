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

package tk.pratanumandal.expr4j.shuntingyard;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Stack;

import tk.pratanumandal.expr4j.OperatorRepository;
import tk.pratanumandal.expr4j.common.Constants;
import tk.pratanumandal.expr4j.token.Operand;
import tk.pratanumandal.expr4j.token.Operator;
import tk.pratanumandal.expr4j.token.Operator.Properties.Associativity;

/**
 * The <code>ShuntingYardDualStack</code> class provides an implementation of the Shunting Yard algorithm using Dual Stacks.<br><br>
 * 
 * Two stacks are used to immediately evaluate the expression without generating the postfix (or RPN) expression.<br>
 * This is the recommended implementation since theoretically it should use less memory and require less time due to lesser complexity.
 * 
 * @author Pratanu Mandal
 *
 */
public class ShuntingYardDualStack extends ShuntingYard {
	
	/**
	 * Stack to hold the operands.
	 */
	protected Stack<Operand> operandStack;
	
	/**
	 * Stack to hold the operators.
	 */
	protected Stack<Operator> operatorStack;
	
	/**
	 * Method to evaluate the expression string and return the result as an operand.
	 * 
	 * @param expr Expression string
	 * @return Result of expression evaluation as an operand
	 */
	protected Operand evaluateExpr(String expr) {
		// remove all whitespace except if whitespace is present between operands
		expr = expr.replaceAll("(?!\\d|\\+|\\-)\\s+(?!\\d|\\.)", "");
		
		operandStack = new Stack<>();
		operatorStack = new Stack<>();
		
		String token = new String();
		String lastToken = null;
		
		Stack<Operator> functions = new Stack<>();
		Stack<Integer> functionParams = new Stack<>();
		
		for (int i = 0; i < expr.length(); i++) {
			char ch = expr.charAt(i);
			char chNext = (i + 1 < expr.length()) ? expr.charAt(i + 1) : '\u0000';
			
			if (OperatorRepository.isOperator(token + ch) && (!OperatorRepository.isFunction(token + ch) || chNext == '(')) {
				// add to operator stack
				Operator op = new Operator(token + ch);
				
				// handle unary - and + operators
				if (((ch == '-' || ch == '+') && chNext != ' ' &&
					(lastToken == null || (OperatorRepository.isOperator(lastToken) && !lastToken.equals(")"))))) {
					if (ch == '-') op = new Operator("uminus");
					if (ch == '+') op = new Operator("uplus");
				}
				
				if (op.getOperandCount() == 0) {
					if (op.value.equals(",")) {
						if (functions.isEmpty() || functionParams.peek() >= functions.peek().getOperandCount() - 1) {
							throw new RuntimeException("Invalid expression");
						}
						else {
							functionParams.push(functionParams.pop() + 1);
						}
					}
					else {
						Operand eval = op.evaluate();
						operandStack.push(eval);
						lastToken = eval.value;
						token = new String();
						continue;
					}
				}
				else if (op.value.equals("(")) {
					operatorStack.push(op);
					if (lastToken != null && OperatorRepository.isFunction(lastToken)) {
						functions.push(new Operator(lastToken));
						functionParams.push(0);
					}
					else if (!functions.isEmpty()) {
						functions.push(functions.peek());
						functionParams.push(0);
					}
				}
				else if (op.value.equals(")")) {
					boolean flag = false;
					while (!operatorStack.isEmpty()) {
						if (operatorStack.peek().value.equals("(")) {
							operatorStack.pop();
							flag = true;
							break;
						}
						// evaluate top of stack
						evaluateTOS();
					}
					if (!flag) {
						throw new RuntimeException("Unmatched number of paranthesis");
					}
					if (!functions.empty()) {
						functions.pop();
						functionParams.pop();
					}
				}
				else {
					while (!operatorStack.isEmpty() &&
							(operatorStack.peek().compareTo(op) > 0 ||
							 (operatorStack.peek().compareTo(op) == 0 &&
							  operatorStack.peek().getAssociativity() == Associativity.LEFT))) {
						// evaluate top of stack
						evaluateTOS();
					}
					operatorStack.push(op);
				}
				
				lastToken = token + ch;
				token = new String();
			}
			else if (Operand.isOperand(token + ch) && (chNext != 'e' && chNext != 'E') &&
						(chNext == '\u0000' || !Operand.isOperand(token + ch + chNext))) {
				// add to operandStack
				operandStack.push(new Operand(token + ch));
				
				lastToken = token + ch;
				token = new String();
			}
			else {
				token += ch;
			}
		}
		
		if (!token.isEmpty()) {
			throw new RuntimeException("Invalid expression");
		}
		
		while (!operatorStack.isEmpty()) {
			if (operatorStack.peek().value.equals("(")) {
				throw new RuntimeException("Unmatched number of paranthesis");
			}
			// evaluate top of stack
			evaluateTOS();
		}
		
		if (operandStack.size() > 1) {
			throw new RuntimeException("Invalid expression");
		}
		
		return operandStack.pop();
	}
	
	/**
	 * Method to evaluate the operator at the top of the operator stack.
	 */
	protected void evaluateTOS() {
		
		Operator operator = operatorStack.pop();
		
		Operand[] operands = new Operand[operator.getOperandCount()];
		
		for (int j = 0; j < operands.length; j++) {
			if (operandStack.empty()) {
				throw new RuntimeException("Invalid expression");
			}
			
			operands[operands.length - j - 1] = operandStack.pop();
		}
		
		Operand operand = operator.evaluate(operands);
		operandStack.push(operand);
	}
	
	/**
	 * Method to evaluate an expression.<br>
	 * This method acts as the single point of access for expression evaluation.
	 * 
	 * @param expr Expression string
	 * @return Result of expression evaluation as a double
	 */
	@Override
	public double evaluate(String expr) {
		try {
			// evaluate the expression
			double result = this.evaluateExpr(expr).toDouble();
			
			if (!Double.isFinite(result)) return result;
			
			// round to n decimal places to preserve accuracy
			BigDecimal bd = BigDecimal.valueOf(result);
			bd = bd.setScale(Constants.PRECISION, RoundingMode.HALF_UP);
			
			// return the result
			return bd.doubleValue();
		}
		finally {
			// expression evaluation can be a memory expensive process
			// clean up
			this.operandStack = null;
			this.operatorStack = null;
		}
	}
	
}
