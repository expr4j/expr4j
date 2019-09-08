package tk.pratanumandal.expr4j;

/**
 * The <code>Operand</code> class represents the operands in the expression.<br>
 * It acts as a wrapper for double value operands.
 * 
 * @author Pratanu Mandal
 *
 */
public class Operand extends Token {

	/**
	 * Parameterized constructor.
	 * 
	 * @param value The double value operand as a String
	 */
	public Operand(String value) {
		super(value);
	}

	/**
	 * Convert operand String to double.
	 * 
	 * @return the double value of the operand
	 */
	public double toDouble() {
		return Double.parseDouble(value);
	}
	
	/**
	 * Utility method to determine if a string is an operand.
	 * 
	 * @param op Operand value as a string
	 * @return true if op is an operand, otherwise false
	 */
	public static boolean isOperand(String op) {
		try {
			Double.parseDouble(op);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
}
