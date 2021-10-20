package tk.pratanumandal.expr4j.impl;

import java.util.Collections;
import java.util.List;

import tk.pratanumandal.expr4j.ExpressionParser;
import tk.pratanumandal.expr4j.common.MathExtras;
import tk.pratanumandal.expr4j.token.Executable;
import tk.pratanumandal.expr4j.token.Function;
import tk.pratanumandal.expr4j.token.Operator;
import tk.pratanumandal.expr4j.token.Operator.OperatorType;

public class DoubleParser extends ExpressionParser<Double> {
	
	/**
	 * The maximum number of decimal places supported.<br>
	 * Currently this value is set to 4.
	 */
	public static final int PRECISION = 4;
	
	/**
	 * Use variable number of parameters for function.<br>
	 * Constant value of -1.
	 */
	public static final int VARIABLE_PARAMETERS = -1;
	
	@Override
	protected void initialize(List<Executable<Double>> executables) {
		Collections.addAll(executables,
			// operators
			new Operator<Double>("+", 1, (operands) -> operands.get(0) + operands.get(1)),
			new Operator<Double>("-", 1, (operands) -> operands.get(0) - operands.get(1)),
			
			new Operator<Double>("*", 2, (operands) -> operands.get(0) * operands.get(1)),
			new Operator<Double>("/", 2, (operands) -> operands.get(0) / operands.get(1)),
			
			new Operator<Double>("^", 3, (operands) -> Math.pow(operands.get(0), operands.get(1))),
			
			new Operator<Double>("!", OperatorType.SUFFIX, 5, (operands) -> MathExtras.factorial(operands.get(0))),
			
			new Operator<Double>("sin", OperatorType.PREFIX, 4, (operands) -> Math.sin(operands.get(0))),
			new Operator<Double>("cos", OperatorType.PREFIX, 4, (operands) -> Math.cos(operands.get(0))),
			new Operator<Double>("tan", OperatorType.PREFIX, 4, (operands) -> Math.tan(operands.get(0))),
			
			new Operator<Double>("asin", OperatorType.PREFIX, 4, (operands) -> Math.asin(operands.get(0))),
			new Operator<Double>("acos", OperatorType.PREFIX, 4, (operands) -> Math.acos(operands.get(0))),
			new Operator<Double>("atan", OperatorType.PREFIX, 4, (operands) -> Math.atan(operands.get(0))),
			
			new Operator<Double>("sinh", OperatorType.PREFIX, 4, (operands) -> Math.sinh(operands.get(0))),
			new Operator<Double>("cosh", OperatorType.PREFIX, 4, (operands) -> Math.cosh(operands.get(0))),
			new Operator<Double>("tanh", OperatorType.PREFIX, 4, (operands) -> Math.tanh(operands.get(0))),
			
			new Operator<Double>("asinh", OperatorType.PREFIX, 4, (operands) -> MathExtras.asinh(operands.get(0))),
			new Operator<Double>("acosh", OperatorType.PREFIX, 4, (operands) -> MathExtras.acosh(operands.get(0))),
			new Operator<Double>("atanh", OperatorType.PREFIX, 4, (operands) -> MathExtras.atanh(operands.get(0))),
			
			new Operator<Double>("deg", OperatorType.SUFFIX, 5, (operands) -> Math.toDegrees(operands.get(0))),
			new Operator<Double>("rad", OperatorType.SUFFIX, 5, (operands) -> Math.toRadians(operands.get(0))),
			
			new Operator<Double>("round", OperatorType.PREFIX, 4, (operands) -> (double) Math.round(operands.get(0))),
			new Operator<Double>("floor", OperatorType.PREFIX, 4, (operands) -> Math.floor(operands.get(0))),
			new Operator<Double>("ceil", OperatorType.PREFIX, 4, (operands) -> Math.ceil(operands.get(0))),
			
			new Operator<Double>("ln", OperatorType.PREFIX, 4, (operands) -> Math.log(operands.get(0))),
			new Operator<Double>("log10", OperatorType.PREFIX, 4, (operands) -> Math.log10(operands.get(0))),
			new Function<Double>("log", 2, (operands) -> MathExtras.log(operands.get(1), operands.get(0))),
			
			new Operator<Double>("sqrt", OperatorType.PREFIX, 4, (operands) -> Math.sqrt(operands.get(0))),
			new Operator<Double>("cbrt", OperatorType.PREFIX, 4, (operands) -> Math.cbrt(operands.get(0))),
			
			new Function<Double>("max", (operands) -> operands.isEmpty() ? 0.0 : Collections.max(operands)),
			new Function<Double>("min", (operands) -> Collections.min(operands)),
			
			new Function<Double>("mean", (operands) -> operands.stream().mapToDouble(d -> d).average().orElse(0.0)),
			
			new Function<Double>("rand", 0, (operands) -> Math.random())
		);
	}

	@Override
	protected Double parseNumber(String number) {
		return Double.parseDouble(number);
	}

	@Override
	protected Double unaryPlus(Double operand) {
		return operand;
	}

	@Override
	protected Double unaryMinus(Double operand) {
		return -operand;
	}
	
}
