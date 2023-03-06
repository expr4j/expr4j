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

package in.pratanumandal.expr4j;

import in.pratanumandal.expr4j.Expression.Node;
import in.pratanumandal.expr4j.exception.Expr4jException;
import in.pratanumandal.expr4j.token.Executable;
import in.pratanumandal.expr4j.token.Function;
import in.pratanumandal.expr4j.token.Operator;
import in.pratanumandal.expr4j.token.Operator.OperatorType;
import in.pratanumandal.expr4j.token.Token;

import java.util.*;

/**
 * The <code>ExpressionBuilder&lt;T&gt;</code> class provides a partial implementation to build expressions independent of the type of operand.<br>
 * An expression is created from the postfix (or RPN) expression. The expression can then be evaluated.<br><br>
 * 
 * @author Pratanu Mandal
 * @since 1.0
 *
 * @param <T> The type of operand for this parser
 */
public abstract class ExpressionBuilder<T> {
	
	/**
	 * Instance of expression.
	 */
	private Expression<T> expression;
	
	/**
	 * Map to hold the executables.
	 */
	private Map<String, Executable<T>> executables;
	
	/**
	 * Map to hold the constants.
	 */
	private Map<String, T> constants;
	
	/**
	 * No-Argument Constructor.
	 */
	public ExpressionBuilder() {
		this.reset();
	}

	/**
	 * Reset the parser.
	 */
	public void reset() {
		executables = new TreeMap<>();
		constants = new TreeMap<>();

		this.addExecutableWithoutCheck(new Operator<T>(Operator.UNARY_PLUS, OperatorType.PREFIX, Integer.MAX_VALUE, (operands) -> unaryPlus(operands.get(0))));
		this.addExecutableWithoutCheck(new Operator<T>(Operator.UNARY_MINUS, OperatorType.PREFIX, Integer.MAX_VALUE, (operands) -> unaryMinus(operands.get(0))));
		this.addExecutableWithoutCheck(new Operator<T>(Operator.IMPLICIT_MULTIPLICATION, OperatorType.INFIX, 2, (operands) -> implicitMultiplication(operands.get(0), operands.get(1))));
	}

	/**
	 * Method to form the expression tree recursively.
	 *
	 * @param node Current node of the expression tree
	 * @param token Token to be inserted
	 * @return true if token could be inserted, otherwise false
	 */
	@SuppressWarnings("unchecked")
	private boolean formTree(Node node, Token token) {
		if (node.token instanceof Function) {
			Function<T> function = (Function<T>) node.token;

			int operandCount = function.parameters;

			if (node.children.size() > 0 && formTree(node.children.get(0), token)) {
				return true;
			}
			else if (node.children.size() < operandCount) {
				node.children.add(0, new Node(token));
				return true;
			}
		}
		else if (node.token instanceof Operator) {
			Operator<T> operator = (Operator<T>) node.token;

			int operandCount = (operator.operatorType == OperatorType.INFIX || operator.operatorType == OperatorType.INFIX_RTL) ? 2 : 1;

			if (node.children.size() > 0 && formTree(node.children.get(0), token)) {
				return true;
			}
			else if (node.children.size() < operandCount) {
				node.children.add(0, new Node(token));
				return true;
			}
		}

		return false;
	}

	/**
	 * Method to form the expression tree.
	 */
	private void formTree(Stack<Token> postfix) {
		while (!postfix.isEmpty()) {
			Token token = postfix.pop();

			if (expression.root == null) {
				Node node = new Node(token);
				expression.root = node;
			}
			else {
				boolean flag = formTree(expression.root, token);

				if (!flag) {
					throw new Expr4jException("Invalid expression");
				}
			}
		}
	}

	/**
	 * Method to parse an expression.<br>
	 * This method acts as the single point of access for expression parsing.
	 *
	 * @param expr Expression string
	 * @return The parsed expression
	 */
	public Expression<T> build(String expr) {
		try {
			// initialize expression
			this.expression = new Expression<>(constants, this::operandToString);

			// tokenize the expression
			ExpressionTokenizer<T> tokenizer = new ExpressionTokenizer<T>() {
				@Override
				protected T stringToOperand(String operand) {
					return ExpressionBuilder.this.stringToOperand(operand);
				}

				@Override
				protected List<String> getNumberPattern() {
					return ExpressionBuilder.this.getNumberPattern();
				}
			};
			List<Token> tokenList = tokenizer.tokenize(expr, executables);

			// form the postfix expression
			ExpressionParser<T> parser = new ExpressionParser<T>();
			Stack<Token> postfix = parser.parse(tokenList);

			// form the tree
			this.formTree(postfix);
			
			return this.expression;
		}
		finally {
			// clean up - expression evaluation can be a memory intensive process
			this.expression = null;
		}
	}
	
	/**
	 * Get unmodifiable list of executables present in the parser.
	 * 
	 * @return List of executables
	 */
	public List<Executable<T>> getExecutables() {
		return Collections.unmodifiableList(new ArrayList<>(executables.values()));
	}
	
	/**
	 * Get executable present in the parser for the specified label.
	 * 
	 * @param label Label of the executable
	 * @return Executable for the specified label if present, else null
	 */
	public Executable<T> getExecutable(String label) {
		return executables.get(label);
	}
	
	/**
	 * Add an executable to the parser without checking.
	 * 
	 * @param executable Executable to be added
	 */
	private void addExecutableWithoutCheck(Executable<T> executable) {
		executables.put(executable.label, executable);
	}
	
	/**
	 * Add an executable to the parser.
	 * 
	 * @param executable Executable to be added
	 */
	public void addExecutable(Executable<T> executable) {
		if (executable.label.equals(Operator.UNARY_PLUS) ||
				executable.label.equals(Operator.UNARY_MINUS) ||
				executable.label.equals(Operator.IMPLICIT_MULTIPLICATION)) {
			throw new Expr4jException("Overriding of predefined operators is forbidden");
		}
		
		executables.put(executable.label, executable);
	}
	
	/**
	 * Add a list of executables to the parser.
	 * 
	 * @param executableList List of executables to be added
	 */
	public void addExecutable(List<Executable<T>> executableList) {
		for (Executable<T> executable : executableList) {
			addExecutable(executable);
		}
	}
	
	/**
	 * Remove executable from the parser for the specified label if present.
	 * 
	 * @param label Label of the executable
	 * @return Executable for the specified label if present, else null
	 */
	public Executable<T> removeExecutable(String label) {
		if (label.equals(Operator.UNARY_PLUS) ||
				label.equals(Operator.UNARY_MINUS) ||
				label.equals(Operator.IMPLICIT_MULTIPLICATION)) {
			throw new Expr4jException("Removal of predefined operators is forbidden");
		}
		
		return executables.remove(label);
	}
	
	/**
	 * Get unmodifiable map of constants present in the parser.
	 * 
	 * @return Map of constants
	 */
	public Map<String, T> getConstants() {
		return Collections.unmodifiableMap(constants);
	}
	
	/**
	 * Get constant present in the parser for the specified label.
	 * 
	 * @param label Label of the constant
	 * @return Constant for the specified label if present, else null
	 */
	public T getConstant(String label) {
		if (!constants.containsKey(label)) {
			throw new Expr4jException("Constant not found: " + label);
		}
		return constants.get(label);
	}
	
	/**
	 * Add a constant to the parser.
	 * 
	 * @param label Label of the constant
	 * @param value Value of the constant
	 */
	public void addConstant(String label, T value) {
		constants.put(label, value);
	}
	
	/**
	 * Remove constant from the parser for the specified label if present.
	 * 
	 * @param label Label of the constant
	 * @return Constant for the specified label if present, else null
	 */
	public T removeConstant(String label) {
		return constants.remove(label);
	}
	
	/**
	 * Method to define operation of unary plus.
	 * 
	 * @param operand Operand of unary plus operation
	 * @return Result of unary plus operation
	 */
	protected abstract T unaryPlus(T operand);
	
	/**
	 * Method to define operation of unary minus.
	 * 
	 * @param operand Operand of unary minus operation
	 * @return Result of unary minus operation
	 */
	protected abstract T unaryMinus(T operand);
	
	/**
	 * Method to define operation of implicit multiplication operation.
	 * 
	 * @param operand0 First operand of implicit multiplication operation
	 * @param operand1 Second operand of implicit multiplication operation
	 * @return Result of implicit multiplication operation
	 */
	protected abstract T implicitMultiplication(T operand0, T operand1);
	
	/**
	 * Method to define procedure to obtain operand from string representation.
	 * 
	 * @param operand String representation of operand
	 * @return Operand
	 */
	protected abstract T stringToOperand(String operand);
	
	/**
	 * Method to define procedure to obtain string representation of operand.
	 * 
	 * @param operand Operand
	 * @return String representation of operand
	 */
	protected abstract String operandToString(T operand);
	
	/**
	 * Method to define the patterns to identify numbers.<br>
	 * Override this method if the patterns to identify numbers need to be customized.
	 * 
	 * @return List of patterns to identify numbers
	 */
	protected List<String> getNumberPattern() {
		return Arrays.asList("(-?\\d+)(\\.\\d+)?(e-|e\\+|e|\\d+)\\d+", "\\d*\\.?\\d+");
	}
	
}
