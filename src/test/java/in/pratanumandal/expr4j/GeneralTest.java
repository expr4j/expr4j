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
import in.pratanumandal.expr4j.expression.ExpressionBuilder;
import in.pratanumandal.expr4j.expression.ExpressionConfig;
import in.pratanumandal.expr4j.expression.ExpressionDictionary;
import in.pratanumandal.expr4j.expression.ExpressionTokenizer;
import in.pratanumandal.expr4j.token.Function;
import in.pratanumandal.expr4j.token.Operand;
import in.pratanumandal.expr4j.token.Operator;
import in.pratanumandal.expr4j.token.OperatorType;
import in.pratanumandal.expr4j.token.Separator;
import in.pratanumandal.expr4j.token.Variable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class GeneralTest {

	protected ExpressionBuilder<String> builder;
	protected ExpressionDictionary<String> expressionDictionary;
	protected ExpressionConfig<String> expressionConfig;

	public GeneralTest() {
		builder = new ExpressionBuilder<>(new ExpressionConfig<String>() {
			@Override
			protected String stringToOperand(String operand) {
				return operand.substring(1, operand.length() - 1);
			}

			@Override
			protected String operandToString(String operand) {
				return "'" + operand + "'";
			}

			@Override
			protected List<String> getOperandPattern() {
				return Arrays.asList("'.*?'");
			}
		});

		expressionDictionary = builder.getExpressionDictionary();
		expressionConfig = builder.getExpressionConfig();

		expressionDictionary.addOperator(new Operator<>("+", OperatorType.INFIX, 1, (parameters) -> parameters.get(0).value() + parameters.get(1).value()));
		expressionDictionary.addFunction(new Function<>("add", (parameters) -> parameters.get(0).value() + parameters.get(1).value()));
	}
	
	@Test
	public void test1() {
		String expected = "HelloWorld";
		String expectedString = "'Hello' + 'World'";
		
		Expression<String> expression = builder.build("'Hello' + 'World'");
		
		String actual = expression.evaluate();
		String actualString = expression.toString();
		
		Assertions.assertEquals(expected, actual);
		Assertions.assertEquals(expectedString, actualString);
	}

	@Test
	public void test2() {
		String expected = "HelloWorld";
		String expectedString = "add('Hello', 'World')";

		Expression<String> expression = builder.build("add('Hello', 'World')");

		String actual = expression.evaluate();
		String actualString = expression.toString();

		Assertions.assertEquals(expected, actual);
		Assertions.assertEquals(expectedString, actualString);
	}

	@Test
	public void test3() {
		String[] expected = {"+"};
		String[] actual = expressionDictionary.getOperators()
				.stream()
				.map(e -> e.label)
				.toArray(String[]::new);
		Assertions.assertArrayEquals(expected, actual);
	}

	@Test
	public void test4() {
		String[] expected = {"add"};
		String[] actual = expressionDictionary.getFunctions()
				.stream()
				.map(e -> e.label)
				.toArray(String[]::new);
		Assertions.assertArrayEquals(expected, actual);
	}

	@Test
	public void test5() {
		String[] expected = {"Operator{label='+', type=INFIX, precedence=1}"};
		String[] actual = expressionDictionary.getOperators()
				.stream()
				.map(e -> e.toString())
				.toArray(String[]::new);
		Assertions.assertArrayEquals(expected, actual);
	}

	@Test
	public void test6() {
		String[] expected = {"Function{label='add', parameters=-1}"};
		String[] actual = expressionDictionary.getFunctions()
				.stream()
				.map(e -> e.toString())
				.toArray(String[]::new);
		Assertions.assertArrayEquals(expected, actual);
	}

	@Test
	public void test7() {
		String expected = "Separator{name='COMMA', label=','}";
		String actual = Separator.COMMA.toString();
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void test8() {
		String expected = "Hello";
		String actual = new Operand<>("Hello").toString();
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void test9() {
		String expected = "Hello";
		String actual = new Variable("Hello").toString();
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void test10() {
		String[] expected = {"Hello", "Operator{label='+', type=INFIX, precedence=1}", "World"};

		ExpressionTokenizer<String> tokenizer = new ExpressionTokenizer<>(expressionDictionary, expressionConfig);

		String[] actual = tokenizer.tokenize("'Hello' + 'World'")
				.stream()
				.map(e -> e.toString())
				.toArray(String[]::new);

		Assertions.assertArrayEquals(expected, actual);
	}

}
