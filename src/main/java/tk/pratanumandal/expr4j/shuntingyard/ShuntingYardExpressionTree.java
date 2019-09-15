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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Stack;

import tk.pratanumandal.expr4j.OperatorRepository;
import tk.pratanumandal.expr4j.common.Expr4jConstants;
import tk.pratanumandal.expr4j.exception.Expr4jException;
import tk.pratanumandal.expr4j.token.Operand;
import tk.pratanumandal.expr4j.token.Operator;
import tk.pratanumandal.expr4j.token.Token;
import tk.pratanumandal.expr4j.token.Operator.Properties.Associativity;

/**
 * The <code>ShuntingYardExpressionTree</code> class provides an implementation of the Shunting Yard algorithm using Expression Tree.<br><br>
 * 
 * An expression tree is created from the postfix (or RPN) expression.<br>
 * The expression tree is then parsed to evaluate the expression.
 * 
 * @author Pratanu Mandal
 * @since 0.0.2
 *
 */
public class ShuntingYardExpressionTree extends ShuntingYard {
	
	/**
	 * The <code>Node</code> class represents a node of the expression tree.<br><br>
	 * 
	 * @author Pratanu Mandal
	 *
	 */
	protected class Node {
		/**
		 * Children of this node.
		 */
		protected final Node[] children;
		
		/**
		 * Token contained in this node.<br>
		 * A token can be an operand, operator, function, variable, or constant.
		 */
		protected final Token token;

		/**
		 * Parameterized constructor.
		 * 
		 * @param token The token in this node
		 */
		public Node(Token token) {
			this.token = token;
			if (token instanceof Operator) {
				this.children = new Node[((Operator) token).getOperandCount()];
			}
			else {
				this.children = null;
			}
		}
	}
	
	/**
	 * Root node of the expression tree.
	 */
	protected Node root;
	
	/**
	 * Stack to hold the postfix (RPN) expression.
	 */
	protected Stack<Token> postfix;
	
	/**
	 * Stack to hold the operators.
	 */
	protected Stack<Operator> operatorStack;
	
	/**
	 * No-Argument Constructor.
	 */
	public ShuntingYardExpressionTree() {}
	
	/**
	 * Method to create the postfix expression from the infix expression.
	 * 
	 * @param expr Expression string
	 */
	protected void init(String expr) {
		// remove all whitespace except if whitespace is present between operands
		expr = expr.replaceAll("(?!\\d|\\+|\\-)\\s+(?!\\d|\\.)", "");
		
		// do not allow empty expressions
		if (expr.isEmpty()) {
			throw new Expr4jException("Invalid expression");
		}
		
		postfix = new Stack<>();
		operatorStack = new Stack<>();
		
		String token = new String();
		String lastToken = null;
		String realLastToken = null;
		
		Stack<Operator> functions = new Stack<>();
		Stack<Integer> functionParams = new Stack<>();
		
		for (int i = 0; i < expr.length(); i++) {
			char ch = expr.charAt(i);
			char chNext = (i + 1 < expr.length()) ? expr.charAt(i + 1) : '\u0000';
			
			if (OperatorRepository.isOperator(token + ch) &&
				((OperatorRepository.isVariableOrConstant(token + ch) &&
				OperatorRepository.isOperator(String.valueOf(chNext)) &&
				!OperatorRepository.isFunction(String.valueOf(chNext))) ||
				!OperatorRepository.isFunction(token + ch) ||
				chNext == '(')) {
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
						if (functions.isEmpty() || (functions.peek().getOperandCount() != -1 &&
								functionParams.peek() >= functions.peek().getOperandCount() - 1)) {
							throw new Expr4jException("Invalid expression");
						}
						else {
							functionParams.push(functionParams.pop() + 1);
							evaluateParenthesis();
							operatorStack.push(new Operator("("));
						}
					}
					else {
						Operand eval = op.evaluate();
						postfix.push(eval);
						lastToken = eval.value;
						realLastToken = op.value;
						token = new String();
						continue;
					}
				}
				else if (op.value.equals("(")) {
					operatorStack.push(op);
					if ((lastToken == null && chNext == ')') ||
						(lastToken != null && !OperatorRepository.isFunction(realLastToken) &&
						!OperatorRepository.isVariableOrConstant(realLastToken) &&
						!realLastToken.equals("(")  && chNext == ')')) {
						throw new Expr4jException("Invalid use of parenthesis");
					}
					if (lastToken != null && OperatorRepository.isFunction(lastToken)) {
						functions.push(new Operator(lastToken));
						functionParams.push(0);
						operatorStack.push(new Operator("("));
					}
					else if (!functions.isEmpty()) {
						functions.push(op);
						functionParams.push(0);
					}
				}
				else if (op.value.equals(")")) {
					evaluateParenthesis();
					if (!functions.empty()) {
						if (functions.peek().isFunction()) {
							evaluateParenthesis();
							if (functions.peek().getOperandCount() == -1) {
								Operator tosOp = operatorStack.pop();
								int paramsCount = functionParams.peek() + 1;
								tosOp = new Operator(tosOp.value) {
									@Override
									public int getOperandCount() {
										return paramsCount;
									}
								};
								operatorStack.push(tosOp);
							}
							postfix.push(operatorStack.pop());
						}
						// pop function and parameter count
						functions.pop();
						functionParams.pop();
					}
				}
				else {
					while (!operatorStack.isEmpty() &&
							(operatorStack.peek().compareTo(op) > 0 ||
							 (operatorStack.peek().compareTo(op) == 0 &&
							  operatorStack.peek().getAssociativity() == Associativity.LEFT))) {
						postfix.push(operatorStack.pop());
					}
					operatorStack.push(op);
				}
				
				lastToken = token + ch;
				realLastToken = lastToken;
				token = new String();
			}
			else if (Operand.isOperand(token + ch) && (chNext != 'e' && chNext != 'E') &&
						(chNext == '\u0000' || !Operand.isOperand(token + ch + chNext))) {
				// add to postfix
				postfix.push(new Operand(token + ch));
				
				lastToken = token + ch;
				realLastToken = lastToken;
				token = new String();
			}
			else {
				token += ch;
			}
		}
		
		if (!token.isEmpty()) {
			throw new Expr4jException("Invalid expression");
		}
		
		while (!operatorStack.isEmpty()) {
			if (operatorStack.peek().value.equals("(")) {
				throw new Expr4jException("Unmatched number of parenthesis");
			}
			postfix.push(operatorStack.pop());
		}
	}
	
	/**
	 * Method to evaluate operators at the top of the operator stack until a left parenthesis is encountered.
	 */
	protected void evaluateParenthesis() {
		boolean flag = false;
		// pop until left parenthesis
		while (!operatorStack.isEmpty()) {
			if (operatorStack.peek().value.equals("(")) {
				operatorStack.pop();
				flag = true;
				break;
			}
			// evaluate top of stack
			postfix.push(operatorStack.pop());
		}
		if (!flag) {
			throw new Expr4jException("Unmatched number of parenthesis");
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
			
			for (int i = operator.getOperandCount() - 1; i >= 0; i--) {
				if (node.children[i] == null) {
					node.children[i] = new Node(token);
					return true;
				}
				else if (formTree(node.children[i], token)) {
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
				Node node = new Node(token);
				root = node;
			}
			else {
				boolean flag = formTree(root, token);
				
				if (!flag) {
					throw new Expr4jException("Invalid expression");
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
			
			Operand[] operands = new Operand[operator.getOperandCount()];
			
			for (int i = 0; i < operator.getOperandCount(); i++) {
				if (node.children[i] == null) {
					throw new Expr4jException("Invalid expression");
				}
				else if (node.children[i].token instanceof Operand) {
					operands[i] = (Operand) node.children[i].token;
				}
				else if (node.children[i].token instanceof Operator) {
					operands[i] = evaluate(node.children[i]);
				}
			}
			
			return operator.evaluate(operands);
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
			bd = bd.setScale(Expr4jConstants.PRECISION, RoundingMode.HALF_UP);
			
			// return the result
			return bd.doubleValue();
		}
		finally {
			// expression evaluation can be a memory expensive process
			// clean up
			this.root = null;
			this.postfix = null;
			this.operatorStack = null;
		}
	}
	
}
