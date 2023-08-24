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

import in.pratanumandal.expr4j.expression.ExpressionBuilder;
import in.pratanumandal.expr4j.expression.ExpressionConfig;
import in.pratanumandal.expr4j.expression.ExpressionDictionary;
import in.pratanumandal.expr4j.impl.utils.DoubleUtils;
import in.pratanumandal.expr4j.token.Function;
import in.pratanumandal.expr4j.token.Operator;
import in.pratanumandal.expr4j.token.OperatorType;

import java.util.Collections;
import java.util.stream.Collectors;

/**
 * The <code>DoubleExpressionBuilder</code> class provides an implementation to parse expressions for parameters of type {@link Double}.<br>
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

		expressionDictionary.addOperator(new Operator<>("+", OperatorType.PREFIX, Integer.MAX_VALUE, (parameters) -> parameters.get(0).value()));
		expressionDictionary.addOperator(new Operator<>("-", OperatorType.PREFIX, Integer.MAX_VALUE, (parameters) -> -parameters.get(0).value()));

		expressionDictionary.addOperator(new Operator<>("+", OperatorType.INFIX, 1, (parameters) -> parameters.get(0).value() + parameters.get(1).value()));
		expressionDictionary.addOperator(new Operator<>("-", OperatorType.INFIX, 1, (parameters) -> parameters.get(0).value() - parameters.get(1).value()));

		expressionDictionary.addOperator(new Operator<>("*", OperatorType.INFIX, 2, (parameters) -> parameters.get(0).value() * parameters.get(1).value()));
		expressionDictionary.addOperator(new Operator<>("/", OperatorType.INFIX, 2, (parameters) -> parameters.get(0).value() / parameters.get(1).value()));
		expressionDictionary.addOperator(new Operator<>("%", OperatorType.INFIX, 2, (parameters) -> parameters.get(0).value() % parameters.get(1).value()));

		expressionDictionary.addOperator(new Operator<>("^", OperatorType.INFIX_RTL, 3, (parameters) -> Math.pow(parameters.get(0).value(), parameters.get(1).value())));

		expressionDictionary.addOperator(new Operator<>("!", OperatorType.POSTFIX, 5, (parameters) -> DoubleUtils.factorial(parameters.get(0).value())));

		expressionDictionary.addOperator(new Operator<>("abs", OperatorType.PREFIX, 4, (parameters) -> Math.abs(parameters.get(0).value())));

		expressionDictionary.addOperator(new Operator<>("sin", OperatorType.PREFIX, 4, (parameters) -> Math.sin(parameters.get(0).value())));
		expressionDictionary.addOperator(new Operator<>("cos", OperatorType.PREFIX, 4, (parameters) -> Math.cos(parameters.get(0).value())));
		expressionDictionary.addOperator(new Operator<>("tan", OperatorType.PREFIX, 4, (parameters) -> Math.tan(parameters.get(0).value())));

		expressionDictionary.addOperator(new Operator<>("asin", OperatorType.PREFIX, 4, (parameters) -> Math.asin(parameters.get(0).value())));
		expressionDictionary.addOperator(new Operator<>("acos", OperatorType.PREFIX, 4, (parameters) -> Math.acos(parameters.get(0).value())));
		expressionDictionary.addOperator(new Operator<>("atan", OperatorType.PREFIX, 4, (parameters) -> Math.atan(parameters.get(0).value())));

		expressionDictionary.addOperator(new Operator<>("sinh", OperatorType.PREFIX, 4, (parameters) -> Math.sinh(parameters.get(0).value())));
		expressionDictionary.addOperator(new Operator<>("cosh", OperatorType.PREFIX, 4, (parameters) -> Math.cosh(parameters.get(0).value())));
		expressionDictionary.addOperator(new Operator<>("tanh", OperatorType.PREFIX, 4, (parameters) -> Math.tanh(parameters.get(0).value())));

		expressionDictionary.addOperator(new Operator<>("asinh", OperatorType.PREFIX, 4, (parameters) -> DoubleUtils.asinh(parameters.get(0).value())));
		expressionDictionary.addOperator(new Operator<>("acosh", OperatorType.PREFIX, 4, (parameters) -> DoubleUtils.acosh(parameters.get(0).value())));
		expressionDictionary.addOperator(new Operator<>("atanh", OperatorType.PREFIX, 4, (parameters) -> DoubleUtils.atanh(parameters.get(0).value())));

		expressionDictionary.addFunction(new Function<>("deg", 1, (parameters) -> Math.toDegrees(parameters.get(0).value())));
		expressionDictionary.addFunction(new Function<>("rad", 1, (parameters) -> Math.toRadians(parameters.get(0).value())));

		expressionDictionary.addOperator(new Operator<>("round", OperatorType.PREFIX, 4, (parameters) -> (double) Math.round(parameters.get(0).value())));
		expressionDictionary.addOperator(new Operator<>("floor", OperatorType.PREFIX, 4, (parameters) -> Math.floor(parameters.get(0).value())));
		expressionDictionary.addOperator(new Operator<>("ceil", OperatorType.PREFIX, 4, (parameters) -> Math.ceil(parameters.get(0).value())));

		expressionDictionary.addOperator(new Operator<>("ln", OperatorType.PREFIX, 4, (parameters) -> Math.log(parameters.get(0).value())));
		expressionDictionary.addOperator(new Operator<>("log10", OperatorType.PREFIX, 4, (parameters) -> Math.log10(parameters.get(0).value())));
		expressionDictionary.addFunction(new Function<>("log", 2, (parameters) -> DoubleUtils.log(parameters.get(1).value(), parameters.get(0).value())));

		expressionDictionary.addOperator(new Operator<>("sqrt", OperatorType.PREFIX, 4, (parameters) -> Math.sqrt(parameters.get(0).value())));
		expressionDictionary.addOperator(new Operator<>("cbrt", OperatorType.PREFIX, 4, (parameters) -> Math.cbrt(parameters.get(0).value())));

		expressionDictionary.addFunction(new Function<>("exp", 1, (parameters) -> Math.exp(parameters.get(0).value())));

		expressionDictionary.addFunction(new Function<>("max", (parameters) -> parameters.isEmpty() ? 0.0 : Collections.max(parameters.stream().map(e -> e.value()).collect(Collectors.toList()))));
		expressionDictionary.addFunction(new Function<>("min", (parameters) -> parameters.isEmpty() ? 0.0 : Collections.min(parameters.stream().map(e -> e.value()).collect(Collectors.toList()))));

		expressionDictionary.addFunction(new Function<>("mean", (parameters) -> DoubleUtils.average(parameters.stream().map(e -> e.value()).collect(Collectors.toList()))));
		expressionDictionary.addFunction(new Function<>("average", (parameters) -> DoubleUtils.average(parameters.stream().map(e -> e.value()).collect(Collectors.toList()))));

		expressionDictionary.addFunction(new Function<>("rand", 0, (parameters) -> Math.random()));

		expressionDictionary.addConstant("pi", Math.PI);
		expressionDictionary.addConstant("e", Math.E);
	}
	
}
