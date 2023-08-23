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

package in.pratanumandal.expr4j.token;

import in.pratanumandal.expr4j.Expression;

import java.util.List;
import java.util.Map;

/**
 * The <code>LazyOperation&lt;T&gt;</code> functional interface represents an operation that can be executed lazily.
 *
 * @author Pratanu Mandal
 * @since 1.0
 *
 * @param <T> The type of operand
 */
@FunctionalInterface
public interface LazyOperation<T> extends Operation<T> {

    /**
     * Execute the operation.
     *
     * @param expression The expression
     * @param nodes List of nodes
     * @param variables Map of variables
     * @return Evaluated result
     */
    T execute(Expression<T> expression, List<Expression.Node> nodes, Map<String, T> variables);

}
