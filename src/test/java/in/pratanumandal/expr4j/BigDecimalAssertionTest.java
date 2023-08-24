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

package in.pratanumandal.expr4j;

import in.pratanumandal.expr4j.expression.Expression;
import in.pratanumandal.expr4j.expression.ExpressionDictionary;
import in.pratanumandal.expr4j.impl.BigDecimalExpressionBuilder;
import in.pratanumandal.expr4j.impl.utils.BigDecimalUtils;
import in.pratanumandal.expr4j.token.Function;
import in.pratanumandal.expr4j.token.Operator;
import in.pratanumandal.expr4j.token.OperatorType;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class BigDecimalAssertionTest {

	public static int PRECISION = 10;

	protected BigDecimalExpressionBuilder builder = new BigDecimalExpressionBuilder();

    protected ExpressionDictionary<BigDecimal> expressionDictionary = builder.getExpressionDictionary();

	private void assertEquals(BigDecimal expected, BigDecimal actual) {
		Assert.assertTrue(BigDecimalUtils.equals(expected, actual, PRECISION));
	}

	@Test
	public void test1() {
		BigDecimal expected = new BigDecimal(8.02981363726);
		String expectedString = "5 + 3 / cos(sin(-6)) ^ 0.25";

		Expression<BigDecimal> expression = builder.build("5+3/cos(sin(-6))^0.25");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test2() {
		BigDecimal expected = new BigDecimal(99.99);
		String expectedString = "1E+2 - 0.01";

		Expression<BigDecimal> expression = builder.build("1e+2 - 1e-2");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test3() {
		BigDecimal expected = new BigDecimal(1.0);
		String expectedString = "ceil(rand())";

		Expression<BigDecimal> expression = builder.build("ceil(rand())");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test4() {
		BigDecimal expected = new BigDecimal(-1.0);
		String expectedString = "floor(-rand())";

		Expression<BigDecimal> expression = builder.build("floor(-rand())");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test5() {
		BigDecimal expected = new BigDecimal(30.0);
		String expectedString = "deg(asin(sin(rad(30))))";

		Expression<BigDecimal> expression = builder.build("deg(asin(sin(rad(30))))");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test6() {
		BigDecimal expected = new BigDecimal(0.37222364116);
		String expectedString = "log(ln(2 + 3) * 4, 2)";

		Expression<BigDecimal> expression = builder.build("log((ln(2 + 3) * 4), 2)");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test7() {
		BigDecimal expected = new BigDecimal(6.30929753571);
		String expectedString = "log(3, max(5 ^ 4, 4 ^ 5))";

		Expression<BigDecimal> expression = builder.build("log(3, max(5 ^ 4, 4 ^ 5))");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test8() {
		BigDecimal expected = new BigDecimal(-5.0);
		String expectedString = "(2 + 3) * 4 - 5 ^ 2";

		Expression<BigDecimal> expression = builder.build("(2 + 3) * 4 - (5 ^ 2)");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test9() {
		BigDecimal expected = new BigDecimal(1.26185950714);
		String expectedString = "log(3, max(ln 10, 4))";

		Expression<BigDecimal> expression = builder.build("log(3, max(ln 10, 4))");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test10() {
		BigDecimal expected = new BigDecimal(0.54047630885);
		String expectedString = "log(5 + 2 * 4, max(log10 10, 4))";

		Expression<BigDecimal> expression = builder.build("log(5 + (2) * 4, max(log10(10), 4))");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test11() {
		BigDecimal expected = new BigDecimal(1.85021985907);
		String expectedString = "log(max(ln 10, 4), 5 + 2 * 4)";

		Expression<BigDecimal> expression = builder.build("log(max(ln(10), 4), 5 + (2) * 4)");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test12() {
		BigDecimal expected = new BigDecimal(0.0);
		String expectedString = "floor(rand())";

		Expression<BigDecimal> expression = builder.build("floor(rand())");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test13() {
		BigDecimal expected = new BigDecimal(-3.0);
		String expectedString = "2 + (3 - 4) * 5";

		Expression<BigDecimal> expression = builder.build("2 + (3 - 4) * 5");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test14() {
		expressionDictionary.addFunction(new Function<>("avg", (parameters) ->
				parameters.stream().map(e -> e.value())
						.collect(Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))
						.divide(new BigDecimal(parameters.size()), builder.getMathContext())));

		BigDecimal expected = new BigDecimal(12.7434874639);
		String expectedString = "avg(2 + 3, min(5, 8) * 6 + 1 + pi, cos 9)";

		Expression<BigDecimal> expression = builder.build("avg(2 + 3, min(5, 8) * 6 + (1 + pi), cos(9))");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);

		expressionDictionary.removeFunction("avg");
	}

	@Test
	public void test15() {
		expressionDictionary.addFunction(new Function<>("ee", (parameters) ->
				parameters.stream().map(e -> e.value())
						.collect(Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))
						.divide(new BigDecimal(parameters.size()), builder.getMathContext())));

		BigDecimal expected = new BigDecimal(12.7434874639);
		String expectedString = "ee(2 + 3, max(5, 2) * 6 + 1 + pi, cos 9)";

		Expression<BigDecimal> expression = builder.build("ee(2 + 3, max(5, 2) * 6 + (1 + pi), cos(9))");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);

		expressionDictionary.removeFunction("ee");
	}

	@Test
	public void test16() {
		expressionDictionary.addFunction(new Function<>("esume", (parameters) ->
				parameters.stream().map(e -> e.value())
						.collect(Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))
						.divide(new BigDecimal(parameters.size()), builder.getMathContext())));

		BigDecimal expected = new BigDecimal(12.7434874639);
		String expectedString = "esume(2 + 3, max(5, 2) * 6 + 1 + pi, cos 9)";

		Expression<BigDecimal> expression = builder.build("esume(2 + 3, max(5, 2) * 6 + (1 + pi), cos(9))");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);

		expressionDictionary.removeFunction("esume");
	}

	@Test
	public void test17() {
		expressionDictionary.addFunction(new Function<>("pisumpi", (parameters) ->
				parameters.stream().map(e -> e.value())
						.collect(Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))
						.divide(new BigDecimal(parameters.size()), builder.getMathContext())));

		BigDecimal expected = new BigDecimal(12.7434874639);
		String expectedString = "pisumpi(2 + 3, max(5, 2) * 6 + 1 + pi, cos 9)";

		Expression<BigDecimal> expression = builder.build("pisumpi(2 + 3, max(5, 2) * 6 + (1 + pi), cos(9))");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);

		expressionDictionary.removeFunction("pisumpi");
	}

	@Test
	public void test18() {
		expressionDictionary.addFunction(new Function<>("esumpi", (parameters) ->
				parameters.stream().map(e -> e.value())
						.collect(Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))
						.divide(new BigDecimal(parameters.size()), builder.getMathContext())));

		BigDecimal expected = new BigDecimal(12.7434874639);
		String expectedString = "esumpi(2 + 3, max(5, 2) * 6 + 1 + pi, cos 9)";

		Expression<BigDecimal> expression = builder.build("esumpi(2 + 3, max(5, 2) * 6 + (1 + pi), cos(9))");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);

		expressionDictionary.removeFunction("esumpi");
	}

	@Test
	public void test19() {
		expressionDictionary.addFunction(new Function<>("uminusFunc", (parameters) ->
				parameters.stream().map(e -> e.value())
						.collect(Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))
						.divide(new BigDecimal(parameters.size()), builder.getMathContext())));

		BigDecimal expected = new BigDecimal(12.7434874639);
		String expectedString = "uminusFunc(2 + 3, max(5, 2) * 6 + 1 + pi, cos 9)";

		Expression<BigDecimal> expression = builder.build("uminusFunc(2 + 3, max(5, 2) * 6 + (1 + pi), cos(9))");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);

		expressionDictionary.removeFunction("uminusFunc");
	}

	@Test
	public void test20() {
		expressionDictionary.addFunction(new Function<>("uplusFunc", (parameters) ->
				parameters.stream().map(e -> e.value())
						.collect(Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))
						.divide(new BigDecimal(parameters.size()), builder.getMathContext())));

		BigDecimal expected = new BigDecimal(12.7434874639);
		String expectedString = "uplusFunc(2 + 3, max(5, 2) * 6 + 1 + pi, cos 9)";

		Expression<BigDecimal> expression = builder.build("uplusFunc(2 + 3, max(5, 2) * 6 + (1 + pi), cos(9))");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);

		expressionDictionary.removeFunction("uplusFunc");
	}

	@Test
	public void test21() {
		expressionDictionary.addFunction(new Function<>("Funcuminus", (parameters) ->
				parameters.stream().map(e -> e.value())
						.collect(Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))
						.divide(new BigDecimal(parameters.size()), builder.getMathContext())));

		BigDecimal expected = new BigDecimal(12.7434874639);
		String expectedString = "Funcuminus(2 + 3, max(5, 2) * 6 + 1 + pi, cos 9)";

		Expression<BigDecimal> expression = builder.build("Funcuminus(2 + 3, max(5, 2) * 6 + (1 + pi), cos(9))");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);

		expressionDictionary.removeFunction("Funcuminus");
	}

	@Test
	public void test22() {
		expressionDictionary.addFunction(new Function<>("Funcuplus", (parameters) ->
				parameters.stream().map(e -> e.value())
						.collect(Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))
						.divide(new BigDecimal(parameters.size()), builder.getMathContext())));

		BigDecimal expected = new BigDecimal(12.7434874639);
		String expectedString = "Funcuplus(2 + 3, max(5, 2) * 6 + 1 + pi, cos 9)";

		Expression<BigDecimal> expression = builder.build("Funcuplus(2 + 3, max(5, 2) * 6 + (1 + pi), cos(9))");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);

		expressionDictionary.removeFunction("Funcuplus");
	}

	@Test
	public void test23() {
		expressionDictionary.addFunction(new Function<>("uminusFuncuminus", (parameters) ->
				parameters.stream().map(e -> e.value())
						.collect(Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))
						.divide(new BigDecimal(parameters.size()), builder.getMathContext())));

		BigDecimal expected = new BigDecimal(12.7434874639);
		String expectedString = "uminusFuncuminus(2 + 3, max(5, 2) * 6 + 1 + pi, cos 9)";

		Expression<BigDecimal> expression = builder.build("uminusFuncuminus(2 + 3, max(5, 2) * 6 + (1 + pi), cos(9))");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);

		expressionDictionary.removeFunction("uminusFuncuminus");
	}

	@Test
	public void test24() {
		expressionDictionary.addFunction(new Function<>("uplusFuncuplus", (parameters) ->
				parameters.stream().map(e -> e.value())
						.collect(Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))
						.divide(new BigDecimal(parameters.size()), builder.getMathContext())));

		BigDecimal expected = new BigDecimal(12.7434874639);
		String expectedString = "uplusFuncuplus(2 + 3, max(5, 2) * 6 + 1 + pi, cos 9)";

		Expression<BigDecimal> expression = builder.build("uplusFuncuplus(2 + 3, max(5, 2) * 6 + (1 + pi), cos(9))");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);

		expressionDictionary.removeFunction("uplusFuncuplus");
	}

	@Test
	public void test25() {
		BigDecimal expected = new BigDecimal(25);
		String expectedString = "5 * 5";

		Expression<BigDecimal> expression = builder.build("5 5");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test26() {
		BigDecimal expected = new BigDecimal(25);
		String expectedString = "5 * 5";

		Expression<BigDecimal> expression = builder.build("5 (5)");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test27() {
		BigDecimal expected = new BigDecimal(15);
		String expectedString = "5 * max(1, 2, 3)";

		Expression<BigDecimal> expression = builder.build("5 max(1, 2, 3)");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test28() {
		Map<String, BigDecimal> variables = new HashMap<>();
		variables.put("x", new BigDecimal(5.0));

		BigDecimal expected = new BigDecimal(25);
		String expectedString = "5 * x";

		Expression<BigDecimal> expression = builder.build("5x");

		BigDecimal actual = expression.evaluate(variables);
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test29() {
		Map<String, BigDecimal> variables = new HashMap<>();
		variables.put("x5", new BigDecimal(5.0));

		BigDecimal expected = new BigDecimal(5);
		String expectedString = "x5";

		Expression<BigDecimal> expression = builder.build("x5");

		BigDecimal actual = expression.evaluate(variables);
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test30() {
		BigDecimal expected = new BigDecimal(30);
		String expectedString = "+5 * 6";

		Expression<BigDecimal> expression = builder.build("+ 5 6");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test31() {
		BigDecimal expected = new BigDecimal("2.4178516392292583494E+24");
		String expectedString = "2 ^ (3 ^ 4)";

		Expression<BigDecimal> expression = builder.build("2 ^ 3 ^ 4");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test32() {
		BigDecimal expected = new BigDecimal(2.5);
		String expectedString = "5.5 % 3";

		Expression<BigDecimal> expression = builder.build("5.5 % 3");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test33() {
		expressionDictionary.addOperator(new Operator<>("incr", OperatorType.POSTFIX, 5,
				(parameters) -> parameters.get(0).value().add(BigDecimal.ONE)));

		BigDecimal expected = new BigDecimal(121);
		String expectedString = "(5 !) incr";

		Expression<BigDecimal> expression = builder.build("5 ! incr");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);

		expressionDictionary.removeOperator("incr");
	}

	@Test
	public void test34() {
		BigDecimal expected = new BigDecimal(0.27987335076);
		String expectedString = "sin(cos 5)";

		Expression<BigDecimal> expression = builder.build("sin cos 5");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test35() {
		BigDecimal expected = new BigDecimal(6.27987335076);
		String expectedString = "sin(cos 5) + 6";

		Expression<BigDecimal> expression = builder.build("sin cos 5 + 6");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test36() {
		BigDecimal expected = new BigDecimal(-0.95892427466);
		String expectedString = "+sin 5";

		Expression<BigDecimal> expression = builder.build("+ sin 5");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test37() {
		BigDecimal expected = new BigDecimal(0.95892427466);
		String expectedString = "-sin 5";

		Expression<BigDecimal> expression = builder.build("- sin 5");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test38() {
		BigDecimal expected = new BigDecimal(-4.79462137332);
		String expectedString = "5 * sin 5";

		Expression<BigDecimal> expression = builder.build("5 sin 5");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test39() {
		BigDecimal expected = new BigDecimal(99000);
		String expectedString = "10 ^ 5 - 10 ^ 3";

		Expression<BigDecimal> expression = builder.build("10 ^ 5 - 10 ^ 3");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test40() {
		BigDecimal expected = new BigDecimal(99999.999);
		String expectedString = "10 ^ 5 - 10 ^ -3";

		Expression<BigDecimal> expression = builder.build("10 ^ 5 - 10 ^ -3");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test41() {
		BigDecimal expected = new BigDecimal(-30);
		String expectedString = "-5 * 6";

		Expression<BigDecimal> expression = builder.build("-5 6");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test42() {
		BigDecimal expected = new BigDecimal(-30);
		String expectedString = "-5 * 6";

		Expression<BigDecimal> expression = builder.build("-5 * 6");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test43() {
		BigDecimal expected = new BigDecimal(-30);
		String expectedString = "-(5 * 6)";

		Expression<BigDecimal> expression = builder.build("-(5 6)");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test44() {
		BigDecimal expected = new BigDecimal(-30);
		String expectedString = "-(5 * 6)";

		Expression<BigDecimal> expression = builder.build("-(5 * 6)");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test45() {
		BigDecimal expected = new BigDecimal(30);
		String expectedString = "abs(-(5 * 6))";

		Expression<BigDecimal> expression = builder.build("abs -(5 * 6)");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test46() {
		BigDecimal expected = new BigDecimal(15);
		String expectedString = "1 + 2 + 3 + 4 + 5";

		Expression<BigDecimal> expression = builder.build("1 + 2 + 3 + 4 + 5");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test47() {
		BigDecimal expected = new BigDecimal(33614);
		String expectedString = "2 * (3 + 4) ^ 5";

		Expression<BigDecimal> expression = builder.build("2 (3 + 4) ^ 5");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test48() {
		BigDecimal expected = new BigDecimal(-0.27201055544);
		String expectedString = "sin 5 * cos 5";

		Expression<BigDecimal> expression = builder.build("sin 5 cos 5");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test49() {
		BigDecimal expected = new BigDecimal(2.31243834127);
		String expectedString = "asinh 5";

		Expression<BigDecimal> expression = builder.build("asinh 5");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test50() {
		BigDecimal expected = new BigDecimal(2.29243166956);
		String expectedString = "acosh 5";

		Expression<BigDecimal> expression = builder.build("acosh 5");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test51() {
		BigDecimal expected = new BigDecimal(0.54930614433);
		String expectedString = "atanh 0.5";

		Expression<BigDecimal> expression = builder.build("atanh 0.5");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test52() {
		BigDecimal expected = new BigDecimal(2.4);
		String expectedString = "average(1, 2, 2, 3, 4)";

		Expression<BigDecimal> expression = builder.build("average(1, 2, 2, 3, 4)");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test53() {
		BigDecimal expected = new BigDecimal(3);
		String expectedString = "cbrt 27";

		Expression<BigDecimal> expression = builder.build("cbrt(27)");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test54() {
		BigDecimal expected = new BigDecimal(-115.070912959576616);
		String expectedString = "5 ! * sin 5";

		Expression<BigDecimal> expression = builder.build("5! sin 5");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test55() {
		builder.setMathContext(new MathContext(5, RoundingMode.HALF_UP));

		BigDecimal expected = new BigDecimal(-115.07);
		String expectedString = "5 ! * sin 5";

		Expression<BigDecimal> expression = builder.build("5! sin 5");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);

		builder.setMathContext(BigDecimalExpressionBuilder.DEFAULT_CONTEXT);
	}

}
