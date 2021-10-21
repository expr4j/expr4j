package tk.pratanumandal.expr4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tk.pratanumandal.expr4j.exception.Expr4jException;
import tk.pratanumandal.expr4j.token.Function;
import tk.pratanumandal.expr4j.token.Operand;
import tk.pratanumandal.expr4j.token.Operator;
import tk.pratanumandal.expr4j.token.Operator.OperatorType;
import tk.pratanumandal.expr4j.token.Token;
import tk.pratanumandal.expr4j.token.Variable;

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
	
	private final Map<String, T> constants;
	
	Expression(Map<String, T> constants) {
		this.constants = new HashMap<>(constants);
	}

	public Map<String, T> getConstants() {
		return new HashMap<>(constants);
	}

	/**
	 * Method to recursively evaluate the expression tree and return the result as an operand.
	 * 
	 * @param node Current node of the expression tree
	 * @return Result of expression evaluation as an operand
	 */
	@SuppressWarnings("unchecked")
	protected Operand<T> evaluate(Node node, Map<String, T> variables) {
		if (node.token instanceof Variable) {
			Variable variable = (Variable) node.token;
			
			if (!variables.containsKey(variable.label)) {
				throw new Expr4jException("Variable not found: " + variable.label);
			}
			
			return new Operand<T>(variables.get(variable.label));
		}
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
		else {
			return (Operand<T>) node.token;
		}
	}
	
	public T evaluate(Map<String, T> variables) {
		if (root == null) {
			throw new Expr4jException("Invalid expression");
		}
		
		Map<String, T> constantsAndVariables = new HashMap<>(constants);
		if (variables != null) constantsAndVariables.putAll(variables);
		
		return evaluate(root, constantsAndVariables).value;
	}
	
	public T evaluate() {
		return evaluate(new HashMap<String, T>());
	}
	
}
