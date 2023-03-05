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
import in.pratanumandal.expr4j.impl.utils.ComplexUtils;
import in.pratanumandal.expr4j.token.Function;
import in.pratanumandal.expr4j.token.Operator;
import in.pratanumandal.expr4j.token.Operator.OperatorType;
import org.apache.commons.math3.complex.Complex;

import java.util.Arrays;

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
		addExecutable(Arrays.asList(
			new Operator<>("+", OperatorType.INFIX, 1, (operands) -> operands.get(0).add(operands.get(1))),
			new Operator<>("-", OperatorType.INFIX, 1, (operands) -> operands.get(0).subtract(operands.get(1))),

			new Operator<>("*", OperatorType.INFIX, 2, (operands) -> operands.get(0).multiply(operands.get(1))),
			new Operator<>("/", OperatorType.INFIX, 2, (operands) -> operands.get(0).divide(operands.get(1))),

			new Operator<>("^", OperatorType.INFIX_RTL, 3, (operands) -> operands.get(0).pow(operands.get(1))),

			new Operator<>("abs", OperatorType.PREFIX, 4, (operands) -> new Complex(operands.get(0).abs())),

			new Operator<>("sin", OperatorType.PREFIX, 4, (operands) -> operands.get(0).sin()),
			new Operator<>("cos", OperatorType.PREFIX, 4, (operands) -> operands.get(0).cos()),
			new Operator<>("tan", OperatorType.PREFIX, 4, (operands) -> operands.get(0).tan()),

			new Operator<>("asin", OperatorType.PREFIX, 4, (operands) -> operands.get(0).asin()),
			new Operator<>("acos", OperatorType.PREFIX, 4, (operands) -> operands.get(0).acos()),
			new Operator<>("atan", OperatorType.PREFIX, 4, (operands) -> operands.get(0).atan()),

			new Operator<>("sinh", OperatorType.PREFIX, 4, (operands) -> operands.get(0).sinh()),
			new Operator<>("cosh", OperatorType.PREFIX, 4, (operands) -> operands.get(0).cosh()),
			new Operator<>("tanh", OperatorType.PREFIX, 4, (operands) -> operands.get(0).tanh()),

			new Operator<>("asinh", OperatorType.PREFIX, 4, (operands) -> ComplexUtils.asinh(operands.get(0))),
			new Operator<>("acosh", OperatorType.PREFIX, 4, (operands) -> ComplexUtils.acosh(operands.get(0))),
			new Operator<>("atanh", OperatorType.PREFIX, 4, (operands) -> ComplexUtils.atanh(operands.get(0))),

			new Operator<>("ln", OperatorType.PREFIX, 4, (operands) -> operands.get(0).log()),
			new Operator<>("log10", OperatorType.PREFIX, 4, (operands) -> ComplexUtils.log10(operands.get(0))),
			new Function<>("log", 2, (operands) -> ComplexUtils.log(operands.get(1), operands.get(0))),

			new Operator<>("sqrt", OperatorType.PREFIX, 4, (operands) -> operands.get(0).sqrt()),
			new Operator<>("cbrt", OperatorType.PREFIX, 4, (operands) -> ComplexUtils.cbrt(operands.get(0))),

			new Function<>("exp", 1, (operands) -> operands.get(0).exp()),

			new Function<>("max", (operands) -> operands.isEmpty() ? Complex.ZERO : ComplexUtils.max(operands)),
			new Function<>("min", (operands) -> operands.isEmpty() ? Complex.ZERO : ComplexUtils.min(operands)),

			new Function<>("mean", (operands) -> ComplexUtils.average(operands)),
			new Function<>("average", (operands) -> ComplexUtils.average(operands)),

			new Function<>("rand", 0, (operands) -> new Complex(Math.random(), Math.random()))
		));

		addConstant("pi", new Complex(Math.PI));
		addConstant("e", new Complex(Math.E));

		addConstant("i", Complex.I);
	}

	/**
	 * Define operation of unary plus.
	 * 
	 * @param operand Operand of unary plus operation
	 * @return Result of unary plus operation
	 */
	@Override
	protected Complex unaryPlus(Complex operand) {
		return operand;
	}

	/**
	 * Define operation of unary minus.
	 * 
	 * @param operand Operand of unary minus operation
	 * @return Result of unary minus operation
	 */
	@Override
	protected Complex unaryMinus(Complex operand) {
		return Complex.ZERO.subtract(operand);
	}
	
	/**
	 * Define operation of implicit multiplication operation.
	 * 
	 * @param operand0 First operand of implicit multiplication operation
	 * @param operand1 Second operand of implicit multiplication operation
	 * @return Result of implicit multiplication operation
	 */
	@Override
	protected Complex implicitMultiplication(Complex operand0, Complex operand1) {
		return operand0.multiply(operand1);
	}
	
	/**
	 * Method to define procedure to parse string representation of operand.
	 * 
	 * @param operand String representation of operand
	 * @return Parsed operand
	 */
	@Override
	protected Complex stringToOperand(String operand) {
		return new Complex(Double.parseDouble(operand), 0);
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
		}
		return representation.toString();
	}
	
}
