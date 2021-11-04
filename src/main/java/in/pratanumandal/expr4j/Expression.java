/**
 * Copyright 2021 Pratanu Mandal
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

package in.pratanumandal.expr4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import in.pratanumandal.expr4j.exception.Expr4jException;
import in.pratanumandal.expr4j.token.Function;
import in.pratanumandal.expr4j.token.Operand;
import in.pratanumandal.expr4j.token.Operator;
import in.pratanumandal.expr4j.token.Operator.OperatorType;
import in.pratanumandal.expr4j.token.Token;
import in.pratanumandal.expr4j.token.Variable;

/**
 * The <code>Expression&lt;T&gt;</code> class represents a parsed expression that can be evaluated.
 * 
 * @author Pratanu Mandal
 * @since 1.0
 *
 * @param <T> The type of operand for this expression
 */
public class Expression<T> {
	
	/**
	 * The <code>Node</code> class represents a node of the expression tree.<br><br>
	 * 
	 * @author Pratanu Mandal
	 *
	 */
	public static class Node {
		/**
		 * Children of this node.
		 */
		public final List<Node> children;
		
		/**
		 * Token contained in this node.<br>
		 * A token can be an operand, operator, function, variable, or constant.
		 */
		public final Token token;

		/**
		 * Parameterized constructor.
		 * 
		 * @param token The token in this node
		 */
		public Node(Token token) {
			this.token = token;
			if (token instanceof Function || token instanceof Operator) {
				this.children = new ArrayList<Expression.Node>();
			}
			else {
				this.children = null;
			}
		}
	}
	
	/**
	 * Root node of the expression tree.
	 */
	public Node root;
	
	/**
	 * Map to hold the constants.
	 */
	private final Map<String, T> constants;
	
	private final OperandRepresentation<T> operandRepresentation;
	
	/**
	 * Parameterized constructor.
	 * 
	 * @param constants Map of constants
	 * @param operandRepresentation Instance of <code>OperandRepresentation</code>
	 */
	public Expression(Map<String, T> constants, OperandRepresentation<T> operandRepresentation) {
		this.constants = new HashMap<>(constants);
		this.operandRepresentation = operandRepresentation;
	}

	/**
	 * Get unmodifiable map of constants for this expression.
	 * 
	 * @return Map of constants
	 */
	public Map<String, T> getConstants() {
		return Collections.unmodifiableMap(constants);
	}
	
	/**
	 * Recursively evaluate the expression tree and return the result.
	 * 
	 * @param node Current node of the expression tree
	 * @param variables Map of variables
	 * @return Result of expression evaluation
	 */
	@SuppressWarnings("unchecked")
	protected Operand<T> evaluate(Node node, Map<String, T> variables) {
		// encountered variable
		if (node.token instanceof Variable) {
			Variable variable = (Variable) node.token;
			
			if (!variables.containsKey(variable.label)) {
				throw new Expr4jException("Variable not found: " + variable.label);
			}
			
			return new Operand<T>(variables.get(variable.label));
		}
		
		// encountered function
		else if (node.token instanceof Function) {
			Function<T> function = (Function<T>) node.token;
			
			int operandCount = function.parameters;
			if (node.children.size() != operandCount) {
				throw new Expr4jException("Invalid expression");
			}
			
			List<T> operands = new ArrayList<>();
			for (int i = 0; i < operandCount; i++) {
				operands.add(evaluate(node.children.get(i), variables).value);
			}
			
			return new Operand<T>(function.evaluate(operands));
		}
		
		// encountered operator
		else if (node.token instanceof Operator) {
			Operator<T> operator = (Operator<T>) node.token;
			
			int operandCount = (operator.operatorType == OperatorType.INFIX || operator.operatorType == OperatorType.INFIX_RTL) ? 2 : 1;
			if (node.children.size() != operandCount) {
				throw new Expr4jException("Invalid expression");
			}
			
			List<T> operands = new ArrayList<>();
			for (int i = 0; i < operandCount; i++) {
				operands.add(evaluate(node.children.get(i), variables).value);
			}
			
			return new Operand<T>(operator.evaluate(operands));
		}
		
		// encountered operand
		else {
			return (Operand<T>) node.token;
		}
	}
	
	/**
	 * Evaluate the expression against a set of variables.<br>
	 * Variables passed to this method override an predefined constants with the same label.
	 * 
	 * @param variables Map of variables
	 * @return Evaluated result
	 */
	public T evaluate(Map<String, T> variables) {
		if (root == null) {
			throw new Expr4jException("Invalid expression");
		}
		
		Map<String, T> constantsAndVariables = new HashMap<>(constants);
		if (variables != null) constantsAndVariables.putAll(variables);
		
		return evaluate(root, constantsAndVariables).value;
	}
	
	/**
	 * Evaluate the expression.
	 * 
	 * @return Evaluated result
	 */
	public T evaluate() {
		return evaluate(new HashMap<String, T>());
	}
	
	/**
	 * Form string representation of expression.
	 * 
	 * @param node Current node of the expression tree
	 * @return Result of expression evaluation
	 */
	@SuppressWarnings("unchecked")
	protected String toString(Node node) {
		// encountered variable
		if (node.token instanceof Variable) {
			Variable variable = (Variable) node.token;
			return variable.label;
		}
		
		// encountered function
		else if (node.token instanceof Function) {
			Function<T> function = (Function<T>) node.token;
			
			int operandCount = function.parameters;
			if (node.children.size() != operandCount) {
				throw new Expr4jException("Invalid expression");
			}
			
			String operands = node.children.stream().map(this::toString).collect(Collectors.joining(", "));
			
			return function.label + "(" + operands + ")";
		}
		
		// encountered operator
		else if (node.token instanceof Operator) {
			Operator<T> operator = (Operator<T>) node.token;
			
			int operandCount = (operator.operatorType == OperatorType.INFIX || operator.operatorType == OperatorType.INFIX_RTL) ? 2 : 1;
			if (node.children.size() != operandCount) {
				throw new Expr4jException("Invalid expression");
			}
			
			String label = null;
			if (operator.label.equals(Operator.UNARY_PLUS)) label = "+";
			else if (operator.label.equals(Operator.UNARY_MINUS)) label = "-";
			else if (operator.label.equals(Operator.IMPLICIT_MULTIPLICATION)) {
				if (node.children.get(0).token instanceof Operand && node.children.get(1).token instanceof Operand) {
					label = " * ";
				}
				else label = " ";
			}
			else if (operandCount == 2) label = " " + operator.label + " ";
			else label = operator.label;
			
			if (operandCount == 2) {
				StringBuilder sb = new StringBuilder();
				
				Token left = node.children.get(0).token;
				if (left instanceof Operator &&
						(((Operator<T>) left).operatorType == OperatorType.INFIX ||
						((Operator<T>) left).operatorType == OperatorType.INFIX_RTL)) {
					sb.append("(");
					sb.append(this.toString(node.children.get(0)));
					sb.append(")");
				}
				else {
					sb.append(this.toString(node.children.get(0)));
				}
				
				sb.append(label);
				
				Token right = node.children.get(1).token;
				if (right instanceof Operator &&
						(((Operator<T>) right).operatorType == OperatorType.INFIX ||
						((Operator<T>) right).operatorType == OperatorType.INFIX_RTL)) {
					sb.append("(");
					sb.append(this.toString(node.children.get(1)));
					sb.append(")");
				}
				else {
					sb.append(this.toString(node.children.get(1)));
				}
				
				return sb.toString();
			}
			else {
				if (operator.label.equals(Operator.UNARY_PLUS) || operator.label.equals(Operator.UNARY_MINUS)) {
					return label + this.toString(node.children.get(0));
				}
				else if (node.children.get(0).token instanceof Operator || node.children.get(0).token instanceof Function) {
					if (operator.operatorType == OperatorType.PREFIX) {
						return label + "(" + this.toString(node.children.get(0)) + ")";
					}
					else {
						return "(" + this.toString(node.children.get(0)) + ") " + label;
					}
				}
				else {
					if (operator.operatorType == OperatorType.PREFIX) {
						return label + " " + this.toString(node.children.get(0));
					}
					else {
						return this.toString(node.children.get(0)) + " " + label;
					}
				}
			}
		}
		
		// encountered operand
		else {
			Operand<T> operand = (Operand<T>) node.token;
			return operandRepresentation.toString(operand.value);
		}
	}

	/**
	 * Get string representation of expression.
	 */
	@Override
	public String toString() {
		return this.toString(root);
	}
	
	/**
	 * The <code>OperandRepresentation&lt;T&gt;</code> functional interface represents a string representation of an operand.
	 * 
	 * @author Pratanu Mandal
	 * @since 1.0
	 *
	 * @param <T> The type of operand
	 */
	@FunctionalInterface
	public interface OperandRepresentation<T> {
		
		/**
		 * Get string representation of operand.
		 * 
		 * @param value Value of operand
		 * @return String representation of operand
		 */
		public abstract String toString(T value);

	}
	
}
