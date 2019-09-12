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
import tk.pratanumandal.expr4j.shuntingyard.ShuntingYard;
import tk.pratanumandal.expr4j.token.Operand;

public abstract class ShuntingYardTest {
	
	public static double DELTA = 0.0;
	
	protected ShuntingYard sy;
	
	@Test
	public void test1() {
		double expected = 8.0298136373;
		double actual = sy.evaluate("5+3/cos(sin(-6))^0.25");
		Assert.assertEquals(expected, actual, DELTA);
	}
	
	@Test
	public void test2() {
		double expected = 99.99;
		double actual = sy.evaluate("1e+2 - 1e-2");
		Assert.assertEquals(expected, actual, DELTA);
	}
	
	@Test
	public void test3() {
		double expected = 1.0;
		double actual = sy.evaluate("ceil(rand)");
		Assert.assertEquals(expected, actual, DELTA);
	}
	
	@Test
	public void test4() {
		double expected = -1.0;
		double actual = sy.evaluate("floor(-rand)");
		Assert.assertEquals(expected, actual, DELTA);
	}
	
	@Test
	public void test5() {
		double expected = 30.0;
		double actual = sy.evaluate("deg(asin(sin(rad(30))))");
		Assert.assertEquals(expected, actual, DELTA);
	}
	
	@Test
	public void test6() {
		double expected = 0.3722236412;
		double actual = sy.evaluate("log(2, (ln(2 + 3) * 4))");
		Assert.assertEquals(expected, actual, DELTA);
	}
	
	@Test
	public void test7() {
		double expected = 6.3092975357;
		double actual = sy.evaluate("log(max(5 ^ 4, 4 ^ 5), 3)");
		Assert.assertEquals(expected, actual, DELTA);
	}
	
	@Test
	public void test8() {
		double expected = -5.0;
		double actual = sy.evaluate("(2 + 3) * 4 - (5 ^ 2)");
		Assert.assertEquals(expected, actual, DELTA);
	}
	
	@Test(expected = Expr4jException.class)
	public void test9() {
		sy.evaluate("");
	}
	
	@Test(expected = Expr4jException.class)
	public void test10() {
		sy.evaluate("()");
	}
	
	@Test(expected = Expr4jException.class)
	public void test11() {
		sy.evaluate("5 +() 6");
	}
	
	@Test(expected = Expr4jException.class)
	public void test12() {
		sy.evaluate("5() + 6");
	}
	
	@Test
	public void test13() {
		double expected = 1.2618595071;
		double actual = sy.evaluate("log(max(ln(10), 4), 3)");
		Assert.assertEquals(expected, actual, DELTA);
	}
	
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

}
