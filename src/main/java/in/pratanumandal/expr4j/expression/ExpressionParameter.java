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

package in.pratanumandal.expr4j.expression;

import java.util.Map;

/**
 * The <code>ExpressionParameter&lt;T&gt;</code> class represents a parameter of an operation.<br><br>
 *
 * @author Pratanu Mandal
 * @since 1.0
 *
 */
public class ExpressionParameter<T> {

    private Expression<T> expression;
    private ExpressionNode node;
    private Map<String, T> variables;
    private T result;

    public ExpressionParameter(Expression<T> expression, ExpressionNode node, Map<String, T> variables) {
        this.expression = expression;
        this.node = node;
        this.variables = variables;
    }

    public T value() {
        if (this.result == null) {
            this.result = this.expression.evaluate(this.node, this.variables).value;
        }
        return this.result;
    }

}
