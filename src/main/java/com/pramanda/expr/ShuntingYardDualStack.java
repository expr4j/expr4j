package com.pramanda.expr;

import java.util.Stack;

import com.pramanda.expr.Operator.Properties.Associativity;

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
		
		for (int i = 0; i < expr.length(); i++) {
			char ch = expr.charAt(i);
			char chNext = (i + 1 < expr.length()) ? expr.charAt(i + 1) : '\u0000';
			
			if (Operator.isOperator(token + ch) && (chNext == '\u0000' || !Operator.isOperator(token + ch + chNext))) {
				// add to operator stack
				Operator op = new Operator(token + ch);
				
				// handle unary - and + operators
				if (((ch == '-' || ch == '+') && chNext != ' ' &&
					(lastToken == null || (Operator.isOperator(lastToken) && !lastToken.equals(")"))))) {
					if (ch == '-') op = new Operator("uminus");
					if (ch == '+') op = new Operator("uplus");
				}
				
				if (op.getOperandCount() == 0) {
					Operand eval = op.evaluate();
					operandStack.push(eval);
					lastToken = eval.value;
					token = new String();
					continue;
				}
				else if (op.value.equals("(")) {
					operatorStack.push(op);
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
			return this.evaluateExpr(expr).toDouble();
		}
		finally {
			// expression evaluation can be a memory expensive process
			// clean up
			this.operandStack = null;
			this.operatorStack = null;
		}
	}
	
}
