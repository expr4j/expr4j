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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import tk.pratanumandal.expr4j.Expression.Node;
import tk.pratanumandal.expr4j.exception.Expr4jException;
import tk.pratanumandal.expr4j.token.Separator;
import tk.pratanumandal.expr4j.token.Executable;
import tk.pratanumandal.expr4j.token.Function;
import tk.pratanumandal.expr4j.token.Operand;
import tk.pratanumandal.expr4j.token.Operator;
import tk.pratanumandal.expr4j.token.Operator.OperatorType;
import tk.pratanumandal.expr4j.token.Token;
import tk.pratanumandal.expr4j.token.Variable;

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
public abstract class ExpressionParser<T> {
	
	private Expression<T> expression;
	
	/**
	 * Stack to hold the postfix (RPN) expression.
	 */
	private Stack<Token> postfix;
	
	/**
	 * Stack to hold the operators.
	 */
	private Stack<Token> operatorStack;
	
	private Stack<Integer> functionStack;
	
	private Map<String, Executable<T>> executables;
	
	private Map<String, T> constants;
	
	/**
	 * No-Argument Constructor.
	 */
	public ExpressionParser() {
		executables = new HashMap<>();
		constants = new HashMap<>();
		
		this.addExecutable(new Operator<T>(Operator.UNARY_PLUS, OperatorType.PREFIX, Integer.MAX_VALUE, (operands) -> unaryPlus(operands.get(0))));
		this.addExecutable(new Operator<T>(Operator.UNARY_MINUS, OperatorType.PREFIX, Integer.MAX_VALUE, (operands) -> unaryMinus(operands.get(0))));
		this.addExecutable(new Operator<T>(Operator.IMPLICIT_MULTIPLICATION, OperatorType.INFIX, Integer.MAX_VALUE, (operands) -> imlicitMultiplication(operands.get(0), operands.get(1))));
		
		this.initialize();
	}
	
	/**
	 * Method to create the postfix expression from the infix expression.
	 * 
	 * @param expr Expression string
	 */
	@SuppressWarnings("unchecked")
	private void initialize(String expr) {
		// do not allow blank expressions
		if (expr.isBlank()) {
			throw new Expr4jException("Invalid expression");
		}
		
		expression = new Expression<>(constants);
		
		postfix = new Stack<>();
		operatorStack = new Stack<>();
		functionStack = new Stack<>();
		
		Map<String, Function<T>> functions = new HashMap<>();
		Map<String, Operator<T>> operators = new HashMap<>();
		
		Iterator<Entry<String, Executable<T>>> iterator = executables.entrySet().iterator();
		while (iterator.hasNext()) {
			Executable<T> executable = iterator.next().getValue();
			
			if (executable instanceof Function) {
				Function<T> function = (Function<T>) executable;
				functions.put(function.label, function);
			}
			else if (executable instanceof Operator) {
				Operator<T> operator = (Operator<T>) executable;
				operators.put(operator.label, operator);
			}
		}
		
		Comparator<String> comparator = (object, other) -> (other.length() - object.length());
		
		List<String> functionAndOperatorList = new ArrayList<>();
		functionAndOperatorList.addAll(functions.keySet());
		functionAndOperatorList.addAll(operators.keySet());
		Collections.sort(functionAndOperatorList, comparator);
		String functionAndOperatorPatternString = functionAndOperatorList.stream().map(Pattern::quote).collect(Collectors.joining("|"));
		Pattern functionAndOperatorPattern = Pattern.compile(functionAndOperatorPatternString);
		
		Pattern unaryPattern = Pattern.compile("(\\+|\\-)");
		
		List<Pattern> numberPatternList = new ArrayList<>();
		
		for (String patternString : getNumberPattern()) {
			Pattern numberPattern = Pattern.compile(patternString);
			numberPatternList.add(numberPattern);
		}
		
		Pattern variablePattern = Pattern.compile("[a-zA-Z]+[0-9]*[a-zA-Z]*");
		
		Pattern openBracketPattern = Pattern.compile("\\(");
		Pattern closeBracketPattern = Pattern.compile("\\)");
		Pattern commaPattern = Pattern.compile(",");
		
		Pattern whitespacePattern = Pattern.compile("\\s+");
		
		Separator openBracket = new Separator("(");
		Separator closeBracket = new Separator(")");
		Separator comma = new Separator(",");
		
		int index = 0;
		
		boolean probableUnary = true;
		boolean probableZeroFunction = false;
		
		Token lastToken = null;
		
		// while has more characters
		outer:
		while (index < expr.length()) {
			Matcher matcher;
			
			matcher = openBracketPattern.matcher(expr.substring(index));
			if (matcher.lookingAt()) {
				if (lastToken instanceof Operator) {
					Operator<T> operator = (Operator<T>) lastToken;
					if (operator.operatorType == OperatorType.SUFFIX) {
						operatorStack.push(operators.get(Operator.IMPLICIT_MULTIPLICATION));
					}
				}
				else if (lastToken == closeBracket || lastToken instanceof Operand || lastToken instanceof Variable) {
					operatorStack.push(operators.get(Operator.IMPLICIT_MULTIPLICATION));
				}
				
				index++;
				operatorStack.push(openBracket);
				
				lastToken = openBracket;
				
				probableZeroFunction = false;
				probableUnary = true;
				continue;
			}
			
			matcher = closeBracketPattern.matcher(expr.substring(index));
			if (matcher.lookingAt()) {
				if (lastToken == openBracket || lastToken == comma) {
					throw new Expr4jException("Invalid expression");
				}
				else if (lastToken instanceof Operator) {
					Operator<T> operator = (Operator<T>) lastToken;
					if (operator.operatorType != OperatorType.SUFFIX) {
						throw new Expr4jException("Invalid expression");
					}
				}
				
				if (probableZeroFunction) {
					if (functionStack.isEmpty()) {
						throw new Expr4jException("Invalid expression");
					}
					functionStack.pop();
					functionStack.push(0);
				}
				
				index++;
				evaluateParenthesis();
				
				lastToken = closeBracket;
				
				probableZeroFunction = false;
				probableUnary = false;
				continue;
			}
			
			matcher = commaPattern.matcher(expr.substring(index));
			if (matcher.lookingAt()) {
				if (lastToken instanceof Function) {
					throw new Expr4jException("Invalid expression");
				}
				else if (lastToken instanceof Operator) {
					Operator<T> operator = (Operator<T>) lastToken;
					if (operator.operatorType != OperatorType.SUFFIX) {
						throw new Expr4jException("Invalid expression");
					}
				}
				else if (lastToken == openBracket || lastToken == comma) {
					throw new Expr4jException("Invalid expression");
				}
				
				index++;
				while (!operatorStack.isEmpty() && !(operatorStack.peek() instanceof Function)) {
					postfix.push(operatorStack.pop());
				}
				
				if (functionStack.isEmpty()) {
					throw new Expr4jException("Invalid expression");
				}
				functionStack.push(functionStack.pop() + 1);
				
				lastToken = comma;
				
				probableZeroFunction = false;
				probableUnary = true;
				continue;
			}
			
			matcher = unaryPattern.matcher(expr.substring(index));
			if (probableUnary && matcher.lookingAt()) {
				String match = matcher.group();
				index++;
				
				Operator<T> operator = operators.get(match.equals("+") ? Operator.UNARY_PLUS : Operator.UNARY_MINUS);
				
				while (!operatorStack.isEmpty() &&
						(operatorStack.peek() instanceof Operator &&
								((Operator<T>) operatorStack.peek()).compareTo(operator) > 0)) {
					postfix.push(operatorStack.pop());
				}
				operatorStack.push(operator);
				
				lastToken = operator;
				
				probableZeroFunction = false;
				probableUnary = false;
				continue;
			}
			
			matcher = functionAndOperatorPattern.matcher(expr.substring(index));
			if (matcher.lookingAt()) {
				String match = matcher.group();
				index += match.length();
				
				if (functions.containsKey(match)) {
					if (lastToken instanceof Operator) {
						Operator<T> operator = (Operator<T>) lastToken;
						if (operator.operatorType == OperatorType.SUFFIX) {
							operatorStack.push(operators.get(Operator.IMPLICIT_MULTIPLICATION));
						}
					}
					else if (lastToken == closeBracket || lastToken instanceof Operand || lastToken instanceof Variable) {
						operatorStack.push(operators.get(Operator.IMPLICIT_MULTIPLICATION));
					}
					
					Function<T> function = functions.get(match);
					
					matcher = openBracketPattern.matcher(expr.substring(index));
					if (matcher.lookingAt()) {
						index++;
						operatorStack.push(function);
						functionStack.push(1);
						
						lastToken = function;
						
						if (function.parameters == Function.VARIABLE_PARAMETERS) probableZeroFunction = true;
						probableUnary = true;
					}
					else {
						throw new Expr4jException("Missing open bracket for function: " + function.label);
					}
				}
				else {
					Operator<T> operator = operators.get(match);
					
					if (operator.operatorType == OperatorType.INFIX) {
						if (lastToken == null || lastToken instanceof Function) {
							throw new Expr4jException("Invalid expression");
						}
						else if (lastToken instanceof Operator) {
							Operator<T> operator1 = (Operator<T>) lastToken;
							if (operator1.operatorType != OperatorType.SUFFIX) {
								throw new Expr4jException("Invalid expression");
							}
						}
						else if (lastToken == openBracket || lastToken == comma) {
							throw new Expr4jException("Invalid expression");
						}
					}
					else if (operator.operatorType == OperatorType.PREFIX) {
						if (lastToken != null &&
								(lastToken instanceof Operand || lastToken instanceof Variable || lastToken == closeBracket)) {
							throw new Expr4jException("Invalid expression");
						}
					}
					else if (operator.operatorType == OperatorType.SUFFIX) {
						if (lastToken == null || lastToken instanceof Function || lastToken instanceof Operator) {
							throw new Expr4jException("Invalid expression");
						}
					}
					
					if (operator.operatorType == OperatorType.SUFFIX) {
						while (!operatorStack.isEmpty() &&
								(operatorStack.peek() instanceof Operator &&
										((Operator<T>) operatorStack.peek()).compareTo(operator) > 0)) {
							postfix.push(operatorStack.pop());
						}
						postfix.push(operator);
						
						probableZeroFunction = false;
						probableUnary = false;
					}
					else {
						while (!operatorStack.isEmpty() &&
								(operatorStack.peek() instanceof Operator &&
										((Operator<T>) operatorStack.peek()).compareTo(operator) > 0)) {
							postfix.push(operatorStack.pop());
						}
						operatorStack.push(operator);
						
						probableZeroFunction = false;
						probableUnary = true;
					}
					
					lastToken = operator;
				}
				
				continue;
			}
			
			for (Pattern numberPattern : numberPatternList) {
				matcher = numberPattern.matcher(expr.substring(index));
				if (matcher.lookingAt()) {
					if (lastToken instanceof Operator) {
						Operator<T> operator = (Operator<T>) lastToken;
						if (operator.operatorType == OperatorType.SUFFIX) {
							operatorStack.push(operators.get(Operator.IMPLICIT_MULTIPLICATION));
						}
					}
					else if (lastToken == closeBracket || lastToken instanceof Operand || lastToken instanceof Variable) {
						operatorStack.push(operators.get(Operator.IMPLICIT_MULTIPLICATION));
					}
					
					String number = matcher.group();
					index += number.length();
					
					Operand<T> operand = new Operand<T>(this.parseNumber(number));
					postfix.push(operand);
					
					lastToken = operand;
					
					probableZeroFunction = false;
					probableUnary = false;
					continue outer;
				}
			}
			
			matcher = variablePattern.matcher(expr.substring(index));
			if (matcher.lookingAt()) {
				if (lastToken instanceof Operator) {
					Operator<T> operator = (Operator<T>) lastToken;
					if (operator.operatorType == OperatorType.SUFFIX) {
						operatorStack.push(operators.get(Operator.IMPLICIT_MULTIPLICATION));
					}
				}
				else if (lastToken == closeBracket || lastToken instanceof Operand || lastToken instanceof Variable) {
					operatorStack.push(operators.get(Operator.IMPLICIT_MULTIPLICATION));
				}
				
				String match = matcher.group();
				index += match.length();
				
				Variable variable = new Variable(match);
				postfix.push(variable);
				
				lastToken = variable;
				
				probableZeroFunction = false;
				probableUnary = false;
				continue;
			}
			
			matcher = whitespacePattern.matcher(expr.substring(index));
			if (matcher.lookingAt()) {
				String whitespace = matcher.group();
				index += whitespace.length();
				
				continue;
			}
			
			throw new Expr4jException("Invalid expression");
		}
		
		while (!operatorStack.isEmpty()) {
			Token token = operatorStack.peek();
			if (token instanceof Function || token instanceof Separator) {
				throw new Expr4jException("Unmatched number of parenthesis");
			}
			postfix.push(operatorStack.pop());
		}
		
//		Iterator<Token> iterator1 = postfix.iterator();
//		while (iterator1.hasNext()) {
//			System.out.print(iterator1.next() + " ");
//		}
//		System.out.println();
	}
	
	/**
	 * Method to evaluate operators at the top of the operator stack until a left parenthesis is encountered.
	 */
	@SuppressWarnings("unchecked")
	private void evaluateParenthesis() {
		boolean flag = false;
		
		// pop until left parenthesis
		while (!operatorStack.isEmpty()) {
			Token token = operatorStack.peek();
			
			// encountered a function
			if (token instanceof Function) {
				Function<T> function = (Function<T>) operatorStack.pop();
				if (function.parameters == Function.VARIABLE_PARAMETERS) {
					if (functionStack.isEmpty()) {
						throw new Expr4jException("Invalid expression");
					}
					function = new Function<T>(function.label, functionStack.pop(), function.operation);
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
					if (operator.operatorType == OperatorType.PREFIX) {
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
			
			int operandCount = operator.operatorType == OperatorType.INFIX ? 2 : 1;
			
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
	private void formTree() {
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
	 * Method to evaluate an expression.<br>
	 * This method acts as the single point of access for expression evaluation.
	 * 
	 * @param expr Expression string
	 * @return Result of expression evaluation as a double
	 */
	public Expression<T> parse(String expr) {
		try {
			// initialize expression
			this.initialize(expr);
			
			// form the tree
			this.formTree();
			
			return this.expression;
		}
		finally {
			// expression evaluation can be a memory expensive process
			// clean up
			this.expression = null;
			this.postfix = null;
			this.operatorStack = null;
		}
	}
	
	public List<Executable<T>> getExecutables() {
		return new ArrayList<>(executables.values());
	}
	
	public Executable<T> getExecutable(String label) {
		return executables.get(label);
	}
	
	public void addExecutable(Executable<T> executable) {
		executables.put(executable.label, executable);
	}
	
	public void addExecutable(List<Executable<T>> executableList) {
		for (Executable<T> executable : executableList) {
			addExecutable(executable);
		}
	}
	
	public void removeExecutable(String label) {
		executables.remove(label);
	}
	
	public Map<String, T> getConstants() {
		return new HashMap<>(constants);
	}
	
	public T getConstant(String label) {
		if (!constants.containsKey(label)) {
			throw new Expr4jException("Constant not found: " + label);
		}
		return constants.get(label);
	}
	
	public void addConstant(String label, T value) {
		constants.put(label, value);
	}

	protected abstract void initialize();
	
	protected abstract T unaryPlus(T operand);
	
	protected abstract T unaryMinus(T operand);
	
	protected abstract T imlicitMultiplication(T operand0, T operand1);
	
	protected abstract T parseNumber(String number);
	
	protected List<String> getNumberPattern() {
		return Arrays.asList("(-?\\d+)(\\.\\d+)?(e-|e\\+|e|\\d+)\\d+", "\\d*\\.?\\d+");
	}
	
}
