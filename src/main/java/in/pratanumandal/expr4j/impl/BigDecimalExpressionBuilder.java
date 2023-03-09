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
import in.pratanumandal.expr4j.ExpressionBuilder;
import in.pratanumandal.expr4j.ExpressionConfig;
import in.pratanumandal.expr4j.ExpressionDictionary;
import in.pratanumandal.expr4j.impl.utils.BigDecimalUtils;
import in.pratanumandal.expr4j.token.Function;
import in.pratanumandal.expr4j.token.Operator;
import in.pratanumandal.expr4j.token.OperatorType;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Collections;

/**
 * The <code>BigDecimalExpressionBuilder</code> class provides an implementation to parse expressions for operands of type {@link BigDecimal}.<br>
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

		expressionDictionary.addOperator(new Operator<>("+", OperatorType.PREFIX, Integer.MAX_VALUE, (operands) -> operands.get(0)));
		expressionDictionary.addOperator(new Operator<>("-", OperatorType.PREFIX, Integer.MAX_VALUE, (operands) -> operands.get(0).negate()));

		expressionDictionary.addOperator(new Operator<>("+", OperatorType.INFIX, 1, (operands) -> operands.get(0).add(operands.get(1), mathContext)));
		expressionDictionary.addOperator(new Operator<>("-", OperatorType.INFIX, 1, (operands) -> operands.get(0).subtract(operands.get(1), mathContext)));

		expressionDictionary.addOperator(new Operator<>("*", OperatorType.INFIX, 2, (operands) -> operands.get(0).multiply(operands.get(1), mathContext)));
		expressionDictionary.addOperator(new Operator<>("/", OperatorType.INFIX, 2, (operands) -> operands.get(0).divide(operands.get(1), mathContext)));
		expressionDictionary.addOperator(new Operator<>("%", OperatorType.INFIX, 2, (operands) -> operands.get(0).remainder(operands.get(1), mathContext)));

		expressionDictionary.addOperator(new Operator<>("^", OperatorType.INFIX_RTL, 3, (operands) -> BigDecimalMath.pow(operands.get(0), operands.get(1), mathContext)));

		expressionDictionary.addOperator(new Operator<>("!", OperatorType.POSTFIX, 5, (operands) -> BigDecimalUtils.factorial(operands.get(0))));

		expressionDictionary.addOperator(new Operator<>("abs", OperatorType.PREFIX, 4, (operands) -> operands.get(0).abs(mathContext)));

		expressionDictionary.addOperator(new Operator<>("sin", OperatorType.PREFIX, 4, (operands) -> BigDecimalMath.sin(operands.get(0), mathContext)));
		expressionDictionary.addOperator(new Operator<>("cos", OperatorType.PREFIX, 4, (operands) -> BigDecimalMath.cos(operands.get(0), mathContext)));
		expressionDictionary.addOperator(new Operator<>("tan", OperatorType.PREFIX, 4, (operands) -> BigDecimalMath.tan(operands.get(0), mathContext)));

		expressionDictionary.addOperator(new Operator<>("asin", OperatorType.PREFIX, 4, (operands) -> BigDecimalMath.asin(operands.get(0), mathContext)));
		expressionDictionary.addOperator(new Operator<>("acos", OperatorType.PREFIX, 4, (operands) -> BigDecimalMath.acos(operands.get(0), mathContext)));
		expressionDictionary.addOperator(new Operator<>("atan", OperatorType.PREFIX, 4, (operands) -> BigDecimalMath.atan(operands.get(0), mathContext)));

		expressionDictionary.addOperator(new Operator<>("sinh", OperatorType.PREFIX, 4, (operands) -> BigDecimalMath.sinh(operands.get(0), mathContext)));
		expressionDictionary.addOperator(new Operator<>("cosh", OperatorType.PREFIX, 4, (operands) -> BigDecimalMath.cosh(operands.get(0), mathContext)));
		expressionDictionary.addOperator(new Operator<>("tanh", OperatorType.PREFIX, 4, (operands) -> BigDecimalMath.tanh(operands.get(0), mathContext)));

		expressionDictionary.addOperator(new Operator<>("asinh", OperatorType.PREFIX, 4, (operands) -> BigDecimalMath.asinh(operands.get(0), mathContext)));
		expressionDictionary.addOperator(new Operator<>("acosh", OperatorType.PREFIX, 4, (operands) -> BigDecimalMath.acosh(operands.get(0), mathContext)));
		expressionDictionary.addOperator(new Operator<>("atanh", OperatorType.PREFIX, 4, (operands) -> BigDecimalMath.atanh(operands.get(0), mathContext)));

		expressionDictionary.addFunction(new Function<>("deg", 1, (operands) -> BigDecimalMath.toDegrees(operands.get(0), mathContext)));
		expressionDictionary.addFunction(new Function<>("rad", 1, (operands) -> BigDecimalMath.toRadians(operands.get(0), mathContext)));

		expressionDictionary.addOperator(new Operator<>("round", OperatorType.PREFIX, 4, (operands) -> operands.get(0).setScale(0, RoundingMode.HALF_UP)));
		expressionDictionary.addOperator(new Operator<>("floor", OperatorType.PREFIX, 4, (operands) -> operands.get(0).setScale(0, RoundingMode.FLOOR)));
		expressionDictionary.addOperator(new Operator<>("ceil", OperatorType.PREFIX, 4, (operands) -> operands.get(0).setScale(0, RoundingMode.CEILING)));

		expressionDictionary.addOperator(new Operator<>("ln", OperatorType.PREFIX, 4, (operands) -> BigDecimalMath.log(operands.get(0), mathContext)));
		expressionDictionary.addOperator(new Operator<>("log10", OperatorType.PREFIX, 4, (operands) -> BigDecimalMath.log10(operands.get(0), mathContext)));
		expressionDictionary.addFunction(new Function<>("log", 2, (operands) -> BigDecimalUtils.log(operands.get(1), operands.get(0), mathContext)));

		expressionDictionary.addOperator(new Operator<>("sqrt", OperatorType.PREFIX, 4, (operands) -> BigDecimalMath.sqrt(operands.get(0), mathContext)));
		expressionDictionary.addOperator(new Operator<>("cbrt", OperatorType.PREFIX, 4, (operands) -> BigDecimalUtils.cbrt(operands.get(0), mathContext)));

		expressionDictionary.addFunction(new Function<>("exp", 1, (operands) -> BigDecimalMath.exp(operands.get(0), mathContext)));

		expressionDictionary.addFunction(new Function<>("max", (operands) -> operands.isEmpty() ? BigDecimal.ZERO : Collections.max(operands)));
		expressionDictionary.addFunction(new Function<>("min", (operands) -> operands.isEmpty() ? BigDecimal.ZERO : Collections.min(operands)));

		expressionDictionary.addFunction(new Function<>("mean", (operands) -> BigDecimalUtils.average(operands, mathContext)));
		expressionDictionary.addFunction(new Function<>("average", (operands) -> BigDecimalUtils.average(operands, mathContext)));

		expressionDictionary.addFunction(new Function<>("rand", 0, (operands) -> new BigDecimal(Math.random())));

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
