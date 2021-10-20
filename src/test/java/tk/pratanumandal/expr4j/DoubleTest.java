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

import org.junit.Assert;
import org.junit.Test;

import tk.pratanumandal.expr4j.exception.Expr4jException;
import tk.pratanumandal.expr4j.impl.DoubleParser;

public class DoubleTest {
	
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
	
	@Test(expected = Expr4jException.class)
	public void test9() {
		Expression<Double> expression = parser.parse("");
		expression.evaluate();
	}
	
	@Test(expected = Expr4jException.class)
	public void test10() {
		Expression<Double> expression = parser.parse("()");
		expression.evaluate();
	}
	
	@Test(expected = Expr4jException.class)
	public void test11() {
		Expression<Double> expression = parser.parse("5 +() 6");
		expression.evaluate();
	}
	
	@Test(expected = Expr4jException.class)
	public void test12() {
		Expression<Double> expression = parser.parse("5() + 6");
		expression.evaluate();
	}
	
	@Test
	public void test13() {
		double expected = 1.2618595071429;
		Expression<Double> expression = parser.parse("log(3, max(ln 10, 4))");
		double actual = expression.evaluate();
		Assert.assertEquals(expected, actual, DELTA);
	}
	/*
	@Test
	public void test14() {
		double expected = 0.5404763089;
		double actual = sy.evaluate("log(max(ln(10), 4), 5 + (2) * 4)");
		Assert.assertEquals(expected, actual, DELTA);
	}
	
	@Test
	public void test15() {
		double expected = 1.8502198591;
		double actual = sy.evaluate("log(5 + (2) * 4, max(ln(10), 4))");
		Assert.assertEquals(expected, actual, DELTA);
	}
	
	@Test
	public void test16() {
		OperatorRepository.addFunction("avg", (operands) -> {
    		double sum = 0;
    		for (Operand operand : operands) {
    			sum += operand.toDouble();
    		}
    		return new Operand(sum / operands.length);
    	});
		
		double expected = 12.7434874639;
		double actual = sy.evaluate("mean(2 + 3, max(5, 2) * 6 + (1 + pi), cos(9))");
		Assert.assertEquals(expected, actual, DELTA);
		
		OperatorRepository.removeFunction("avg");
	}
	
	@Test
	public void test17() {
		double expected = 0.0;
		double actual = sy.evaluate("floor(rand())");
		Assert.assertEquals(expected, actual, DELTA);
	}
	
	@Test
	public void test18() {
		double expected = -3.0;
		double actual = sy.evaluate("2 + (3 - 4) * 5");
		Assert.assertEquals(expected, actual, DELTA);
	}
	
	@Test(expected = Expr4jException.class)
	public void test19() {
		sy.evaluate("(2 + (3)");
	}
	
	@Test(expected = Expr4jException.class)
	public void test20() {
		sy.evaluate("(2 + 3))");
	}
	
	@Test
	public void test21() {
		OperatorRepository.addFunction("ee", (operands) -> {
    		double sum = 0;
    		for (Operand operand : operands) {
    			sum += operand.toDouble();
    		}
    		return new Operand(sum / operands.length);
    	});
		
		double expected = 12.7434874639;
		double actual = sy.evaluate("ee(2 + 3, max(5, 2) * 6 + (1 + pi), cos(9))");
		Assert.assertEquals(expected, actual, DELTA);
		
		OperatorRepository.removeFunction("ee");
	}
	
	@Test
	public void test22() {
		OperatorRepository.addFunction("esume", (operands) -> {
    		double sum = 0;
    		for (Operand operand : operands) {
    			sum += operand.toDouble();
    		}
    		return new Operand(sum / operands.length);
    	});
		
		double expected = 12.7434874639;
		double actual = sy.evaluate("esume(2 + 3, max(5, 2) * 6 + (1 + pi), cos(9))");
		Assert.assertEquals(expected, actual, DELTA);
		
		OperatorRepository.removeFunction("esume");
	}
	
	@Test
	public void test23() {
		OperatorRepository.addFunction("pisumpi", (operands) -> {
    		double sum = 0;
    		for (Operand operand : operands) {
    			sum += operand.toDouble();
    		}
    		return new Operand(sum / operands.length);
    	});
		
		double expected = 12.7434874639;
		double actual = sy.evaluate("pisumpi(2 + 3, max(5, 2) * 6 + (1 + pi), cos(9))");
		Assert.assertEquals(expected, actual, DELTA);
		
		OperatorRepository.removeFunction("pisumpi");
	}
	
	@Test
	public void test24() {
		OperatorRepository.addFunction("esumpi", (operands) -> {
    		double sum = 0;
    		for (Operand operand : operands) {
    			sum += operand.toDouble();
    		}
    		return new Operand(sum / operands.length);
    	});
		
		double expected = 12.7434874639;
		double actual = sy.evaluate("esumpi(2 + 3, max(5, 2) * 6 + (1 + pi), cos(9))");
		Assert.assertEquals(expected, actual, DELTA);
		
		OperatorRepository.removeFunction("esumpi");
	}
	
	@Test
	public void test25() {
		OperatorRepository.addFunction("uminusFunc", (operands) -> {
    		double sum = 0;
    		for (Operand operand : operands) {
    			sum += operand.toDouble();
    		}
    		return new Operand(sum / operands.length);
    	});
		
		double expected = 12.7434874639;
		double actual = sy.evaluate("uminusFunc(2 + 3, max(5, 2) * 6 + (1 + pi), cos(9))");
		Assert.assertEquals(expected, actual, DELTA);
		
		OperatorRepository.removeFunction("uminusFunc");
	}
	
	@Test
	public void test26() {
		OperatorRepository.addFunction("uplusFunc", (operands) -> {
    		double sum = 0;
    		for (Operand operand : operands) {
    			sum += operand.toDouble();
    		}
    		return new Operand(sum / operands.length);
    	});
		
		double expected = 12.7434874639;
		double actual = sy.evaluate("uplusFunc(2 + 3, max(5, 2) * 6 + (1 + pi), cos(9))");
		Assert.assertEquals(expected, actual, DELTA);
		
		OperatorRepository.removeFunction("uplusFunc");
	}
	
	@Test
	public void test27() {
		OperatorRepository.addFunction("Funcuminus", (operands) -> {
    		double sum = 0;
    		for (Operand operand : operands) {
    			sum += operand.toDouble();
    		}
    		return new Operand(sum / operands.length);
    	});
		
		double expected = 12.7434874639;
		double actual = sy.evaluate("Funcuminus(2 + 3, max(5, 2) * 6 + (1 + pi), cos(9))");
		Assert.assertEquals(expected, actual, DELTA);
		
		OperatorRepository.removeFunction("Funcuminus");
	}
	
	@Test
	public void test28() {
		OperatorRepository.addFunction("Funcuplus", (operands) -> {
    		double sum = 0;
    		for (Operand operand : operands) {
    			sum += operand.toDouble();
    		}
    		return new Operand(sum / operands.length);
    	});
		
		double expected = 12.7434874639;
		double actual = sy.evaluate("Funcuplus(2 + 3, max(5, 2) * 6 + (1 + pi), cos(9))");
		Assert.assertEquals(expected, actual, DELTA);
		
		OperatorRepository.removeFunction("Funcuplus");
	}
	
	@Test
	public void test29() {
		OperatorRepository.addFunction("uminusFuncuminus", (operands) -> {
    		double sum = 0;
    		for (Operand operand : operands) {
    			sum += operand.toDouble();
    		}
    		return new Operand(sum / operands.length);
    	});
		
		double expected = 12.7434874639;
		double actual = sy.evaluate("uminusFuncuminus(2 + 3, max(5, 2) * 6 + (1 + pi), cos(9))");
		Assert.assertEquals(expected, actual, DELTA);
		
		OperatorRepository.removeFunction("uminusFuncuminus");
	}
	
	@Test
	public void test30() {
		OperatorRepository.addFunction("uplusFuncuplus", (operands) -> {
    		double sum = 0;
    		for (Operand operand : operands) {
    			sum += operand.toDouble();
    		}
    		return new Operand(sum / operands.length);
    	});
		
		double expected = 12.7434874639;
		double actual = sy.evaluate("uplusFuncuplus(2 + 3, max(5, 2) * 6 + (1 + pi), cos(9))");
		Assert.assertEquals(expected, actual, DELTA);
		
		OperatorRepository.removeFunction("uplusFuncuplus");
	}*/

}
