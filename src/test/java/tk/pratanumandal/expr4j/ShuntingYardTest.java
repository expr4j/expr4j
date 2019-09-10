/**
 * Copyright 2019 Pratanu Mandal
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in the
 * Software without restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the
 * following conditions:
 * 
 * 		The above copyright notice and this permission notice shall be included
 * 		in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 */

package tk.pratanumandal.expr4j;

import org.junit.Assert;
import org.junit.Test;

import tk.pratanumandal.expr4j.exception.Expr4jException;
import tk.pratanumandal.expr4j.shuntingyard.ShuntingYard;

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

}
