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

import ch.obermuhlner.math.big.BigDecimalMath;
import in.pratanumandal.expr4j.expression.ExpressionBuilder;
import in.pratanumandal.expr4j.expression.ExpressionConfig;
import in.pratanumandal.expr4j.expression.ExpressionDictionary;
import in.pratanumandal.expr4j.impl.utils.BigDecimalUtils;
import in.pratanumandal.expr4j.token.Function;
import in.pratanumandal.expr4j.token.Operator;
import in.pratanumandal.expr4j.token.OperatorType;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * The <code>BigDecimalExpressionBuilder</code> class provides an implementation to parse expressions for parameters of type {@link BigDecimal}.<br>
 * 
 * @author Pratanu Mandal
 * @since 1.0
 *
 */
public class BigDecimalExpressionBuilder extends ExpressionBuilder<BigDecimal> {

	/** Default math context */
	public static final MathContext DEFAULT_CONTEXT = new MathContext(20, RoundingMode.HALF_UP);

	/** Math context */
	private MathContext mathContext;

	/**
	 * No-Argument Constructor.
	 */
	public BigDecimalExpressionBuilder() {
		this(DEFAULT_CONTEXT);
	}

	/**
	 * Parameterized constructor.
	 *
	 * @param precision Precision
	 */
	public BigDecimalExpressionBuilder(int precision) {
		this(precision, DEFAULT_CONTEXT.getRoundingMode());
	}

	/**
	 * Parameterized constructor.
	 *
	 * @param precision Precision
	 * @param roundingMode Rounding mode
	 */
	public BigDecimalExpressionBuilder(int precision, RoundingMode roundingMode) {
		this(new MathContext(precision, roundingMode));
	}

	/**
	 * Parameterized constructor.
	 *
	 * @param mathContext Math context
	 */
	public BigDecimalExpressionBuilder(MathContext mathContext) {
		super(new ExpressionConfig<BigDecimal>() {
			@Override
			protected BigDecimal stringToOperand(String operand) {
				return new BigDecimal(operand);
			}

			@Override
			protected String operandToString(BigDecimal operand) {
				return operand.toString();
			}
		});

		this.mathContext = mathContext;
		this.initialize();
	}

	/**
	 * Initialize the operators, functions, and constants.
	 */
	protected void initialize() {
		ExpressionDictionary<BigDecimal> expressionDictionary = this.getExpressionDictionary();

		expressionDictionary.addOperator(new Operator<>("+", OperatorType.PREFIX, Integer.MAX_VALUE, (parameters) -> parameters.get(0).value()));
		expressionDictionary.addOperator(new Operator<>("-", OperatorType.PREFIX, Integer.MAX_VALUE, (parameters) -> parameters.get(0).value().negate()));

		expressionDictionary.addOperator(new Operator<>("+", OperatorType.INFIX, 1, (parameters) -> parameters.get(0).value().add(parameters.get(1).value(), mathContext)));
		expressionDictionary.addOperator(new Operator<>("-", OperatorType.INFIX, 1, (parameters) -> parameters.get(0).value().subtract(parameters.get(1).value(), mathContext)));

		expressionDictionary.addOperator(new Operator<>("*", OperatorType.INFIX, 2, (parameters) -> parameters.get(0).value().multiply(parameters.get(1).value(), mathContext)));
		expressionDictionary.addOperator(new Operator<>("/", OperatorType.INFIX, 2, (parameters) -> parameters.get(0).value().divide(parameters.get(1).value(), mathContext)));
		expressionDictionary.addOperator(new Operator<>("%", OperatorType.INFIX, 2, (parameters) -> parameters.get(0).value().remainder(parameters.get(1).value(), mathContext)));

		expressionDictionary.addOperator(new Operator<>("^", OperatorType.INFIX_RTL, 3, (parameters) -> BigDecimalMath.pow(parameters.get(0).value(), parameters.get(1).value(), mathContext)));

		expressionDictionary.addOperator(new Operator<>("!", OperatorType.POSTFIX, 5, (parameters) -> BigDecimalUtils.factorial(parameters.get(0).value())));

		expressionDictionary.addOperator(new Operator<>("abs", OperatorType.PREFIX, 4, (parameters) -> parameters.get(0).value().abs(mathContext)));

		expressionDictionary.addOperator(new Operator<>("sin", OperatorType.PREFIX, 4, (parameters) -> BigDecimalMath.sin(parameters.get(0).value(), mathContext)));
		expressionDictionary.addOperator(new Operator<>("cos", OperatorType.PREFIX, 4, (parameters) -> BigDecimalMath.cos(parameters.get(0).value(), mathContext)));
		expressionDictionary.addOperator(new Operator<>("tan", OperatorType.PREFIX, 4, (parameters) -> BigDecimalMath.tan(parameters.get(0).value(), mathContext)));

		expressionDictionary.addOperator(new Operator<>("asin", OperatorType.PREFIX, 4, (parameters) -> BigDecimalMath.asin(parameters.get(0).value(), mathContext)));
		expressionDictionary.addOperator(new Operator<>("acos", OperatorType.PREFIX, 4, (parameters) -> BigDecimalMath.acos(parameters.get(0).value(), mathContext)));
		expressionDictionary.addOperator(new Operator<>("atan", OperatorType.PREFIX, 4, (parameters) -> BigDecimalMath.atan(parameters.get(0).value(), mathContext)));

		expressionDictionary.addOperator(new Operator<>("sinh", OperatorType.PREFIX, 4, (parameters) -> BigDecimalMath.sinh(parameters.get(0).value(), mathContext)));
		expressionDictionary.addOperator(new Operator<>("cosh", OperatorType.PREFIX, 4, (parameters) -> BigDecimalMath.cosh(parameters.get(0).value(), mathContext)));
		expressionDictionary.addOperator(new Operator<>("tanh", OperatorType.PREFIX, 4, (parameters) -> BigDecimalMath.tanh(parameters.get(0).value(), mathContext)));

		expressionDictionary.addOperator(new Operator<>("asinh", OperatorType.PREFIX, 4, (parameters) -> BigDecimalMath.asinh(parameters.get(0).value(), mathContext)));
		expressionDictionary.addOperator(new Operator<>("acosh", OperatorType.PREFIX, 4, (parameters) -> BigDecimalMath.acosh(parameters.get(0).value(), mathContext)));
		expressionDictionary.addOperator(new Operator<>("atanh", OperatorType.PREFIX, 4, (parameters) -> BigDecimalMath.atanh(parameters.get(0).value(), mathContext)));

		expressionDictionary.addFunction(new Function<>("deg", 1, (parameters) -> BigDecimalMath.toDegrees(parameters.get(0).value(), mathContext)));
		expressionDictionary.addFunction(new Function<>("rad", 1, (parameters) -> BigDecimalMath.toRadians(parameters.get(0).value(), mathContext)));

		expressionDictionary.addOperator(new Operator<>("round", OperatorType.PREFIX, 4, (parameters) -> parameters.get(0).value().setScale(0, RoundingMode.HALF_UP)));
		expressionDictionary.addOperator(new Operator<>("floor", OperatorType.PREFIX, 4, (parameters) -> parameters.get(0).value().setScale(0, RoundingMode.FLOOR)));
		expressionDictionary.addOperator(new Operator<>("ceil", OperatorType.PREFIX, 4, (parameters) -> parameters.get(0).value().setScale(0, RoundingMode.CEILING)));

		expressionDictionary.addOperator(new Operator<>("ln", OperatorType.PREFIX, 4, (parameters) -> BigDecimalMath.log(parameters.get(0).value(), mathContext)));
		expressionDictionary.addOperator(new Operator<>("log10", OperatorType.PREFIX, 4, (parameters) -> BigDecimalMath.log10(parameters.get(0).value(), mathContext)));
		expressionDictionary.addFunction(new Function<>("log", 2, (parameters) -> BigDecimalUtils.log(parameters.get(1).value(), parameters.get(0).value(), mathContext)));

		expressionDictionary.addOperator(new Operator<>("sqrt", OperatorType.PREFIX, 4, (parameters) -> BigDecimalMath.sqrt(parameters.get(0).value(), mathContext)));
		expressionDictionary.addOperator(new Operator<>("cbrt", OperatorType.PREFIX, 4, (parameters) -> BigDecimalUtils.cbrt(parameters.get(0).value(), mathContext)));

		expressionDictionary.addFunction(new Function<>("exp", 1, (parameters) -> BigDecimalMath.exp(parameters.get(0).value(), mathContext)));

		expressionDictionary.addFunction(new Function<>("max", (parameters) -> parameters.isEmpty() ? BigDecimal.ZERO : Collections.max(parameters.stream().map(e -> e.value()).collect(Collectors.toList()))));
		expressionDictionary.addFunction(new Function<>("min", (parameters) -> parameters.isEmpty() ? BigDecimal.ZERO : Collections.min(parameters.stream().map(e -> e.value()).collect(Collectors.toList()))));

		expressionDictionary.addFunction(new Function<>("mean", (parameters) -> BigDecimalUtils.average(parameters.stream().map(e -> e.value()).collect(Collectors.toList()), mathContext)));
		expressionDictionary.addFunction(new Function<>("average", (parameters) -> BigDecimalUtils.average(parameters.stream().map(e -> e.value()).collect(Collectors.toList()), mathContext)));

		expressionDictionary.addFunction(new Function<>("rand", 0, (parameters) -> new BigDecimal(Math.random())));

		expressionDictionary.addConstant("pi", BigDecimalMath.pi(mathContext));
		expressionDictionary.addConstant("e", BigDecimalMath.e(mathContext));
	}

	/**
	 * Get the math context.
	 *
	 * @return Math context
	 */
	public MathContext getMathContext() {
		return mathContext;
	}

	/**
	 * Set the math context.
	 *
	 * @param mathContext Math context
	 */
	public void setMathContext(MathContext mathContext) {
		this.reset();
		this.mathContext = mathContext;
		this.initialize();
	}
	
}
