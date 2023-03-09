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

import in.pratanumandal.expr4j.exception.Expr4jException;
import in.pratanumandal.expr4j.token.*;

import java.util.List;
import java.util.Stack;

/**
 * The <code>ExpressionParser&lt;T&gt;</code> class parses expressions independent of the type of operand.<br>
 * This class parses the tokenized expression to generate a postfix (RPN) expression.<br><br>
 * 
 * @author Pratanu Mandal
 * @since 1.0
 *
 * @param <T> The type of operand
 */
public class ExpressionParser<T> {
	
	/**
	 * Stack to hold the postfix (RPN) expression.
	 */
	private Stack<Token> postfix;
	
	/**
	 * Stack to hold the operators.
	 */
	private Stack<Token> operatorStack;
	
	/**
	 * Stack to hold the count of function parameters.
	 */
	private Stack<Integer> functionStack;
	
	/**
	 * No-Argument Constructor.
	 */
	public ExpressionParser() {
	}

	/**
	 * Method to create the postfix (RPN) expression from the infix expression.
	 *
	 * @param tokenList The token list
	 * @return The postfix expression
	 */
	public Stack<Token> parse(List<Token> tokenList) {
		// initialize members
		postfix = new Stack<>();
		operatorStack = new Stack<>();
		functionStack = new Stack<>();

		boolean probableZeroFunction = false;

		Token lastToken = null;

		// iterate over tokens
		for (int i = 0; i < tokenList.size(); i++) {
			Token token = tokenList.get(i);

			if (token instanceof Separator) {
				Separator separator = (Separator) token;

				// open bracket
				if (separator == Separator.OPEN_BRACKET) {
					operatorStack.push(separator);
				}

				// close bracket
				else if (separator == Separator.CLOSE_BRACKET) {
					throwIfNotPostfix(lastToken);
					throwIfOpenBracketOrComma(lastToken);

					if (probableZeroFunction) {
						if (functionStack.isEmpty()) {
							throw new Expr4jException("Invalid expression");
						}
						functionStack.pop();
						functionStack.push(0);
					}

					evaluateParenthesis();
				}

				// comma
				else if (separator == Separator.COMMA) {
					throwIfFunction(lastToken);
					throwIfNotPostfix(lastToken);
					throwIfOpenBracketOrComma(lastToken);

					while (!operatorStack.isEmpty() && !(operatorStack.peek() instanceof Function)) {
						postfix.push(operatorStack.pop());
					}

					if (functionStack.isEmpty()) {
						throw new Expr4jException("Invalid expression");
					}
					functionStack.push(functionStack.pop() + 1);
				}

				probableZeroFunction = false;
			}

			// functions
			else if (token instanceof Function) {
				Function<T> function = (Function<T>) token;

				Token nextToken = i != tokenList.size() - 1 ? tokenList.get(i + 1) : null;
				throwIfNoOpenBracket(nextToken, function);

				i++;

				operatorStack.push(function);

				if (function.parameters == 0) functionStack.push(0);
				else functionStack.push(1);

				if (function.parameters == Function.VARIABLE_PARAMETERS) probableZeroFunction = true;
			}

			// operators
			else if (token instanceof Operator) {
				Operator<T> operator = (Operator<T>) token;

				if (operator.type == OperatorType.INFIX ||
						operator.type == OperatorType.INFIX_RTL) {
					throwIfNull(lastToken);
					throwIfFunction(lastToken);
					throwIfNotPostfix(lastToken);
					throwIfOpenBracketOrComma(lastToken);
				}
				else if (operator.type == OperatorType.POSTFIX) {
					throwIfNull(lastToken);
					throwIfNotPostfix(lastToken);
				}

				pushOperator(operator);
				probableZeroFunction = false;
			}

			// numbers and variables
			else if (token instanceof Operand || token instanceof Variable) {
				postfix.push(token);
				probableZeroFunction = false;
			}

			// invalid token
			else {
				throw new Expr4jException("Invalid expression");
			}

			lastToken = token;
		}

		// process operator stack
		while (!operatorStack.isEmpty()) {
			Token token = operatorStack.peek();
			if (token instanceof Function || token instanceof Separator) {
				throw new Expr4jException("Unmatched number of parenthesis");
			}
			postfix.push(operatorStack.pop());
		}

		return postfix;
	}

	/**
	 * Push operator to operator stack or postfix stack.
	 *
	 * @param operator The operator to push
	 */
	private void pushOperator(Operator<T> operator) {
		if (operator.type != OperatorType.PREFIX) {
			while (!operatorStack.isEmpty() &&
					(operatorStack.peek() instanceof Operator &&
							operator.compareTo((Operator<T>) operatorStack.peek()) > 0)) {
				postfix.push(operatorStack.pop());
			}
		}
		if (operator.type == OperatorType.POSTFIX) {
			postfix.push(operator);
		}
		else {
			operatorStack.push(operator);
		}
	}

	/**
	 * Method to evaluate operators at the top of the operator stack until a left parenthesis or a function is encountered.
	 */
	private void evaluateParenthesis() {
		boolean flag = false;

		// pop until left parenthesis
		while (!operatorStack.isEmpty()) {
			Token token = operatorStack.peek();

			// encountered a function
			if (token instanceof Function) {
				Function<T> function = (Function<T>) operatorStack.pop();

				if (functionStack.isEmpty()) {
					throw new Expr4jException("Invalid expression");
				}
				int actualParamters = functionStack.pop();

				if (function.parameters == Function.VARIABLE_PARAMETERS) {
					function = new Function<T>(function.label, actualParamters, function.operation);
				}
				else if (function.parameters != actualParamters) {
					throw new Expr4jException("Incorrect number of parameters for function: " + function.label);
				}

				postfix.push(function);

				flag = true;
				break;
			}

			// encountered an open bracket
			else if (token instanceof Separator) {
				operatorStack.pop();

				if (!operatorStack.isEmpty() && operatorStack.peek() instanceof Operator) {
					Operator<T> operator = (Operator<T>) operatorStack.peek();
					if (operator.type == OperatorType.PREFIX) {
						postfix.push(operatorStack.pop());
					}
				}

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
	 * Throw exception if token is null.
	 *
	 * @param token The token
	 */
	private void throwIfNull(Token token) {
		if (token == null) {
			throw new Expr4jException("Invalid expression");
		}
	}

	/**
	 * Throw exception token is a function.
	 *
	 * @param token The token
	 */
	private void throwIfFunction(Token token) {
		if (token instanceof Function) {
			throw new Expr4jException("Invalid expression");
		}
	}

	/**
	 * Throw exception if token is an operator but not of type POSTFIX.
	 *
	 * @param token The token
	 */
	private void throwIfNotPostfix(Token token) {
		if (token instanceof Operator) {
			Operator<T> operator = (Operator<T>) token;
			if (operator.type != OperatorType.POSTFIX) {
				throw new Expr4jException("Invalid expression");
			}
		}
	}

	/**
	 * Throw exception if function is not followed by open bracket.
	 *
	 * @param token The token
	 * @param function The function
	 */
	private void throwIfNoOpenBracket(Token token, Function function) {
		if (token instanceof Separator) {
			Separator separator = (Separator) token;
			if (separator != Separator.OPEN_BRACKET) {
				throw new Expr4jException("Missing open bracket for function: " + function.label);
			}
		}
		else {
			throw new Expr4jException("Missing open bracket for function: " + function.label);
		}
	}

	/**
	 * Throw exception if the token is an open bracket or a comma.
	 *
	 * @param token The token
	 */
	private void throwIfOpenBracketOrComma(Token token) {
		if (token instanceof Separator) {
			Separator separator = (Separator) token;
			if (separator == Separator.OPEN_BRACKET || separator == Separator.COMMA) {
				throw new Expr4jException("Invalid expression");
			}
		}
	}
	
}
