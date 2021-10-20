/**
 * Copyright 2019 Pratanu Mandal
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

package tk.pratanumandal.expr4j;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import tk.pratanumandal.expr4j.impl.DoubleParser;
import tk.pratanumandal.expr4j.token.Function;

public class DoublePositiveTest {
	
	public static double DELTA = 0.00000000001;
	
	protected DoubleParser parser = new DoubleParser();
	
	@Test
	public void test1() {
		double expected = 8.02981363726;
		Expression<Double> expression = parser.parse("5+3/cos(sin(-6))^0.25");
		double actual = expression.evaluate();
		Assert.assertEquals(expected, actual, DELTA);
	}
	
	@Test
	public void test2() {
		double expected = 99.99;
		Expression<Double> expression = parser.parse("1e+2 - 1e-2");
		double actual = expression.evaluate();
		Assert.assertEquals(expected, actual, DELTA);
	}
	
	@Test
	public void test3() {
		double expected = 1.0;
		Expression<Double> expression = parser.parse("ceil(rand())");
		double actual = expression.evaluate();
		Assert.assertEquals(expected, actual, DELTA);
	}
	
	@Test
	public void test4() {
		double expected = -1.0;
		Expression<Double> expression = parser.parse("floor(-rand())");
		double actual = expression.evaluate();
		Assert.assertEquals(expected, actual, DELTA);
	}
	
	@Test
	public void test5() {
		double expected = 30.0;
		Expression<Double> expression = parser.parse("asin(sin(30 rad)) deg");
		double actual = expression.evaluate();
		Assert.assertEquals(expected, actual, DELTA);
	}
	
	@Test
	public void test6() {
		double expected = 0.37222364116;
		Expression<Double> expression = parser.parse("log((ln(2 + 3) * 4), 2)");
		double actual = expression.evaluate();
		Assert.assertEquals(expected, actual, DELTA);
	}
	
	@Test
	public void test7() {
		double expected = 6.30929753571;
		Expression<Double> expression = parser.parse("log(3, max(5 ^ 4, 4 ^ 5))");
		double actual = expression.evaluate();
		Assert.assertEquals(expected, actual, DELTA);
	}
	
	@Test
	public void test8() {
		double expected = -5.0;
		Expression<Double> expression = parser.parse("(2 + 3) * 4 - (5 ^ 2)");
		double actual = expression.evaluate();
		Assert.assertEquals(expected, actual, DELTA);
	}
	
	@Test
	public void test9() {
		double expected = 1.26185950714;
		Expression<Double> expression = parser.parse("log(3, max(ln 10, 4))");
		double actual = expression.evaluate();
		Assert.assertEquals(expected, actual, DELTA);
	}
	
	@Test
	public void test10() {
		double expected = 0.54047630885;
		Expression<Double> expression = parser.parse("log(5 + (2) * 4, max(ln(10), 4))");
		double actual = expression.evaluate();
		Assert.assertEquals(expected, actual, DELTA);
	}
	
	@Test
	public void test11() {
		double expected = 1.85021985907;
		Expression<Double> expression = parser.parse("log(max(ln(10), 4), 5 + (2) * 4)");
		double actual = expression.evaluate();
		Assert.assertEquals(expected, actual, DELTA);
	}
	
	@Test
	public void test12() {
		double expected = 0.0;
		Expression<Double> expression = parser.parse("floor(rand())");
		double actual = expression.evaluate();
		Assert.assertEquals(expected, actual, DELTA);
	}
	
	@Test
	public void test13() {
		double expected = -3.0;
		Expression<Double> expression = parser.parse("2 + (3 - 4) * 5");
		double actual = expression.evaluate();
		Assert.assertEquals(expected, actual, DELTA);
	}
	
	@Test
	public void test14() {
		parser.addExecutable(new Function<Double>("avg", (operands) -> {
    		double sum = 0;
    		for (Double operand : operands) {
    			sum += operand;
    		}
    		return sum / operands.size();
    	}));
		
		double expected = 12.7434874639;
		Expression<Double> expression = parser.parse("avg(2 + 3, max(5, 2) * 6 + (1 + pi), cos(9))");
		double actual = expression.evaluate();
		Assert.assertEquals(expected, actual, DELTA);
		
		parser.removeExecutable("avg");
	}
	
	@Test
	public void test15() {
		parser.addExecutable(new Function<Double>("ee", (operands) -> {
    		double sum = 0;
    		for (Double operand : operands) {
    			sum += operand;
    		}
    		return sum / operands.size();
    	}));
		
		double expected = 12.7434874639;
		Expression<Double> expression = parser.parse("ee(2 + 3, max(5, 2) * 6 + (1 + pi), cos(9))");
		double actual = expression.evaluate();
		Assert.assertEquals(expected, actual, DELTA);
		
		parser.removeExecutable("ee");
	}
	
	@Test
	public void test16() {
		parser.addExecutable(new Function<Double>("esume", (operands) -> {
    		double sum = 0;
    		for (Double operand : operands) {
    			sum += operand;
    		}
    		return sum / operands.size();
    	}));
		
		double expected = 12.7434874639;
		Expression<Double> expression = parser.parse("esume(2 + 3, max(5, 2) * 6 + (1 + pi), cos(9))");
		double actual = expression.evaluate();
		Assert.assertEquals(expected, actual, DELTA);
		
		parser.removeExecutable("esume");
	}
	
	@Test
	public void test17() {
		parser.addExecutable(new Function<Double>("pisumpi", (operands) -> {
    		double sum = 0;
    		for (Double operand : operands) {
    			sum += operand;
    		}
    		return sum / operands.size();
    	}));
		
		double expected = 12.7434874639;
		Expression<Double> expression = parser.parse("pisumpi(2 + 3, max(5, 2) * 6 + (1 + pi), cos(9))");
		double actual = expression.evaluate();
		Assert.assertEquals(expected, actual, DELTA);
		
		parser.removeExecutable("pisumpi");
	}
	
	@Test
	public void test18() {
		parser.addExecutable(new Function<Double>("esumpi", (operands) -> {
    		double sum = 0;
    		for (Double operand : operands) {
    			sum += operand;
    		}
    		return sum / operands.size();
    	}));
		
		double expected = 12.7434874639;
		Expression<Double> expression = parser.parse("esumpi(2 + 3, max(5, 2) * 6 + (1 + pi), cos(9))");
		double actual = expression.evaluate();
		Assert.assertEquals(expected, actual, DELTA);
		
		parser.removeExecutable("esumpi");
	}
	
	@Test
	public void test19() {
		parser.addExecutable(new Function<Double>("uminusFunc", (operands) -> {
    		double sum = 0;
    		for (Double operand : operands) {
    			sum += operand;
    		}
    		return sum / operands.size();
    	}));
		
		double expected = 12.7434874639;
		Expression<Double> expression = parser.parse("uminusFunc(2 + 3, max(5, 2) * 6 + (1 + pi), cos(9))");
		double actual = expression.evaluate();
		Assert.assertEquals(expected, actual, DELTA);
		
		parser.removeExecutable("uminusFunc");
	}
	
	@Test
	public void test20() {
		parser.addExecutable(new Function<Double>("uplusFunc", (operands) -> {
    		double sum = 0;
    		for (Double operand : operands) {
    			sum += operand;
    		}
    		return sum / operands.size();
    	}));
		
		double expected = 12.7434874639;
		Expression<Double> expression = parser.parse("uplusFunc(2 + 3, max(5, 2) * 6 + (1 + pi), cos(9))");
		double actual = expression.evaluate();
		Assert.assertEquals(expected, actual, DELTA);
		
		parser.removeExecutable("uplusFunc");
	}
	
	@Test
	public void test21() {
		parser.addExecutable(new Function<Double>("Funcuminus", (operands) -> {
    		double sum = 0;
    		for (Double operand : operands) {
    			sum += operand;
    		}
    		return sum / operands.size();
    	}));
		
		double expected = 12.7434874639;
		Expression<Double> expression = parser.parse("Funcuminus(2 + 3, max(5, 2) * 6 + (1 + pi), cos(9))");
		double actual = expression.evaluate();
		Assert.assertEquals(expected, actual, DELTA);
		
		parser.removeExecutable("Funcuminus");
	}
	
	@Test
	public void test28() {
		parser.addExecutable(new Function<Double>("Funcuplus", (operands) -> {
    		double sum = 0;
    		for (Double operand : operands) {
    			sum += operand;
    		}
    		return sum / operands.size();
    	}));
		
		double expected = 12.7434874639;
		Expression<Double> expression = parser.parse("Funcuplus(2 + 3, max(5, 2) * 6 + (1 + pi), cos(9))");
		double actual = expression.evaluate();
		Assert.assertEquals(expected, actual, DELTA);
		
		parser.removeExecutable("Funcuplus");
	}
	
	@Test
	public void test29() {
		parser.addExecutable(new Function<Double>("uminusFuncuminus", (operands) -> {
    		double sum = 0;
    		for (Double operand : operands) {
    			sum += operand;
    		}
    		return sum / operands.size();
    	}));
		
		double expected = 12.7434874639;
		Expression<Double> expression = parser.parse("uminusFuncuminus(2 + 3, max(5, 2) * 6 + (1 + pi), cos(9))");
		double actual = expression.evaluate();
		Assert.assertEquals(expected, actual, DELTA);
		
		parser.removeExecutable("uminusFuncuminus");
	}
	
	@Test
	public void test30() {
		parser.addExecutable(new Function<Double>("uplusFuncuplus", (operands) -> {
    		double sum = 0;
    		for (Double operand : operands) {
    			sum += operand;
    		}
    		return sum / operands.size();
    	}));
		
		double expected = 12.7434874639;
		Expression<Double> expression = parser.parse("uplusFuncuplus(2 + 3, max(5, 2) * 6 + (1 + pi), cos(9))");
		double actual = expression.evaluate();
		Assert.assertEquals(expected, actual, DELTA);
		
		parser.removeExecutable("uplusFuncuplus");
	}
	
	@Test
	public void test31() {
		double expected = 25;
		Expression<Double> expression = parser.parse("5 5");
		double actual = expression.evaluate();
		Assert.assertEquals(expected, actual, DELTA);
	}
	
	@Test
	public void test32() {
		double expected = 25;
		Expression<Double> expression = parser.parse("5 (5)");
		double actual = expression.evaluate();
		Assert.assertEquals(expected, actual, DELTA);
	}
	
	@Test
	public void test33() {
		double expected = 15;
		Expression<Double> expression = parser.parse("5 max(1, 2, 3)");
		double actual = expression.evaluate();
		Assert.assertEquals(expected, actual, DELTA);
	}
	
	@Test
	public void test34() {
		Map<String, Double> variables = new HashMap<>();
		variables.put("z", 5.0);
		
		double expected = 25;
		Expression<Double> expression = parser.parse("5z");
		double actual = expression.evaluate(variables);
		Assert.assertEquals(expected, actual, DELTA);
	}
	
	@Test
	public void test35() {
		double expected = 30;
		Expression<Double> expression = parser.parse("+ 5 6");
		double actual = expression.evaluate();
		Assert.assertEquals(expected, actual, DELTA);
	}

}
