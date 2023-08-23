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

import java.util.ArrayList;
import java.util.List;

/**
 * The <code>ExpressionConfig&lt;T&gt;</code> class defines configurations for the expression.
 *
 * @author Pratanu Mandal
 * @since 1.0
 *
 * @param <T> The type of operand
 */
public abstract class ExpressionConfig<T> {

    /**
     * No-Argument Constructor.
     */
    public ExpressionConfig() {
    }

    /**
     * Method to define procedure to obtain operand from string representation.
     *
     * @param operand String representation of operand
     * @return Operand
     */
    protected abstract T stringToOperand(String operand);

    /**
     * Method to define procedure to obtain string representation of operand.
     *
     * @param operand Operand
     * @return String representation of operand
     */
    protected abstract String operandToString(T operand);

    /**
     * Method to define the patterns to identify operands.<br>
     * Override this method if the patterns to identify operands need to be customized.
     *
     * @return List of patterns to identify operands
     */
    protected List<String> getOperandPattern() {
        List<String> list = new ArrayList<>();
        list.add("(-?\\d+)(\\.\\d+)?(e-|e\\+|e|\\d+)\\d+");
        list.add("\\d*\\.?\\d+");
        return list;
    }

}
