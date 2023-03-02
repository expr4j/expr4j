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

package in.pratanumandal.expr4j.parser;

import in.pratanumandal.expr4j.parser.utils.DoubleUtils;
import in.pratanumandal.expr4j.token.Function;
import in.pratanumandal.expr4j.token.Operator;
import in.pratanumandal.expr4j.token.Operator.OperatorType;

import java.util.Arrays;
import java.util.Collections;

/**
 * The <code>DoubleParser</code> class provides an implementation to parse expressions for operands of type Double.<br>
 * 
 * @author Pratanu Mandal
 * @since 1.0
 *
 */
public class DoubleParser extends ExpressionParser<Double> {

	/**
	 * No-Argument Constructor.
	 */
	public DoubleParser() {
		super();
		this.initialize();
	}
	
	/**
	 * Initialize the operators, functions, and constants for operands of type Double.
	 */
	protected void initialize() {
		addExecutable(Arrays.asList(
			new Operator<>("+", OperatorType.INFIX, 1, (operands) -> operands.get(0) + operands.get(1)),
			new Operator<>("-", OperatorType.INFIX, 1, (operands) -> operands.get(0) - operands.get(1)),
			
			new Operator<>("*", OperatorType.INFIX, 2, (operands) -> operands.get(0) * operands.get(1)),
			new Operator<>("/", OperatorType.INFIX, 2, (operands) -> operands.get(0) / operands.get(1)),
			new Operator<>("%", OperatorType.INFIX, 2, (operands) -> operands.get(0) % operands.get(1)),
			
			new Operator<>("^", OperatorType.INFIX_RTL, 3, (operands) -> Math.pow(operands.get(0), operands.get(1))),
			
			new Operator<>("!", OperatorType.SUFFIX, 5, (operands) -> DoubleUtils.factorial(operands.get(0))),
			
			new Operator<>("sin", OperatorType.PREFIX, 4, (operands) -> Math.sin(operands.get(0))),
			new Operator<>("cos", OperatorType.PREFIX, 4, (operands) -> Math.cos(operands.get(0))),
			new Operator<>("tan", OperatorType.PREFIX, 4, (operands) -> Math.tan(operands.get(0))),
			
			new Operator<>("asin", OperatorType.PREFIX, 4, (operands) -> Math.asin(operands.get(0))),
			new Operator<>("acos", OperatorType.PREFIX, 4, (operands) -> Math.acos(operands.get(0))),
			new Operator<>("atan", OperatorType.PREFIX, 4, (operands) -> Math.atan(operands.get(0))),
			
			new Operator<>("sinh", OperatorType.PREFIX, 4, (operands) -> Math.sinh(operands.get(0))),
			new Operator<>("cosh", OperatorType.PREFIX, 4, (operands) -> Math.cosh(operands.get(0))),
			new Operator<>("tanh", OperatorType.PREFIX, 4, (operands) -> Math.tanh(operands.get(0))),
			
			new Operator<>("asinh", OperatorType.PREFIX, 4, (operands) -> DoubleUtils.asinh(operands.get(0))),
			new Operator<>("acosh", OperatorType.PREFIX, 4, (operands) -> DoubleUtils.acosh(operands.get(0))),
			new Operator<>("atanh", OperatorType.PREFIX, 4, (operands) -> DoubleUtils.atanh(operands.get(0))),
			
			new Function<>("deg", 1, (operands) -> Math.toDegrees(operands.get(0))),
			new Function<>("rad", 1, (operands) -> Math.toRadians(operands.get(0))),
			
			new Operator<>("round", OperatorType.PREFIX, 4, (operands) -> (double) Math.round(operands.get(0))),
			new Operator<>("floor", OperatorType.PREFIX, 4, (operands) -> Math.floor(operands.get(0))),
			new Operator<>("ceil", OperatorType.PREFIX, 4, (operands) -> Math.ceil(operands.get(0))),
			
			new Operator<>("ln", OperatorType.PREFIX, 4, (operands) -> Math.log(operands.get(0))),
			new Operator<>("log10", OperatorType.PREFIX, 4, (operands) -> Math.log10(operands.get(0))),
			new Function<>("log", 2, (operands) -> DoubleUtils.log(operands.get(1), operands.get(0))),
			
			new Operator<>("sqrt", OperatorType.PREFIX, 4, (operands) -> Math.sqrt(operands.get(0))),
			new Operator<>("cbrt", OperatorType.PREFIX, 4, (operands) -> Math.cbrt(operands.get(0))),
			
			new Function<>("exp", 1, (operands) -> Math.exp(operands.get(0))),
			
			new Function<>("max", (operands) -> operands.isEmpty() ? 0.0 : Collections.max(operands)),
			new Function<>("min", (operands) -> operands.isEmpty() ? 0.0 : Collections.min(operands)),
			
			new Function<>("mean", (operands) -> operands.stream().mapToDouble(d -> d).average().orElse(0.0)),
			
			new Function<>("rand", 0, (operands) -> Math.random())
		));
		
		addConstant("pi", Math.PI);
		addConstant("e", Math.E);
	}

	/**
	 * Define operation of unary plus.
	 * 
	 * @param operand Operand of unary plus operation
	 * @return Result of unary plus operation
	 */
	@Override
	protected Double unaryPlus(Double operand) {
		return operand;
	}

	/**
	 * Define operation of unary minus.
	 * 
	 * @param operand Operand of unary minus operation
	 * @return Result of unary minus operation
	 */
	@Override
	protected Double unaryMinus(Double operand) {
		return -operand;
	}
	
	/**
	 * Define operation of implicit multiplication operation.
	 * 
	 * @param operand0 First operand of implicit multiplication operation
	 * @param operand1 Second operand of implicit multiplication operation
	 * @return Result of implicit multiplication operation
	 */
	@Override
	protected Double implicitMultiplication(Double operand0, Double operand1) {
		return operand0 * operand1;
	}
	
	/**
	 * Method to define procedure to parse string representation of operand.
	 * 
	 * @param operand String representation of operand
	 * @return Parsed operand
	 */
	@Override
	protected Double stringToOperand(String operand) {
		return Double.parseDouble(operand);
	}

	/**
	 * Method to define procedure to obtain string representation of operand.
	 * 
	 * @param operand Operand
	 * @return String representation of operand
	 */
	@Override
	protected String operandToString(Double operand) {
		return operand == operand.intValue() ? String.valueOf(operand.intValue()) : operand.toString();
	}
	
}
