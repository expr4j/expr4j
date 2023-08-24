package in.pratanumandal.expr4j.token;

import in.pratanumandal.expr4j.expression.ExpressionParameter;

import java.util.List;

/**
 * The <code>Operation&lt;T&gt;</code> interface represents an operation that can be executed.
 *
 * @author Pratanu Mandal
 * @since 1.0
 *
 * @param <T> The type of operand
 */
public interface Operation<T> {

    /**
     * Execute the operation.
     *
     * @param parameters List of parameters
     * @return Evaluated result
     */
    T execute(List<ExpressionParameter<T>> parameters);

}
