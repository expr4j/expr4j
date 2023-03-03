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

import ch.obermuhlner.math.big.BigDecimalMath;
import in.pratanumandal.expr4j.parser.utils.BigDecimalUtils;
import in.pratanumandal.expr4j.token.Function;
import in.pratanumandal.expr4j.token.Operator;
import in.pratanumandal.expr4j.token.Operator.OperatorType;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collections;

/**
 * The <code>BigDecimalParser</code> class provides an implementation to parse expressions for operands of type {@link BigDecimal}.<br>
 * 
 * @author Pratanu Mandal
 * @since 1.0
 *
 */
public class BigDecimalParser extends ExpressionParser<BigDecimal> {

	/** Default precision */
	public static final int DEFAULT_PRECISION = 20;

	/** Default rounding mode */
	public static final RoundingMode DEFAULT_ROUNDING_MODE = RoundingMode.HALF_UP;

	/** Math context */
	private MathContext mathContext;

	/**
	 * No-Argument Constructor.
	 */
	public BigDecimalParser() {
		this(DEFAULT_PRECISION);
	}

	/**
	 * Parameterized constructor.
	 *
	 * @param precision Precision
	 */
	public BigDecimalParser(int precision) {
		this(precision, DEFAULT_ROUNDING_MODE);
	}

	/**
	 * Parameterized constructor.
	 *
	 * @param precision Precision
	 * @param roundingMode Rounding mode
	 */
	public BigDecimalParser(int precision, RoundingMode roundingMode) {
		this(new MathContext(precision, roundingMode));
	}

	/**
	 * Parameterized constructor.
	 *
	 * @param mathContext Math context
	 */
	public BigDecimalParser(MathContext mathContext) {
		super();
		this.mathContext = mathContext;
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
			new Operator<>("/", OperatorType.INFIX, 2, (operands) -> operands.get(0).divide(operands.get(1), mathContext)),
			new Operator<>("%", OperatorType.INFIX, 2, (operands) -> operands.get(0).remainder(operands.get(1))),

			new Operator<>("^", OperatorType.INFIX_RTL, 3, (operands) -> BigDecimalMath.pow(operands.get(0), operands.get(1), mathContext)),

			new Operator<>("!", OperatorType.SUFFIX, 5, (operands) -> BigDecimalUtils.factorial(operands.get(0))),

			new Operator<>("abs", OperatorType.PREFIX, 4, (operands) -> operands.get(0).abs(mathContext)),

			new Operator<>("sin", OperatorType.PREFIX, 4, (operands) -> BigDecimalMath.sin(operands.get(0), mathContext)),
			new Operator<>("cos", OperatorType.PREFIX, 4, (operands) -> BigDecimalMath.cos(operands.get(0), mathContext)),
			new Operator<>("tan", OperatorType.PREFIX, 4, (operands) -> BigDecimalMath.tan(operands.get(0), mathContext)),

			new Operator<>("asin", OperatorType.PREFIX, 4, (operands) -> BigDecimalMath.asin(operands.get(0), mathContext)),
			new Operator<>("acos", OperatorType.PREFIX, 4, (operands) -> BigDecimalMath.acos(operands.get(0), mathContext)),
			new Operator<>("atan", OperatorType.PREFIX, 4, (operands) -> BigDecimalMath.atan(operands.get(0), mathContext)),

			new Operator<>("sinh", OperatorType.PREFIX, 4, (operands) -> BigDecimalMath.sinh(operands.get(0), mathContext)),
			new Operator<>("cosh", OperatorType.PREFIX, 4, (operands) -> BigDecimalMath.cosh(operands.get(0), mathContext)),
			new Operator<>("tanh", OperatorType.PREFIX, 4, (operands) -> BigDecimalMath.tanh(operands.get(0), mathContext)),

			new Operator<>("asinh", OperatorType.PREFIX, 4, (operands) -> BigDecimalMath.asinh(operands.get(0), mathContext)),
			new Operator<>("acosh", OperatorType.PREFIX, 4, (operands) -> BigDecimalMath.acosh(operands.get(0), mathContext)),
			new Operator<>("atanh", OperatorType.PREFIX, 4, (operands) -> BigDecimalMath.atanh(operands.get(0), mathContext)),

			new Function<>("deg", 1, (operands) -> BigDecimalMath.toDegrees(operands.get(0), mathContext)),
			new Function<>("rad", 1, (operands) -> BigDecimalMath.toRadians(operands.get(0), mathContext)),

			new Operator<>("round", OperatorType.PREFIX, 4, (operands) -> operands.get(0).setScale(0, RoundingMode.HALF_UP)),
			new Operator<>("floor", OperatorType.PREFIX, 4, (operands) -> operands.get(0).setScale(0, RoundingMode.FLOOR)),
			new Operator<>("ceil", OperatorType.PREFIX, 4, (operands) -> operands.get(0).setScale(0, RoundingMode.CEILING)),

			new Operator<>("ln", OperatorType.PREFIX, 4, (operands) -> BigDecimalMath.log(operands.get(0), mathContext)),
			new Operator<>("log10", OperatorType.PREFIX, 4, (operands) -> BigDecimalMath.log10(operands.get(0), mathContext)),
			new Function<>("log", 2, (operands) -> BigDecimalUtils.log(operands.get(1), operands.get(0), mathContext)),

			new Operator<>("sqrt", OperatorType.PREFIX, 4, (operands) -> BigDecimalMath.sqrt(operands.get(0), mathContext)),
			new Operator<>("cbrt", OperatorType.PREFIX, 4, (operands) -> BigDecimalUtils.cbrt(operands.get(0), mathContext)),

			new Function<>("exp", 1, (operands) -> BigDecimalMath.exp(operands.get(0), mathContext)),

			new Function<>("max", (operands) -> operands.isEmpty() ? BigDecimal.ZERO : Collections.max(operands)),
			new Function<>("min", (operands) -> operands.isEmpty() ? BigDecimal.ZERO : Collections.min(operands)),

			new Function<>("mean", (operands) -> BigDecimalUtils.average(operands, mathContext)),
			new Function<>("average", (operands) -> BigDecimalUtils.average(operands, mathContext)),

			new Function<>("rand", 0, (operands) -> new BigDecimal(Math.random()))
		));

		addConstant("pi", BigDecimalMath.pi(mathContext));
		addConstant("e", BigDecimalMath.e(mathContext));
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

	/**
	 * Define operation of unary plus.
	 * 
	 * @param operand Operand of unary plus operation
	 * @return Result of unary plus operation
	 */
	@Override
	protected BigDecimal unaryPlus(BigDecimal operand) {
		return operand;
	}

	/**
	 * Define operation of unary minus.
	 * 
	 * @param operand Operand of unary minus operation
	 * @return Result of unary minus operation
	 */
	@Override
	protected BigDecimal unaryMinus(BigDecimal operand) {
		return operand.negate();
	}
	
	/**
	 * Define operation of implicit multiplication operation.
	 * 
	 * @param operand0 First operand of implicit multiplication operation
	 * @param operand1 Second operand of implicit multiplication operation
	 * @return Result of implicit multiplication operation
	 */
	@Override
	protected BigDecimal implicitMultiplication(BigDecimal operand0, BigDecimal operand1) {
		return operand0.multiply(operand1);
	}
	
	/**
	 * Method to define procedure to parse string representation of operand.
	 * 
	 * @param operand String representation of operand
	 * @return Parsed operand
	 */
	@Override
	protected BigDecimal stringToOperand(String operand) {
		return new BigDecimal(operand);
	}

	/**
	 * Method to define procedure to obtain string representation of operand.
	 * 
	 * @param operand Operand
	 * @return String representation of operand
	 */
	@Override
	protected String operandToString(BigDecimal operand) {
		return operand.toString();
	}
	
}
