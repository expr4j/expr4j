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
import in.pratanumandal.expr4j.impl.utils.ComplexUtils;
import in.pratanumandal.expr4j.token.Function;
import in.pratanumandal.expr4j.token.Operator;
import in.pratanumandal.expr4j.token.OperatorType;
import org.apache.commons.numbers.complex.Complex;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The <code>ComplexExpressionBuilder</code> class provides an implementation to parse expressions for parameters of type {@link Complex}.<br>
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
		super(new ExpressionConfig<Complex>() {
			@Override
			protected Complex stringToOperand(String operand) {
				if (!operand.contains("i")) {
					return Complex.ofCartesian(Double.parseDouble(operand), 0);
				}
				else {
					return Complex.ofCartesian(0, Double.parseDouble(operand.replaceAll("\\s*i", "")));
				}
			}

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
			protected List<String> getOperandPattern() {
				return Arrays.asList("(-?\\d+)(\\.\\d+)?(e-|e\\+|e|\\d+)\\d+(\\s*i)?", "\\d*\\.?\\d+(\\s*i)?");
			}
		});

		this.initialize();
	}
	
	/**
	 * Initialize the operators, functions, and constants.
	 */
	protected void initialize() {
		ExpressionDictionary<Complex> expressionDictionary = this.getExpressionDictionary();

		expressionDictionary.addOperator(new Operator<>("+", OperatorType.PREFIX, Integer.MAX_VALUE, (parameters) -> parameters.get(0).value()));
		expressionDictionary.addOperator(new Operator<>("-", OperatorType.PREFIX, Integer.MAX_VALUE, (parameters) -> parameters.get(0).value().negate()));

		expressionDictionary.addOperator(new Operator<>("+", OperatorType.INFIX, 1, (parameters) -> parameters.get(0).value().add(parameters.get(1).value())));
		expressionDictionary.addOperator(new Operator<>("-", OperatorType.INFIX, 1, (parameters) -> parameters.get(0).value().subtract(parameters.get(1).value())));

		expressionDictionary.addOperator(new Operator<>("*", OperatorType.INFIX, 2, (parameters) -> parameters.get(0).value().multiply(parameters.get(1).value())));
		expressionDictionary.addOperator(new Operator<>("/", OperatorType.INFIX, 2, (parameters) -> parameters.get(0).value().divide(parameters.get(1).value())));

		expressionDictionary.addOperator(new Operator<>("^", OperatorType.INFIX_RTL, 3, (parameters) -> parameters.get(0).value().pow(parameters.get(1).value())));

		expressionDictionary.addOperator(new Operator<>("abs", OperatorType.PREFIX, 4, (parameters) -> Complex.ofCartesian(parameters.get(0).value().abs(), 0)));

		expressionDictionary.addOperator(new Operator<>("sin", OperatorType.PREFIX, 4, (parameters) -> parameters.get(0).value().sin()));
		expressionDictionary.addOperator(new Operator<>("cos", OperatorType.PREFIX, 4, (parameters) -> parameters.get(0).value().cos()));
		expressionDictionary.addOperator(new Operator<>("tan", OperatorType.PREFIX, 4, (parameters) -> parameters.get(0).value().tan()));

		expressionDictionary.addOperator(new Operator<>("asin", OperatorType.PREFIX, 4, (parameters) -> parameters.get(0).value().asin()));
		expressionDictionary.addOperator(new Operator<>("acos", OperatorType.PREFIX, 4, (parameters) -> parameters.get(0).value().acos()));
		expressionDictionary.addOperator(new Operator<>("atan", OperatorType.PREFIX, 4, (parameters) -> parameters.get(0).value().atan()));

		expressionDictionary.addOperator(new Operator<>("sinh", OperatorType.PREFIX, 4, (parameters) -> parameters.get(0).value().sinh()));
		expressionDictionary.addOperator(new Operator<>("cosh", OperatorType.PREFIX, 4, (parameters) -> parameters.get(0).value().cosh()));
		expressionDictionary.addOperator(new Operator<>("tanh", OperatorType.PREFIX, 4, (parameters) -> parameters.get(0).value().tanh()));

		expressionDictionary.addOperator(new Operator<>("asinh", OperatorType.PREFIX, 4, (parameters) -> ComplexUtils.asinh(parameters.get(0).value())));
		expressionDictionary.addOperator(new Operator<>("acosh", OperatorType.PREFIX, 4, (parameters) -> ComplexUtils.acosh(parameters.get(0).value())));
		expressionDictionary.addOperator(new Operator<>("atanh", OperatorType.PREFIX, 4, (parameters) -> ComplexUtils.atanh(parameters.get(0).value())));

		expressionDictionary.addFunction(new Function<>("deg", 1, (parameters) -> ComplexUtils.toDegrees(parameters.get(0).value())));
		expressionDictionary.addFunction(new Function<>("rad", 1, (parameters) -> ComplexUtils.toRadians(parameters.get(0).value())));

		expressionDictionary.addOperator(new Operator<>("ln", OperatorType.PREFIX, 4, (parameters) -> parameters.get(0).value().log()));
		expressionDictionary.addOperator(new Operator<>("log10", OperatorType.PREFIX, 4, (parameters) -> ComplexUtils.log10(parameters.get(0).value())));
		expressionDictionary.addFunction(new Function<>("log", 2, (parameters) -> ComplexUtils.log(parameters.get(1).value(), parameters.get(0).value())));

		expressionDictionary.addOperator(new Operator<>("sqrt", OperatorType.PREFIX, 4, (parameters) -> parameters.get(0).value().sqrt()));
		expressionDictionary.addOperator(new Operator<>("cbrt", OperatorType.PREFIX, 4, (parameters) -> ComplexUtils.cbrt(parameters.get(0).value())));

		expressionDictionary.addFunction(new Function<>("exp", 1, (parameters) -> parameters.get(0).value().exp()));

		expressionDictionary.addFunction(new Function<>("max", (parameters) -> parameters.isEmpty() ? Complex.ZERO : ComplexUtils.max(parameters.stream().map(e -> e.value()).collect(Collectors.toList()))));
		expressionDictionary.addFunction(new Function<>("min", (parameters) -> parameters.isEmpty() ? Complex.ZERO : ComplexUtils.min(parameters.stream().map(e -> e.value()).collect(Collectors.toList()))));

		expressionDictionary.addFunction(new Function<>("mean", (parameters) -> ComplexUtils.average(parameters.stream().map(e -> e.value()).collect(Collectors.toList()))));
		expressionDictionary.addFunction(new Function<>("average", (parameters) -> ComplexUtils.average(parameters.stream().map(e -> e.value()).collect(Collectors.toList()))));

		expressionDictionary.addFunction(new Function<>("rand", 0, (parameters) -> Complex.ofCartesian(Math.random(), Math.random())));

		expressionDictionary.addConstant("pi", Complex.ofCartesian(Math.PI, 0));
		expressionDictionary.addConstant("e", Complex.ofCartesian(Math.E, 0));

		expressionDictionary.addConstant("i", Complex.I);
	}
	
}
