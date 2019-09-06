package com.pramanda.expr;

import java.util.Stack;

import com.pramanda.expr.Operator.Properties.Associativity;

public class ShuntingYardTree extends ShuntingYard {
	
	protected class Node {
		protected Node left;
		protected Node right;
		protected Token token;
	}
	
	protected Node root;
	protected Stack<Token> postfix;
	
	protected void init(String expr) {
		// remove all whitespace except if whitespace is present between operands
		expr = expr.replaceAll("(?!\\d|\\+|\\-)\\s+(?!\\d|\\.)", "");
		
		postfix = new Stack<>();
		Stack<Operator> opStack = new Stack<>();
		
		String token = new String();
		String lastToken = null;
		
		for (int i = 0; i < expr.length(); i++) {
			char ch = expr.charAt(i);
			char chNext = (i + 1 < expr.length()) ? expr.charAt(i + 1) : '\u0000';
			
			if (Operator.isOperator(token + ch)) {
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
					postfix.push(eval);
					lastToken = eval.value;
					token = new String();
					continue;
				}
				else if (op.value.equals("(")) {
					opStack.push(op);
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
			else if (Operand.isOperand(token + ch) && (chNext != 'e' && chNext != 'E') && (chNext == '\u0000' || !Operand.isOperand(token + ch + chNext))) {
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
	
	@Override
	public double evaluate(String expr) {
		try {
			// initialize expression
			this.init(expr);
			
			// form the tree
			this.formTree();
			
			// evaluate the expression
			return this.evaluate(root).toDouble();
		}
		finally {
			// expression evaluation can be a memory expensive process
			// clean up
			this.root = null;
			this.postfix = null;
		}
	}
	
}
