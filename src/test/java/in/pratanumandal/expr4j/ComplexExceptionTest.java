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

import in.pratanumandal.expr4j.exception.Expr4jException;
import in.pratanumandal.expr4j.expression.Expression;
import in.pratanumandal.expr4j.impl.ComplexExpressionBuilder;
import org.apache.commons.numbers.complex.Complex;
import org.junit.Test;

public class ComplexExceptionTest {
	
	protected ComplexExpressionBuilder builder = new ComplexExpressionBuilder();
	
	@Test(expected = Expr4jException.class)
	public void test1() {
		Expression<Complex> expression = builder.build("");
		expression.evaluate();
	}
	
	@Test(expected = Expr4jException.class)
	public void test2() {
		Expression<Complex> expression = builder.build("()");
		expression.evaluate();
	}
	
	@Test(expected = Expr4jException.class)
	public void test3() {
		Expression<Complex> expression = builder.build("5 +() 6");
		expression.evaluate();
	}
	
	@Test(expected = Expr4jException.class)
	public void test4() {
		Expression<Complex> expression = builder.build("5() + 6");
		expression.evaluate();
	}
	
	@Test(expected = Expr4jException.class)
	public void test5() {
		Expression<Complex> expression = builder.build("5 (+) 6");
		expression.evaluate();
	}
	
	@Test(expected = Expr4jException.class)
	public void test6() {
		Expression<Complex> expression = builder.build("5 (+) 6");
		expression.evaluate();
	}
	
	@Test(expected = Expr4jException.class)
	public void test7() {
		Expression<Complex> expression = builder.build("5 6 +");
		expression.evaluate();
	}
	
	@Test(expected = Expr4jException.class)
	public void test8() {
		Expression<Complex> expression = builder.build("5 x +");
		expression.evaluate();
	}
	
	@Test(expected = Expr4jException.class)
	public void test9() {
		Expression<Complex> expression = builder.build("+ 5 x");
		expression.evaluate();
	}
	
	@Test(expected = Expr4jException.class)
	public void test10() {
		Expression<Complex> expression = builder.build("5 + x");
		expression.evaluate();
	}
	
	@Test(expected = Expr4jException.class)
	public void test11() {
		Expression<Complex> expression = builder.build("6 sin 5 *");
		expression.evaluate();
	}
	
	@Test(expected = Expr4jException.class)
	public void test12() {
		Expression<Complex> expression = builder.build("6 + * 5");
		expression.evaluate();
	}
	
	@Test(expected = Expr4jException.class)
	public void test13() {
		Expression<Complex> expression = builder.build("5 max(6 *)");
		expression.evaluate();
	}
	
	@Test(expected = Expr4jException.class)
	public void test14() {
		Expression<Complex> expression = builder.build("5 deg max(5) +");
		expression.evaluate();
	}
	
	@Test(expected = Expr4jException.class)
	public void test15() {
		Expression<Complex> expression = builder.build("(5 *) max(5) +");
		expression.evaluate();
	}
	
	@Test(expected = Expr4jException.class)
	public void test16() {
		Expression<Complex> expression = builder.build("(5,5)");
		expression.evaluate();
	}
	
	@Test(expected = Expr4jException.class)
	public void test17() {
		Expression<Complex> expression = builder.build("(2 + (3)");
		expression.evaluate();
	}
	
	@Test(expected = Expr4jException.class)
	public void test18() {
		Expression<Complex> expression = builder.build("(2 + 3))");
		expression.evaluate();
	}

}
