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

import in.pratanumandal.expr4j.parser.BigDecimalParser;
import in.pratanumandal.expr4j.parser.utils.BigDecimalUtils;
import in.pratanumandal.expr4j.token.Function;
import in.pratanumandal.expr4j.token.Operator;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class BigDecimalAssertionTest {
	
	public static int PRECISION = 10;
	
	protected BigDecimalParser parser = new BigDecimalParser();

	private void assertEquals(BigDecimal expected, BigDecimal actual) {
		Assert.assertTrue(BigDecimalUtils.equals(expected, actual, PRECISION));
	}
	
	@Test
	public void test1() {
		BigDecimal expected = new BigDecimal(8.02981363726);
		String expectedString = "5 + (3 / (cos(sin(-6)) ^ 0.25))";

		Expression<BigDecimal> expression = parser.parse("5+3/cos(sin(-6))^0.25");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test2() {
		BigDecimal expected = new BigDecimal(99.99);
		String expectedString = "1E+2 - 0.01";

		Expression<BigDecimal> expression = parser.parse("1e+2 - 1e-2");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test3() {
		BigDecimal expected = new BigDecimal(1.0);
		String expectedString = "ceil(rand())";

		Expression<BigDecimal> expression = parser.parse("ceil(rand())");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test4() {
		BigDecimal expected = new BigDecimal(-1.0);
		String expectedString = "floor(-rand())";

		Expression<BigDecimal> expression = parser.parse("floor(-rand())");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test5() {
		BigDecimal expected = new BigDecimal(30.0);
		String expectedString = "deg(asin(sin(rad(30))))";

		Expression<BigDecimal> expression = parser.parse("deg(asin(sin(rad(30))))");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test6() {
		BigDecimal expected = new BigDecimal(0.37222364116);
		String expectedString = "log(ln(2 + 3) * 4, 2)";

		Expression<BigDecimal> expression = parser.parse("log((ln(2 + 3) * 4), 2)");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test7() {
		BigDecimal expected = new BigDecimal(6.30929753571);
		String expectedString = "log(3, max(5 ^ 4, 4 ^ 5))";

		Expression<BigDecimal> expression = parser.parse("log(3, max(5 ^ 4, 4 ^ 5))");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test8() {
		BigDecimal expected = new BigDecimal(-5.0);
		String expectedString = "((2 + 3) * 4) - (5 ^ 2)";

		Expression<BigDecimal> expression = parser.parse("(2 + 3) * 4 - (5 ^ 2)");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test9() {
		BigDecimal expected = new BigDecimal(1.26185950714);
		String expectedString = "log(3, max(ln 10, 4))";

		Expression<BigDecimal> expression = parser.parse("log(3, max(ln 10, 4))");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test10() {
		BigDecimal expected = new BigDecimal(0.54047630885);
		String expectedString = "log(5 + (2 * 4), max(ln 10, 4))";

		Expression<BigDecimal> expression = parser.parse("log(5 + (2) * 4, max(ln(10), 4))");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test11() {
		BigDecimal expected = new BigDecimal(1.85021985907);
		String expectedString = "log(max(ln 10, 4), 5 + (2 * 4))";

		Expression<BigDecimal> expression = parser.parse("log(max(ln(10), 4), 5 + (2) * 4)");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test12() {
		BigDecimal expected = new BigDecimal(0.0);
		String expectedString = "floor(rand())";

		Expression<BigDecimal> expression = parser.parse("floor(rand())");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test13() {
		BigDecimal expected = new BigDecimal(-3.0);
		String expectedString = "2 + ((3 - 4) * 5)";

		Expression<BigDecimal> expression = parser.parse("2 + (3 - 4) * 5");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test14() {
		parser.addExecutable(new Function<BigDecimal>("avg", (operands) -> {
    		BigDecimal sum = new BigDecimal(0);
    		for (BigDecimal operand : operands) {
    			sum = sum.add(operand);
    		}
    		return sum.divide(new BigDecimal(operands.size()), parser.getMathContext());
    	}));

		BigDecimal expected = new BigDecimal(12.7434874639);
		String expectedString = "avg(2 + 3, (max(5, 2) * 6) + (1 + pi), cos 9)";

		Expression<BigDecimal> expression = parser.parse("avg(2 + 3, max(5, 2) * 6 + (1 + pi), cos(9))");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);

		parser.removeExecutable("avg");
	}

	@Test
	public void test15() {
		parser.addExecutable(new Function<BigDecimal>("ee", (operands) -> {
    		BigDecimal sum = new BigDecimal(0);
    		for (BigDecimal operand : operands) {
    			sum = sum.add(operand);
    		}
			return sum.divide(new BigDecimal(operands.size()), parser.getMathContext());
    	}));

		BigDecimal expected = new BigDecimal(12.7434874639);
		String expectedString = "ee(2 + 3, (max(5, 2) * 6) + (1 + pi), cos 9)";

		Expression<BigDecimal> expression = parser.parse("ee(2 + 3, max(5, 2) * 6 + (1 + pi), cos(9))");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);

		parser.removeExecutable("ee");
	}

	@Test
	public void test16() {
		parser.addExecutable(new Function<BigDecimal>("esume", (operands) -> {
    		BigDecimal sum = new BigDecimal(0);
    		for (BigDecimal operand : operands) {
				sum = sum.add(operand);
    		}
			return sum.divide(new BigDecimal(operands.size()), parser.getMathContext());
    	}));

		BigDecimal expected = new BigDecimal(12.7434874639);
		String expectedString = "esume(2 + 3, (max(5, 2) * 6) + (1 + pi), cos 9)";

		Expression<BigDecimal> expression = parser.parse("esume(2 + 3, max(5, 2) * 6 + (1 + pi), cos(9))");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);

		parser.removeExecutable("esume");
	}

	@Test
	public void test17() {
		parser.addExecutable(new Function<BigDecimal>("pisumpi", (operands) -> {
			BigDecimal sum = new BigDecimal(0);
			for (BigDecimal operand : operands) {
				sum = sum.add(operand);
			}
			return sum.divide(new BigDecimal(operands.size()), parser.getMathContext());
    	}));

		BigDecimal expected = new BigDecimal(12.7434874639);;
		String expectedString = "pisumpi(2 + 3, (max(5, 2) * 6) + (1 + pi), cos 9)";

		Expression<BigDecimal> expression = parser.parse("pisumpi(2 + 3, max(5, 2) * 6 + (1 + pi), cos(9))");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);

		parser.removeExecutable("pisumpi");
	}

	@Test
	public void test18() {
		parser.addExecutable(new Function<BigDecimal>("esumpi", (operands) -> {
			BigDecimal sum = new BigDecimal(0);
			for (BigDecimal operand : operands) {
				sum = sum.add(operand);
			}
			return sum.divide(new BigDecimal(operands.size()), parser.getMathContext());
    	}));

		BigDecimal expected = new BigDecimal(12.7434874639);
		String expectedString = "esumpi(2 + 3, (max(5, 2) * 6) + (1 + pi), cos 9)";

		Expression<BigDecimal> expression = parser.parse("esumpi(2 + 3, max(5, 2) * 6 + (1 + pi), cos(9))");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);

		parser.removeExecutable("esumpi");
	}

	@Test
	public void test19() {
		parser.addExecutable(new Function<BigDecimal>("uminusFunc", (operands) -> {			BigDecimal sum = new BigDecimal(0);
			for (BigDecimal operand : operands) {
				sum = sum.add(operand);
			}
			return sum.divide(new BigDecimal(operands.size()), parser.getMathContext());
    	}));

		BigDecimal expected = new BigDecimal(12.7434874639);
		String expectedString = "uminusFunc(2 + 3, (max(5, 2) * 6) + (1 + pi), cos 9)";

		Expression<BigDecimal> expression = parser.parse("uminusFunc(2 + 3, max(5, 2) * 6 + (1 + pi), cos(9))");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);

		parser.removeExecutable("uminusFunc");
	}

	@Test
	public void test20() {
		parser.addExecutable(new Function<BigDecimal>("uplusFunc", (operands) -> {
			BigDecimal sum = new BigDecimal(0);
			for (BigDecimal operand : operands) {
				sum = sum.add(operand);
			}
			return sum.divide(new BigDecimal(operands.size()), parser.getMathContext());
    	}));

		BigDecimal expected = new BigDecimal(12.7434874639);
		String expectedString = "uplusFunc(2 + 3, (max(5, 2) * 6) + (1 + pi), cos 9)";

		Expression<BigDecimal> expression = parser.parse("uplusFunc(2 + 3, max(5, 2) * 6 + (1 + pi), cos(9))");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);

		parser.removeExecutable("uplusFunc");
	}

	@Test
	public void test21() {
		parser.addExecutable(new Function<BigDecimal>("Funcuminus", (operands) -> {
			BigDecimal sum = new BigDecimal(0);
			for (BigDecimal operand : operands) {
				sum = sum.add(operand);
			}
			return sum.divide(new BigDecimal(operands.size()), parser.getMathContext());
    	}));

		BigDecimal expected = new BigDecimal(12.7434874639);
		String expectedString = "Funcuminus(2 + 3, (max(5, 2) * 6) + (1 + pi), cos 9)";

		Expression<BigDecimal> expression = parser.parse("Funcuminus(2 + 3, max(5, 2) * 6 + (1 + pi), cos(9))");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);

		parser.removeExecutable("Funcuminus");
	}

	@Test
	public void test22() {
		parser.addExecutable(new Function<BigDecimal>("Funcuplus", (operands) -> {
			BigDecimal sum = new BigDecimal(0);
			for (BigDecimal operand : operands) {
				sum = sum.add(operand);
			}
			return sum.divide(new BigDecimal(operands.size()), parser.getMathContext());
    	}));

		BigDecimal expected = new BigDecimal(12.7434874639);
		String expectedString = "Funcuplus(2 + 3, (max(5, 2) * 6) + (1 + pi), cos 9)";

		Expression<BigDecimal> expression = parser.parse("Funcuplus(2 + 3, max(5, 2) * 6 + (1 + pi), cos(9))");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);

		parser.removeExecutable("Funcuplus");
	}

	@Test
	public void test23() {
		parser.addExecutable(new Function<BigDecimal>("uminusFuncuminus", (operands) -> {
			BigDecimal sum = new BigDecimal(0);
			for (BigDecimal operand : operands) {
				sum = sum.add(operand);
			}
			return sum.divide(new BigDecimal(operands.size()), parser.getMathContext());
    	}));

		BigDecimal expected = new BigDecimal(12.7434874639);
		String expectedString = "uminusFuncuminus(2 + 3, (max(5, 2) * 6) + (1 + pi), cos 9)";

		Expression<BigDecimal> expression = parser.parse("uminusFuncuminus(2 + 3, max(5, 2) * 6 + (1 + pi), cos(9))");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);

		parser.removeExecutable("uminusFuncuminus");
	}

	@Test
	public void test24() {
		parser.addExecutable(new Function<BigDecimal>("uplusFuncuplus", (operands) -> {
    		BigDecimal sum = new BigDecimal(0);
    		for (BigDecimal operand : operands) {
    			sum = sum.add(operand);
    		}
    		return sum.divide(new BigDecimal(operands.size()), parser.getMathContext());
    	}));

		BigDecimal expected = new BigDecimal(12.7434874639);
		String expectedString = "uplusFuncuplus(2 + 3, (max(5, 2) * 6) + (1 + pi), cos 9)";

		Expression<BigDecimal> expression = parser.parse("uplusFuncuplus(2 + 3, max(5, 2) * 6 + (1 + pi), cos(9))");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);

		parser.removeExecutable("uplusFuncuplus");
	}

	@Test
	public void test25() {
		BigDecimal expected = new BigDecimal(25);
		String expectedString = "5 * 5";

		Expression<BigDecimal> expression = parser.parse("5 5");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test26() {
		BigDecimal expected = new BigDecimal(25);
		String expectedString = "5 * 5";

		Expression<BigDecimal> expression = parser.parse("5 (5)");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test27() {
		BigDecimal expected = new BigDecimal(15);
		String expectedString = "5 max(1, 2, 3)";

		Expression<BigDecimal> expression = parser.parse("5 max(1, 2, 3)");

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
		String expectedString = "5 x";

		Expression<BigDecimal> expression = parser.parse("5x");

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

		Expression<BigDecimal> expression = parser.parse("x5");

		BigDecimal actual = expression.evaluate(variables);
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test30() {
		BigDecimal expected = new BigDecimal(30);
		String expectedString = "+5 * 6";

		Expression<BigDecimal> expression = parser.parse("+ 5 6");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test31() {
		BigDecimal expected = new BigDecimal("2.4178516392292583494E+24");
		String expectedString = "2 ^ (3 ^ 4)";

		Expression<BigDecimal> expression = parser.parse("2 ^ 3 ^ 4");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test32() {
		BigDecimal expected = new BigDecimal(2.5);
		String expectedString = "5.5 % 3";

		Expression<BigDecimal> expression = parser.parse("5.5 % 3");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test33() {
		parser.addExecutable(new Operator<BigDecimal>("incr", Operator.OperatorType.SUFFIX, 5, (operands) -> {
			return operands.get(0).add(BigDecimal.ONE);
    	}));

		BigDecimal expected = new BigDecimal(121);
		String expectedString = "(5 !) incr";

		Expression<BigDecimal> expression = parser.parse("5 ! incr");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);

		parser.removeExecutable("incr");
	}

	@Test
	public void test34() {
		BigDecimal expected = new BigDecimal(0.27987335076);
		String expectedString = "sin(cos 5)";

		Expression<BigDecimal> expression = parser.parse("sin cos 5");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test35() {
		BigDecimal expected = new BigDecimal(6.27987335076);
		String expectedString = "sin(cos 5) + 6";

		Expression<BigDecimal> expression = parser.parse("sin cos 5 + 6");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test36() {
		BigDecimal expected = new BigDecimal(-0.95892427466);
		String expectedString = "+sin 5";

		Expression<BigDecimal> expression = parser.parse("+ sin 5");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test37() {
		BigDecimal expected = new BigDecimal(0.95892427466);
		String expectedString = "-sin 5";

		Expression<BigDecimal> expression = parser.parse("- sin 5");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test38() {
		BigDecimal expected = new BigDecimal(-4.79462137332);
		String expectedString = "5 sin 5";

		Expression<BigDecimal> expression = parser.parse("5 sin 5");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test39() {
		BigDecimal expected = new BigDecimal(99000);
		String expectedString = "(10 ^ 5) - (10 ^ 3)";

		Expression<BigDecimal> expression = parser.parse("10 ^ 5 - 10 ^ 3");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test40() {
		BigDecimal expected = new BigDecimal(99999.999);
		String expectedString = "(10 ^ 5) - (10 ^ -3)";

		Expression<BigDecimal> expression = parser.parse("10 ^ 5 - 10 ^ -3");

		BigDecimal actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

}
