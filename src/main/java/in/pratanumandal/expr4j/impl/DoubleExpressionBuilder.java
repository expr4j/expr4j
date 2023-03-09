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
import in.pratanumandal.expr4j.ExpressionConfig;
import in.pratanumandal.expr4j.ExpressionDictionary;
import in.pratanumandal.expr4j.impl.utils.DoubleUtils;
import in.pratanumandal.expr4j.token.Function;
import in.pratanumandal.expr4j.token.Operator;
import in.pratanumandal.expr4j.token.OperatorType;

import java.util.Collections;

/**
 * The <code>DoubleExpressionBuilder</code> class provides an implementation to parse expressions for operands of type {@link Double}.<br>
 * 
 * @author Pratanu Mandal
 * @since 1.0
 *
 */
public class DoubleExpressionBuilder extends ExpressionBuilder<Double> {

	/**
	 * No-Argument Constructor.
	 */
	public DoubleExpressionBuilder() {
		super(new ExpressionConfig<Double>() {
			@Override
			protected Double stringToOperand(String operand) {
				return Double.parseDouble(operand);
			}

			protected String operandToString(Double operand) {
				return operand == operand.intValue() ? String.valueOf(operand.intValue()) : operand.toString();
			}
		});

		this.initialize();
	}
	
	/**
	 * Initialize the operators, functions, and constants.
	 */
	protected void initialize() {
		ExpressionDictionary<Double> expressionDictionary = this.getExpressionDictionary();

		expressionDictionary.addOperator(new Operator<>("+", OperatorType.PREFIX, Integer.MAX_VALUE, (operands) -> operands.get(0)));
		expressionDictionary.addOperator(new Operator<>("-", OperatorType.PREFIX, Integer.MAX_VALUE, (operands) -> -operands.get(0)));

		expressionDictionary.addOperator(new Operator<>("+", OperatorType.INFIX, 1, (operands) -> operands.get(0) + operands.get(1)));
		expressionDictionary.addOperator(new Operator<>("-", OperatorType.INFIX, 1, (operands) -> operands.get(0) - operands.get(1)));

		expressionDictionary.addOperator(new Operator<>("*", OperatorType.INFIX, 2, (operands) -> operands.get(0) * operands.get(1)));
		expressionDictionary.addOperator(new Operator<>("/", OperatorType.INFIX, 2, (operands) -> operands.get(0) / operands.get(1)));
		expressionDictionary.addOperator(new Operator<>("%", OperatorType.INFIX, 2, (operands) -> operands.get(0) % operands.get(1)));

		expressionDictionary.addOperator(new Operator<>("^", OperatorType.INFIX_RTL, 3, (operands) -> Math.pow(operands.get(0), operands.get(1))));

		expressionDictionary.addOperator(new Operator<>("!", OperatorType.POSTFIX, 5, (operands) -> DoubleUtils.factorial(operands.get(0))));

		expressionDictionary.addOperator(new Operator<>("abs", OperatorType.PREFIX, 4, (operands) -> Math.abs(operands.get(0))));

		expressionDictionary.addOperator(new Operator<>("sin", OperatorType.PREFIX, 4, (operands) -> Math.sin(operands.get(0))));
		expressionDictionary.addOperator(new Operator<>("cos", OperatorType.PREFIX, 4, (operands) -> Math.cos(operands.get(0))));
		expressionDictionary.addOperator(new Operator<>("tan", OperatorType.PREFIX, 4, (operands) -> Math.tan(operands.get(0))));

		expressionDictionary.addOperator(new Operator<>("asin", OperatorType.PREFIX, 4, (operands) -> Math.asin(operands.get(0))));
		expressionDictionary.addOperator(new Operator<>("acos", OperatorType.PREFIX, 4, (operands) -> Math.acos(operands.get(0))));
		expressionDictionary.addOperator(new Operator<>("atan", OperatorType.PREFIX, 4, (operands) -> Math.atan(operands.get(0))));

		expressionDictionary.addOperator(new Operator<>("sinh", OperatorType.PREFIX, 4, (operands) -> Math.sinh(operands.get(0))));
		expressionDictionary.addOperator(new Operator<>("cosh", OperatorType.PREFIX, 4, (operands) -> Math.cosh(operands.get(0))));
		expressionDictionary.addOperator(new Operator<>("tanh", OperatorType.PREFIX, 4, (operands) -> Math.tanh(operands.get(0))));

		expressionDictionary.addOperator(new Operator<>("asinh", OperatorType.PREFIX, 4, (operands) -> DoubleUtils.asinh(operands.get(0))));
		expressionDictionary.addOperator(new Operator<>("acosh", OperatorType.PREFIX, 4, (operands) -> DoubleUtils.acosh(operands.get(0))));
		expressionDictionary.addOperator(new Operator<>("atanh", OperatorType.PREFIX, 4, (operands) -> DoubleUtils.atanh(operands.get(0))));

		expressionDictionary.addFunction(new Function<>("deg", 1, (operands) -> Math.toDegrees(operands.get(0))));
		expressionDictionary.addFunction(new Function<>("rad", 1, (operands) -> Math.toRadians(operands.get(0))));

		expressionDictionary.addOperator(new Operator<>("round", OperatorType.PREFIX, 4, (operands) -> (double) Math.round(operands.get(0))));
		expressionDictionary.addOperator(new Operator<>("floor", OperatorType.PREFIX, 4, (operands) -> Math.floor(operands.get(0))));
		expressionDictionary.addOperator(new Operator<>("ceil", OperatorType.PREFIX, 4, (operands) -> Math.ceil(operands.get(0))));

		expressionDictionary.addOperator(new Operator<>("ln", OperatorType.PREFIX, 4, (operands) -> Math.log(operands.get(0))));
		expressionDictionary.addOperator(new Operator<>("log10", OperatorType.PREFIX, 4, (operands) -> Math.log10(operands.get(0))));
		expressionDictionary.addFunction(new Function<>("log", 2, (operands) -> DoubleUtils.log(operands.get(1), operands.get(0))));

		expressionDictionary.addOperator(new Operator<>("sqrt", OperatorType.PREFIX, 4, (operands) -> Math.sqrt(operands.get(0))));
		expressionDictionary.addOperator(new Operator<>("cbrt", OperatorType.PREFIX, 4, (operands) -> Math.cbrt(operands.get(0))));

		expressionDictionary.addFunction(new Function<>("exp", 1, (operands) -> Math.exp(operands.get(0))));

		expressionDictionary.addFunction(new Function<>("max", (operands) -> operands.isEmpty() ? 0.0 : Collections.max(operands)));
		expressionDictionary.addFunction(new Function<>("min", (operands) -> operands.isEmpty() ? 0.0 : Collections.min(operands)));

		expressionDictionary.addFunction(new Function<>("mean", (operands) -> DoubleUtils.average(operands)));
		expressionDictionary.addFunction(new Function<>("average", (operands) -> DoubleUtils.average(operands)));

		expressionDictionary.addFunction(new Function<>("rand", 0, (operands) -> Math.random()));

		expressionDictionary.addConstant("pi", Math.PI);
		expressionDictionary.addConstant("e", Math.E);
	}
	
}
