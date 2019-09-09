/**
 * Copyright 2019 Pratanu Mandal
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in the
 * Software without restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the
 * following conditions:
 * 
 * 		The above copyright notice and this permission notice shall be included
 * 		in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 */

package tk.pratanumandal.expr4j.token;

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
	 * Parameterized constructor.
	 * 
	 * @param value The double value operand
	 */
	public Operand(double value) {
		super(String.valueOf(value));
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
