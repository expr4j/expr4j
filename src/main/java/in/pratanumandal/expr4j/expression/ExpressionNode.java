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

import in.pratanumandal.expr4j.token.Function;
import in.pratanumandal.expr4j.token.Operator;
import in.pratanumandal.expr4j.token.Token;

import java.util.ArrayList;
import java.util.List;

/**
 * The <code>ExpressionNode</code> class represents a node of the expression tree.<br><br>
 *
 * @author Pratanu Mandal
 * @since 1.0
 *
 */
public class ExpressionNode {

    /**
     * Children of this node.
     */
    public final List<ExpressionNode> children;

    /**
     * Token contained in this node.<br>
     * A token can be an operand, operator, function, variable, or constant.
     */
    public final Token token;

    /**
     * Parameterized constructor.
     *
     * @param token The token in this node
     */
    public ExpressionNode(Token token) {
        this.token = token;
        if (token instanceof Function || token instanceof Operator) {
            this.children = new ArrayList<>();
        }
        else {
            this.children = null;
        }
    }

}
