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

/**
 * The <code>OperatorType</code> enum represents the type of an operator.<br>
 * It can be of four types: PREFIX, POSTFIX, INFIX, and INFIX_RTL.<br><br>
 *
 * PREFIX - must be applied before an operand, right associative.<br>
 * POSTFIX - must be applied after an operand, left associative.<br>
 * INFIX - must be applied in between two operands, left associative.<br>
 * INFIX_RTL - must be applied in between two operands, right associative.
 *
 * @author Pratanu Mandal
 * @since 1.0
 *
 */
public enum OperatorType {
    /** Prefix operators */
    PREFIX,

    /** Postfix operators */
    POSTFIX,

    /** Infix operators (left to right) */
    INFIX,

    /** Infix operators (right to left) */
    INFIX_RTL
}
