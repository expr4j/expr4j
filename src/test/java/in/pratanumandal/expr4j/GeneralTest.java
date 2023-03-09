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

import in.pratanumandal.expr4j.token.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class GeneralTest {

	protected ExpressionBuilder<String> builder;
	protected ExpressionDictionary<String> expressionDictionary;

	public GeneralTest() {
		builder = new ExpressionBuilder<String>() {
			@Override
			protected String stringToOperand(String operand) {
				return operand;
			}

			@Override
			protected String operandToString(String operand) {
				return operand;
			}

			@Override
			protected List<String> getOperandPattern() {
				return Arrays.asList("[A-Za-z]+");
			}
		};

		expressionDictionary = builder.getExpressionDictionary();

		expressionDictionary.addOperator(new Operator<>("+", OperatorType.INFIX, 1, (operands) -> operands.get(0) + operands.get(1)));
		expressionDictionary.addFunction(new Function<>("add", (operands) -> operands.get(0) + operands.get(1)));
	}
	
	@Test
	public void test1() {
		String expected = "HelloWorld";
		String expectedString = "Hello + World";
		
		Expression<String> expression = builder.build("Hello + World");
		
		String actual = expression.evaluate();
		String actualString = expression.toString();
		
		Assert.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test2() {
		String expected = "HelloWorld";
		String expectedString = "add(Hello, World)";

		Expression<String> expression = builder.build("add(Hello, World)");

		String actual = expression.evaluate();
		String actualString = expression.toString();

		Assert.assertEquals(expected, actual);
		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void test3() {
		String[] expected = {"+"};
		String[] actual = expressionDictionary.getOperators().stream().map(e -> e.label).toArray(String[]::new);
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void test4() {
		String[] expected = {"add"};
		String[] actual = expressionDictionary.getFunctions().stream().map(e -> e.label).toArray(String[]::new);
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void test5() {
		String[] expected = {"Operator{label='+', type=INFIX, precedence=1}"};
		String[] actual = expressionDictionary.getOperators().stream().map(e -> e.toString()).toArray(String[]::new);
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void test6() {
		String[] expected = {"Function{label='add', parameters=-1}"};
		String[] actual = expressionDictionary.getFunctions().stream().map(e -> e.toString()).toArray(String[]::new);
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void test7() {
		String expected = "Separator{name='COMMA', label=','}";
		String actual = Separator.COMMA.toString();
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void test8() {
		String expected = "Hello";
		String actual = new Operand<>("Hello").toString();
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void test9() {
		String expected = "Hello";
		String actual = new Variable("Hello").toString();
		Assert.assertEquals(expected, actual);
	}

}
