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

import in.pratanumandal.expr4j.impl.DoubleExpressionBuilder;
import in.pratanumandal.expr4j.token.Function;
import in.pratanumandal.expr4j.token.Operator;
import in.pratanumandal.expr4j.token.Operator.OperatorType;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class DoubleAssertionTest {
	
	public static double DELTA = 0.00000000001;
	
	protected DoubleExpressionBuilder builder = new DoubleExpressionBuilder();

	private void assertEquals(double expected, double actual) {
		Assert.assertEquals(expected, actual, DELTA);
	}
	
	@Test
	public void test1() {
		double expected = 8.02981363726;
		String expectedString = "5 + 3 / cos(sin(-6)) ^ 0.25";
		
		Expression<Double> expression = builder.build("5+3/cos(sin(-6))^0.25");
		
		double actual = expression.evaluate();
		String actualString = expression.toString();
		
		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}
	
	@Test
	public void test2() {
		double expected = 99.99;
		String expectedString = "100 - 0.01";
		
		Expression<Double> expression = builder.build("1e+2 - 1e-2");
		
		double actual = expression.evaluate();
		String actualString = expression.toString();
		
		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}
	
	@Test
	public void test3() {
		double expected = 1.0;
		String expectedString = "ceil(rand())";
		
		Expression<Double> expression = builder.build("ceil(rand())");
		
		double actual = expression.evaluate();
		String actualString = expression.toString();
		
		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}
	
	@Test
	public void test4() {
		double expected = -1.0;
		String expectedString = "floor(-rand())";
		
		Expression<Double> expression = builder.build("floor(-rand())");
		
		double actual = expression.evaluate();
		String actualString = expression.toString();
		
		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}
	
	@Test
	public void test5() {
		double expected = 30.0;
		String expectedString = "deg(asin(sin(rad(30))))";
		
		Expression<Double> expression = builder.build("deg(asin(sin(rad(30))))");
		
		double actual = expression.evaluate();
		String actualString = expression.toString();
		
		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}
	
	@Test
	public void test6() {
		double expected = 0.37222364116;
		String expectedString = "log(ln(2 + 3) * 4, 2)";
		
		Expression<Double> expression = builder.build("log((ln(2 + 3) * 4), 2)");
		
		double actual = expression.evaluate();
		String actualString = expression.toString();
		
		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}
	
	@Test
	public void test7() {
		double expected = 6.30929753571;
		String expectedString = "log(3, max(5 ^ 4, 4 ^ 5))";
		
		Expression<Double> expression = builder.build("log(3, max(5 ^ 4, 4 ^ 5))");
		
		double actual = expression.evaluate();
		String actualString = expression.toString();
		
		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}
	
	@Test
	public void test8() {
		double expected = -5.0;
		String expectedString = "(2 + 3) * 4 - 5 ^ 2";
		
		Expression<Double> expression = builder.build("(2 + 3) * 4 - (5 ^ 2)");
		
		double actual = expression.evaluate();
		String actualString = expression.toString();
		
		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}
	
	@Test
	public void test9() {
		double expected = 1.26185950714;
		String expectedString = "log(3, max(ln 10, 4))";
		
		Expression<Double> expression = builder.build("log(3, max(ln 10, 4))");
		
		double actual = expression.evaluate();
		String actualString = expression.toString();
		
		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}
	
	@Test
	public void test10() {
		double expected = 0.54047630885;
		String expectedString = "log(5 + 2 * 4, max(ln 10, 4))";
		
		Expression<Double> expression = builder.build("log(5 + (2) * 4, max(ln(10), 4))");
		
		double actual = expression.evaluate();
		String actualString = expression.toString();
		
		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}
	
	@Test
	public void test11() {
		double expected = 1.85021985907;
		String expectedString = "log(max(ln 10, 4), 5 + 2 * 4)";
		
		Expression<Double> expression = builder.build("log(max(ln(10), 4), 5 + (2) * 4)");
		
		double actual = expression.evaluate();
		String actualString = expression.toString();
		
		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}
	
	@Test
	public void test12() {
		double expected = 0.0;
		String expectedString = "floor(rand())";
		
		Expression<Double> expression = builder.build("floor(rand())");
		
		double actual = expression.evaluate();
		String actualString = expression.toString();
		
		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}
	
	@Test
	public void test13() {
		double expected = -3.0;
		String expectedString = "2 + (3 - 4) * 5";
		
		Expression<Double> expression = builder.build("2 + (3 - 4) * 5");
		
		double actual = expression.evaluate();
		String actualString = expression.toString();
		
		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}
	
	@Test
	public void test14() {
		builder.addExecutable(new Function<Double>("avg", (operands) -> {
    		double sum = 0;
    		for (Double operand : operands) {
    			sum += operand;
    		}
    		return sum / operands.size();
    	}));

		double expected = 12.7434874639;
		String expectedString = "avg(2 + 3, max(5, 2) * 6 + 1 + pi, cos 9)";

		Expression<Double> expression = builder.build("avg(2 + 3, max(5, 2) * 6 + (1 + pi), cos(9))");

		double actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);

		builder.removeExecutable("avg");
	}

	@Test
	public void test15() {
		builder.addExecutable(new Function<Double>("ee", (operands) -> {
    		double sum = 0;
    		for (Double operand : operands) {
    			sum += operand;
    		}
    		return sum / operands.size();
    	}));

		double expected = 12.7434874639;
		String expectedString = "ee(2 + 3, max(5, 2) * 6 + 1 + pi, cos 9)";

		Expression<Double> expression = builder.build("ee(2 + 3, max(5, 2) * 6 + (1 + pi), cos(9))");

		double actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);

		builder.removeExecutable("ee");
	}

	@Test
	public void test16() {
		builder.addExecutable(new Function<Double>("esume", (operands) -> {
    		double sum = 0;
    		for (Double operand : operands) {
    			sum += operand;
    		}
    		return sum / operands.size();
    	}));

		double expected = 12.7434874639;
		String expectedString = "esume(2 + 3, max(5, 2) * 6 + 1 + pi, cos 9)";

		Expression<Double> expression = builder.build("esume(2 + 3, max(5, 2) * 6 + (1 + pi), cos(9))");

		double actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);

		builder.removeExecutable("esume");
	}

	@Test
	public void test17() {
		builder.addExecutable(new Function<Double>("pisumpi", (operands) -> {
    		double sum = 0;
    		for (Double operand : operands) {
    			sum += operand;
    		}
    		return sum / operands.size();
    	}));

		double expected = 12.7434874639;
		String expectedString = "pisumpi(2 + 3, max(5, 2) * 6 + 1 + pi, cos 9)";

		Expression<Double> expression = builder.build("pisumpi(2 + 3, max(5, 2) * 6 + (1 + pi), cos(9))");

		double actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);

		builder.removeExecutable("pisumpi");
	}

	@Test
	public void test18() {
		builder.addExecutable(new Function<Double>("esumpi", (operands) -> {
    		double sum = 0;
    		for (Double operand : operands) {
    			sum += operand;
    		}
    		return sum / operands.size();
    	}));

		double expected = 12.7434874639;
		String expectedString = "esumpi(2 + 3, max(5, 2) * 6 + 1 + pi, cos 9)";

		Expression<Double> expression = builder.build("esumpi(2 + 3, max(5, 2) * 6 + (1 + pi), cos(9))");

		double actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);

		builder.removeExecutable("esumpi");
	}

	@Test
	public void test19() {
		builder.addExecutable(new Function<Double>("uminusFunc", (operands) -> {
    		double sum = 0;
    		for (Double operand : operands) {
    			sum += operand;
    		}
    		return sum / operands.size();
    	}));

		double expected = 12.7434874639;
		String expectedString = "uminusFunc(2 + 3, max(5, 2) * 6 + 1 + pi, cos 9)";

		Expression<Double> expression = builder.build("uminusFunc(2 + 3, max(5, 2) * 6 + (1 + pi), cos(9))");

		double actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);

		builder.removeExecutable("uminusFunc");
	}

	@Test
	public void test20() {
		builder.addExecutable(new Function<Double>("uplusFunc", (operands) -> {
    		double sum = 0;
    		for (Double operand : operands) {
    			sum += operand;
    		}
    		return sum / operands.size();
    	}));

		double expected = 12.7434874639;
		String expectedString = "uplusFunc(2 + 3, max(5, 2) * 6 + 1 + pi, cos 9)";

		Expression<Double> expression = builder.build("uplusFunc(2 + 3, max(5, 2) * 6 + (1 + pi), cos(9))");

		double actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);

		builder.removeExecutable("uplusFunc");
	}

	@Test
	public void test21() {
		builder.addExecutable(new Function<Double>("Funcuminus", (operands) -> {
    		double sum = 0;
    		for (Double operand : operands) {
    			sum += operand;
    		}
    		return sum / operands.size();
    	}));

		double expected = 12.7434874639;
		String expectedString = "Funcuminus(2 + 3, max(5, 2) * 6 + 1 + pi, cos 9)";

		Expression<Double> expression = builder.build("Funcuminus(2 + 3, max(5, 2) * 6 + (1 + pi), cos(9))");

		double actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);

		builder.removeExecutable("Funcuminus");
	}

	@Test
	public void test22() {
		builder.addExecutable(new Function<Double>("Funcuplus", (operands) -> {
    		double sum = 0;
    		for (Double operand : operands) {
    			sum += operand;
    		}
    		return sum / operands.size();
    	}));

		double expected = 12.7434874639;
		String expectedString = "Funcuplus(2 + 3, max(5, 2) * 6 + 1 + pi, cos 9)";

		Expression<Double> expression = builder.build("Funcuplus(2 + 3, max(5, 2) * 6 + (1 + pi), cos(9))");

		double actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);

		builder.removeExecutable("Funcuplus");
	}

	@Test
	public void test23() {
		builder.addExecutable(new Function<Double>("uminusFuncuminus", (operands) -> {
    		double sum = 0;
    		for (Double operand : operands) {
    			sum += operand;
    		}
    		return sum / operands.size();
    	}));

		double expected = 12.7434874639;
		String expectedString = "uminusFuncuminus(2 + 3, max(5, 2) * 6 + 1 + pi, cos 9)";

		Expression<Double> expression = builder.build("uminusFuncuminus(2 + 3, max(5, 2) * 6 + (1 + pi), cos(9))");

		double actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);

		builder.removeExecutable("uminusFuncuminus");
	}

	@Test
	public void test24() {
		builder.addExecutable(new Function<Double>("uplusFuncuplus", (operands) -> {
    		double sum = 0;
    		for (Double operand : operands) {
    			sum += operand;
    		}
    		return sum / operands.size();
    	}));

		double expected = 12.7434874639;
		String expectedString = "uplusFuncuplus(2 + 3, max(5, 2) * 6 + 1 + pi, cos 9)";

		Expression<Double> expression = builder.build("uplusFuncuplus(2 + 3, max(5, 2) * 6 + (1 + pi), cos(9))");

		double actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);

		builder.removeExecutable("uplusFuncuplus");
	}

	@Test
	public void test25() {
		double expected = 25;
		String expectedString = "5 * 5";

		Expression<Double> expression = builder.build("5 5");

		double actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test26() {
		double expected = 25;
		String expectedString = "5 * 5";

		Expression<Double> expression = builder.build("5 (5)");

		double actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test27() {
		double expected = 15;
		String expectedString = "5 max(1, 2, 3)";

		Expression<Double> expression = builder.build("5 max(1, 2, 3)");

		double actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test28() {
		Map<String, Double> variables = new HashMap<>();
		variables.put("x", 5.0);

		double expected = 25;
		String expectedString = "5 x";

		Expression<Double> expression = builder.build("5x");

		double actual = expression.evaluate(variables);
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test29() {
		Map<String, Double> variables = new HashMap<>();
		variables.put("x5", 5.0);

		double expected = 5;
		String expectedString = "x5";

		Expression<Double> expression = builder.build("x5");

		double actual = expression.evaluate(variables);
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test30() {
		double expected = 30;
		String expectedString = "+5 * 6";

		Expression<Double> expression = builder.build("+ 5 6");

		double actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test31() {
		double expected = 2417851639229258349412352.0;
		String expectedString = "2 ^ (3 ^ 4)";

		Expression<Double> expression = builder.build("2 ^ 3 ^ 4");

		double actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test32() {
		double expected = 2.5;
		String expectedString = "5.5 % 3";

		Expression<Double> expression = builder.build("5.5 % 3");

		double actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test33() {
		builder.addExecutable(new Operator<Double>("incr", OperatorType.SUFFIX, 5, (operands) -> {
			return operands.get(0) + 1;
    	}));

		double expected = 121;
		String expectedString = "(5 !) incr";

		Expression<Double> expression = builder.build("5 ! incr");

		double actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);

		builder.removeExecutable("incr");
	}
	
	@Test
	public void test34() {
		double expected = 0.27987335076;
		String expectedString = "sin(cos 5)";
		
		Expression<Double> expression = builder.build("sin cos 5");
		
		double actual = expression.evaluate();
		String actualString = expression.toString();
		
		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}
	
	@Test
	public void test35() {
		double expected = 6.27987335076;
		String expectedString = "sin(cos 5) + 6";
		
		Expression<Double> expression = builder.build("sin cos 5 + 6");
		
		double actual = expression.evaluate();
		String actualString = expression.toString();
		
		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}
	
	@Test
	public void test36() {
		double expected = -0.95892427466;
		String expectedString = "+sin 5";
		
		Expression<Double> expression = builder.build("+ sin 5");
		
		double actual = expression.evaluate();
		String actualString = expression.toString();
		
		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}
	
	@Test
	public void test37() {
		double expected = 0.95892427466;
		String expectedString = "-sin 5";
		
		Expression<Double> expression = builder.build("- sin 5");
		
		double actual = expression.evaluate();
		String actualString = expression.toString();
		
		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}
	
	@Test
	public void test38() {
		double expected = -4.79462137332;
		String expectedString = "5 sin 5";
		
		Expression<Double> expression = builder.build("5 sin 5");
		
		double actual = expression.evaluate();
		String actualString = expression.toString();
		
		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}
	
	@Test
	public void test39() {
		double expected = 99000;
		String expectedString = "10 ^ 5 - 10 ^ 3";
		
		Expression<Double> expression = builder.build("10 ^ 5 - 10 ^ 3");
		
		double actual = expression.evaluate();
		String actualString = expression.toString();
		
		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}
	
	@Test
	public void test40() {
		double expected = 99999.999;
		String expectedString = "10 ^ 5 - 10 ^ -3";

		Expression<Double> expression = builder.build("10 ^ 5 - 10 ^ -3");

		double actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test41() {
		double expected = -30;
		String expectedString = "-5 * 6";

		Expression<Double> expression = builder.build("-5 6");

		double actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test42() {
		double expected = -30;
		String expectedString = "-5 * 6";

		Expression<Double> expression = builder.build("-5 * 6");

		double actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test43() {
		double expected = -30;
		String expectedString = "-(5 * 6)";

		Expression<Double> expression = builder.build("-(5 6)");

		double actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test44() {
		double expected = -30;
		String expectedString = "-(5 * 6)";

		Expression<Double> expression = builder.build("-(5 * 6)");

		double actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test45() {
		double expected = 30;
		String expectedString = "abs(-(5 * 6))";

		Expression<Double> expression = builder.build("abs -(5 * 6)");

		double actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test46() {
		double expected = 15;
		String expectedString = "1 + 2 + 3 + 4 + 5";

		Expression<Double> expression = builder.build("1 + 2 + 3 + 4 + 5");

		double actual = expression.evaluate();
		String actualString = expression.toString();

		this.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

}
