package in.pratanumandal.expr4j.token;

import java.util.List;

/**
 * The <code>Operation&lt;T&gt;</code> functional interface represents an operation that can be executed.
 *
 * @author Pratanu Mandal
 * @since 1.0
 *
 * @param <T> The type of operand
 */
@FunctionalInterface
public interface Operation<T> {

    /**
     * Execute the operation.
     *
     * @param operands List of operands
     * @return Evaluated result
     */
    T execute(List<T> operands);

}
