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
