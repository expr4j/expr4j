package tk.pratanumandal.expr4j;

/**
 * The <code>Token</code> class represents any token in expressions.<br><br>
 * A token is the smallest indivisible unit of any expression.<br>
 * Tokens can be operands, operators, functions, variables, or constants.
 * 
 * @author Pratanu Mandal
 *
 */
public abstract class Token {

	/**
	 * The string value of the token.
	 */
	protected String value;

	/**
	 * Parameterized constructor.
	 * 
	 * @param value The value of the token as a String
	 */
	public Token(String value) {
		super();
		this.value = value;
	}
	
	/**
	 * Method to convert token object to string.
	 * 
	 * @return The string value of this token
	 */
	@Override
	public String toString() {
		return value;
	}
	
}
