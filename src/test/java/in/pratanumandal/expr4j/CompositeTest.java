package in.pratanumandal.expr4j;

import in.pratanumandal.expr4j.token.Branch;
import in.pratanumandal.expr4j.token.Operator;
import in.pratanumandal.expr4j.token.OperatorType;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompositeTest {

    public static double DELTA = 0.00000000001;

    class Composite {

        private final Type type;
        private Double number;
        private Boolean condition;

        public Composite(Integer number) {
            this.type = Type.NUMBER;
            this.number = number.doubleValue();
        }

        public Composite(Double number) {
            this.type = Type.NUMBER;
            this.number = number;
        }

        public Composite(Boolean condition) {
            this.type = Type.CONDITION;
            this.condition = condition;
        }

        public Type getType() {
            return type;
        }

        public int intValue() {
            return number.intValue();
        }

        public double doubleValue() {
            return number;
        }

        public Boolean booleanValue() {
            return condition;
        }

        @Override
        public String toString() {
            if (this.getType() == Type.NUMBER) {
                return this.doubleValue() == this.intValue() ?
                        String.valueOf(this.intValue()) :
                        String.valueOf(this.doubleValue());
            }
            else if (this.getType() == Type.CONDITION) {
                return String.valueOf(this.booleanValue());
            }
            return null;
        }
    }

    enum Type {
        NUMBER,
        CONDITION
    }

    protected ExpressionBuilder<Composite> builder;
    protected ExpressionDictionary<Composite> expressionDictionary;
    protected ExpressionConfig<Composite> expressionConfig;

    public CompositeTest() {
        builder = new ExpressionBuilder<>(new ExpressionConfig<Composite>() {
            @Override
            protected Composite stringToOperand(String operand) {
                if (operand.equals("true")) {
                    return new Composite(true);
                }
                else if (operand.equals("false")) {
                    return new Composite(false);
                }
                else {
                    return new Composite(Double.parseDouble(operand));
                }
            }

            @Override
            protected String operandToString(Composite operand) {
                return operand.toString();
            }

            @Override
            protected List<String> getOperandPattern() {
                List<String> list = super.getOperandPattern();
                list.addAll(Arrays.asList("true", "false"));
                return list;
            }
        });

        expressionDictionary = builder.getExpressionDictionary();
        expressionConfig = builder.getExpressionConfig();

        expressionDictionary.addOperator(new Operator<>("+", OperatorType.INFIX, 1,
                (operands) -> new Composite(operands.get(0).doubleValue() + operands.get(1).doubleValue())));

        expressionDictionary.addOperator(new Operator<>("-", OperatorType.INFIX, 1,
                (operands) -> new Composite(operands.get(0).doubleValue() - operands.get(1).doubleValue())));

        expressionDictionary.addOperator(new Operator<>("<", OperatorType.INFIX, 1,
                (operands) -> new Composite(operands.get(0).doubleValue() < operands.get(1).doubleValue())));

        expressionDictionary.addBranch(new Branch<>("if", 3, operand -> operand.booleanValue() ? 1 : 2));

        expressionDictionary.addBranch(new Branch<>("switch", operand -> operand.intValue()));
    }

    private void assertEquals(double expected, double actual) {
        Assert.assertEquals(expected, actual, DELTA);
    }

    @Test
    public void test1() {
        Map<String, Composite> variables = new HashMap<>();
        variables.put("x", new Composite(0.6));

        Composite expected = new Composite(1.0);
        String expectedString = "if(x < 0.5, 0, 1)";

        Expression<Composite> expression = builder.build("if(x < 0.5, 0, 1)");

        Composite actual = expression.evaluate(variables);
        String actualString = expression.toString();

        this.assertEquals(expected.doubleValue(), actual.doubleValue());
        Assert.assertEquals(expectedString, actualString);
    }

    @Test
    public void test2() {
        Map<String, Composite> variables = new HashMap<>();
        variables.put("x", new Composite(3));

        Composite expected = new Composite(8);
        String expectedString = "switch(x, x - 5, x, x + 5)";

        Expression<Composite> expression = builder.build("switch(x, x - 5, x, x + 5)");

        Composite actual = expression.evaluate(variables);
        String actualString = expression.toString();

        this.assertEquals(expected.doubleValue(), actual.doubleValue());
        Assert.assertEquals(expectedString, actualString);
    }

}
