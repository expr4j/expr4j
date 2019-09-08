package com.pratanumandal.expr4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Stack;

import com.pratanumandal.expr4j.Operator.Properties.Associativity;

public class ShuntingYardDualStack extends ShuntingYard {
	
	private Stack<Operand> operandStack;
	private Stack<Operator> operatorStack;
	
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
			
			if (Operator.isOperator(token + ch) && (!Operator.isFunction(token + ch) || chNext == '(')) {
				// add to operator stack
				Operator op = new Operator(token + ch);
				
				// handle unary - and + operators
				if (((ch == '-' || ch == '+') && chNext != ' ' &&
					(lastToken == null || (Operator.isOperator(lastToken) && !lastToken.equals(")"))))) {
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
					if (Operator.isFunction(lastToken)) {
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