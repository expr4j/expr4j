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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Stack;

import tk.pratanumandal.expr4j.Operator.Properties.Associativity;

/**
 * The <code>ShuntingYardDualStack</code> class provides an implementation of the Shunting Yard algorithm using Expression Tree.<br><br>
 * 
 * An expression tree is created from the postfix (or RPN) expression.<br>
 * The expression tree is then parsed to evaluate the expression.
 * 
 * @author Pratanu Mandal
 *
 */
public class ShuntingYardTree extends ShuntingYard {
	
	/**
	 * The <code>Node</code> class represents a node of the expression tree.<br><br>
	 * 
	 * @author Pratanu Mandal
	 *
	 */
	protected class Node {
		/**
		 * Left child of this node.
		 */
		protected Node left;
		
		/**
		 * Right child of this node.
		 */
		protected Node right;
		
		/**
		 * Token contained in this node.<br>
		 * A token can be an operand, operator, function, variable, or constant.
		 */
		protected Token token;
	}
	
	/**
	 * Root node of the expression tree.
	 */
	protected Node root;
	
	/**
	 * Stack containing the postfix expression.
	 */
	protected Stack<Token> postfix;
	
	/**
	 * Method to create the postfix expression from the infix expression.
	 * 
	 * @param expr Expression string
	 */
	protected void init(String expr) {
		// remove all whitespace except if whitespace is present between operands
		expr = expr.replaceAll("(?!\\d|\\+|\\-)\\s+(?!\\d|\\.)", "");
		
		postfix = new Stack<>();
		Stack<Operator> opStack = new Stack<>();
		
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
						postfix.push(eval);
						lastToken = eval.value;
						token = new String();
						continue;
					}
				}
				else if (op.value.equals("(")) {
					opStack.push(op);
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
					while (!opStack.isEmpty()) {
						if (opStack.peek().value.equals("(")) {
							opStack.pop();
							flag = true;
							break;
						}
						postfix.push(opStack.pop());
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
					while (!opStack.isEmpty() &&
							(opStack.peek().compareTo(op) > 0 ||
							 (opStack.peek().compareTo(op) == 0 &&
							  opStack.peek().getAssociativity() == Associativity.LEFT))) {
						postfix.push(opStack.pop());
					}
					opStack.push(op);
				}
				
				lastToken = token + ch;
				token = new String();
			}
			else if (Operand.isOperand(token + ch) && (chNext != 'e' && chNext != 'E') &&
						(chNext == '\u0000' || !Operand.isOperand(token + ch + chNext))) {
				// add to postfix
				postfix.push(new Operand(token + ch));
				
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
		
		while (!opStack.isEmpty()) {
			if (opStack.peek().value.equals("(")) {
				throw new RuntimeException("Unmatched number of paranthesis");
			}
			postfix.push(opStack.pop());
		}
	}
	
	/**
	 * Method to form the expression tree recursively.
	 * 
	 * @param node Current node of the expression tree
	 * @param token Token to be inserted
	 * @return true if token could be inserted, otherwise false
	 */
	protected boolean formTree(Node node, Token token) {
		if (node.token instanceof Operator) {
			Operator operator = (Operator) node.token;
			
			boolean unary = false;
			if (operator.getOperandCount() == 1) {
				unary = true;
			} else {
				unary = false;
			}
			
			if (unary) {
				if (node.left == null) {
					node.left = new Node();
					node.left.token = token;
					return true;
				}
				else {
					return formTree(node.left, token);
				}
			}
			else {
				if (node.right == null) {
					node.right = new Node();
					node.right.token = token;
					return true;
				}
				else if (formTree(node.right, token)) {
					return true;
				}
				else if (node.left == null) {
					node.left = new Node();
					node.left.token = token;
					return true;
				}
				else if (formTree(node.left, token)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Method to form the expression tree.
	 */
	protected void formTree() {
		while (!postfix.isEmpty()) {
			Token token = postfix.pop();
			
			if (root == null) {
				Node node = new Node();
				node.token = token;
				root = node;
			}
			else {
				boolean flag = formTree(root, token);
				
				if (!flag) {
					throw new RuntimeException("Invalid expression");
				}
			}
		}
	}
	
	/**
	 * Method to recursively evaluate the expression tree and return the result as an operand.
	 * 
	 * @param node Current node of the expression tree
	 * @return Result of expression evaluation as an operand
	 */
	protected Operand evaluate(Node node) {
		if (node.token instanceof Operator) {
			Operator operator = (Operator) node.token;
			
			boolean unary = false;
			if (operator.getOperandCount() == 1) {
				unary = true;
			}
			
			if (node.left == null) {
				throw new RuntimeException("Invalid expression");
			}
			
			Operand leftOp = null;
			if (node.left.token instanceof Operand) {
				leftOp = (Operand) node.left.token;
			}
			else if (node.left.token instanceof Operator) {
				leftOp = evaluate(node.left);
			}
			
			Operand rightOp = null;
			if (!unary && node.right.token instanceof Operand) {
				rightOp = (Operand) node.right.token;
			}
			else if (!unary && node.right.token instanceof Operator) {
				rightOp = evaluate(node.right);
			}
			
			return operator.evaluate(leftOp, rightOp);
		}
		else {
			return (Operand) node.token;
		}
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
			// initialize expression
			this.init(expr);
			
			// form the tree
			this.formTree();
			
			// evaluate the expression
			double result = this.evaluate(root).toDouble();
			
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
			this.root = null;
			this.postfix = null;
		}
	}
	
}
