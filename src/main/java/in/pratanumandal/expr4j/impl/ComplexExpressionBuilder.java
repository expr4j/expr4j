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

package in.pratanumandal.expr4j.impl;

import in.pratanumandal.expr4j.ExpressionBuilder;
import in.pratanumandal.expr4j.ExpressionDictionary;
import in.pratanumandal.expr4j.impl.utils.ComplexUtils;
import in.pratanumandal.expr4j.token.Function;
import in.pratanumandal.expr4j.token.Operator;
import in.pratanumandal.expr4j.token.OperatorType;
import org.apache.commons.math3.complex.Complex;

import java.util.Arrays;
import java.util.List;

/**
 * The <code>ComplexExpressionBuilder</code> class provides an implementation to parse expressions for operands of type {@link Complex}.<br>
 * 
 * @author Pratanu Mandal
 * @since 1.0
 *
 */
public class ComplexExpressionBuilder extends ExpressionBuilder<Complex> {

	/**
	 * No-Argument Constructor.
	 */
	public ComplexExpressionBuilder() {
		super();
		this.initialize();
	}
	
	/**
	 * Initialize the operators, functions, and constants.
	 */
	protected void initialize() {
		ExpressionDictionary<Complex> expressionDictionary = this.getExpressionDictionary();

		expressionDictionary.addOperator(new Operator<>("+", OperatorType.PREFIX, Integer.MAX_VALUE, (operands) -> operands.get(0)));
		expressionDictionary.addOperator(new Operator<>("-", OperatorType.PREFIX, Integer.MAX_VALUE, (operands) -> operands.get(0).negate()));

		expressionDictionary.addOperator(new Operator<>("+", OperatorType.INFIX, 1, (operands) -> operands.get(0).add(operands.get(1))));
		expressionDictionary.addOperator(new Operator<>("-", OperatorType.INFIX, 1, (operands) -> operands.get(0).subtract(operands.get(1))));

		expressionDictionary.addOperator(new Operator<>("*", OperatorType.INFIX, 2, (operands) -> operands.get(0).multiply(operands.get(1))));
		expressionDictionary.addOperator(new Operator<>("/", OperatorType.INFIX, 2, (operands) -> operands.get(0).divide(operands.get(1))));

		expressionDictionary.addOperator(new Operator<>("^", OperatorType.INFIX_RTL, 3, (operands) -> operands.get(0).pow(operands.get(1))));

		expressionDictionary.addOperator(new Operator<>("abs", OperatorType.PREFIX, 4, (operands) -> new Complex(operands.get(0).abs())));

		expressionDictionary.addOperator(new Operator<>("sin", OperatorType.PREFIX, 4, (operands) -> operands.get(0).sin()));
		expressionDictionary.addOperator(new Operator<>("cos", OperatorType.PREFIX, 4, (operands) -> operands.get(0).cos()));
		expressionDictionary.addOperator(new Operator<>("tan", OperatorType.PREFIX, 4, (operands) -> operands.get(0).tan()));

		expressionDictionary.addOperator(new Operator<>("asin", OperatorType.PREFIX, 4, (operands) -> operands.get(0).asin()));
		expressionDictionary.addOperator(new Operator<>("acos", OperatorType.PREFIX, 4, (operands) -> operands.get(0).acos()));
		expressionDictionary.addOperator(new Operator<>("atan", OperatorType.PREFIX, 4, (operands) -> operands.get(0).atan()));

		expressionDictionary.addOperator(new Operator<>("sinh", OperatorType.PREFIX, 4, (operands) -> operands.get(0).sinh()));
		expressionDictionary.addOperator(new Operator<>("cosh", OperatorType.PREFIX, 4, (operands) -> operands.get(0).cosh()));
		expressionDictionary.addOperator(new Operator<>("tanh", OperatorType.PREFIX, 4, (operands) -> operands.get(0).tanh()));

		expressionDictionary.addOperator(new Operator<>("asinh", OperatorType.PREFIX, 4, (operands) -> ComplexUtils.asinh(operands.get(0))));
		expressionDictionary.addOperator(new Operator<>("acosh", OperatorType.PREFIX, 4, (operands) -> ComplexUtils.acosh(operands.get(0))));
		expressionDictionary.addOperator(new Operator<>("atanh", OperatorType.PREFIX, 4, (operands) -> ComplexUtils.atanh(operands.get(0))));

		expressionDictionary.addFunction(new Function<>("deg", 1, (operands) -> ComplexUtils.toDegrees(operands.get(0))));
		expressionDictionary.addFunction(new Function<>("rad", 1, (operands) -> ComplexUtils.toRadians(operands.get(0))));

		expressionDictionary.addOperator(new Operator<>("ln", OperatorType.PREFIX, 4, (operands) -> operands.get(0).log()));
		expressionDictionary.addOperator(new Operator<>("log10", OperatorType.PREFIX, 4, (operands) -> ComplexUtils.log10(operands.get(0))));
		expressionDictionary.addFunction(new Function<>("log", 2, (operands) -> ComplexUtils.log(operands.get(1), operands.get(0))));

		expressionDictionary.addOperator(new Operator<>("sqrt", OperatorType.PREFIX, 4, (operands) -> operands.get(0).sqrt()));
		expressionDictionary.addOperator(new Operator<>("cbrt", OperatorType.PREFIX, 4, (operands) -> ComplexUtils.cbrt(operands.get(0))));

		expressionDictionary.addFunction(new Function<>("exp", 1, (operands) -> operands.get(0).exp()));

		expressionDictionary.addFunction(new Function<>("max", (operands) -> operands.isEmpty() ? Complex.ZERO : ComplexUtils.max(operands)));
		expressionDictionary.addFunction(new Function<>("min", (operands) -> operands.isEmpty() ? Complex.ZERO : ComplexUtils.min(operands)));

		expressionDictionary.addFunction(new Function<>("mean", (operands) -> ComplexUtils.average(operands)));
		expressionDictionary.addFunction(new Function<>("average", (operands) -> ComplexUtils.average(operands)));

		expressionDictionary.addFunction(new Function<>("rand", 0, (operands) -> new Complex(Math.random(), Math.random())));

		expressionDictionary.addConstant("pi", new Complex(Math.PI));
		expressionDictionary.addConstant("e", new Complex(Math.E));

		expressionDictionary.addConstant("i", Complex.I);
	}
	
	/**
	 * Method to define procedure to parse string representation of operand.
	 * 
	 * @param operand String representation of operand
	 * @return Parsed operand
	 */
	@Override
	protected Complex stringToOperand(String operand) {
		if (!operand.contains("i")) {
			return new Complex(Double.parseDouble(operand), 0);
		}
		else {
			return new Complex(0, Double.parseDouble(operand.replaceAll("\\s*i", "")));
		}
	}

	/**
	 * Method to define procedure to obtain string representation of operand.
	 * 
	 * @param operand Operand
	 * @return String representation of operand
	 */
	@Override
	protected String operandToString(Complex operand) {
		StringBuilder representation = new StringBuilder();
		double real = operand.getReal();
		double imaginary = operand.getImaginary();
		if (real != 0.0) {
			if (real == (int) real) representation.append((int) real);
			else representation.append(real);
		}
		if (imaginary != 0.0) {
			if (imaginary == (int) imaginary) representation.append((int) imaginary);
			else representation.append(imaginary);
			representation.append("i");
		}
		return representation.toString();
	}

	@Override
	protected List<String> getNumberPattern() {
		return Arrays.asList("(-?\\d+)(\\.\\d+)?(e-|e\\+|e|\\d+)\\d+(\\s*i)?", "\\d*\\.?\\d+(\\s*i)?");
	}
	
}
