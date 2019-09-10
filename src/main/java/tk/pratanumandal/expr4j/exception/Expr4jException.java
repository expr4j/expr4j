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

package tk.pratanumandal.expr4j.exception;

/**
 * The <code>Expr4jException</code> class is the superclass of those exceptions that can be thrown during the execution of Expr4j library.<br>
 * It acts as a wrapper for double value operands.
 * 
 * @author Pratanu Mandal
 * @since 0.0.2
 *
 */
public class Expr4jException extends RuntimeException {

	/**
	 * Serial Version UID for object serialization.
	 */
	private static final long serialVersionUID = 6989809082307883828L;

	/**
	 * Constructs a new expr4j exception with null as its detail message.
	 */
	public Expr4jException() {
		super();
	}

	/**
	 * Constructs a new expr4j exception with the specified detail message, cause, suppression enabled or disabled, and writable stack trace enabled or disabled.
	 * 
	 * @param message the detail message
	 * @param cause the cause of the exception
	 * @param enableSuppression whether or not suppression is enabled or disabled
	 * @param writableStackTrace whether or not the stack trace should be writable
	 */
	public Expr4jException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Constructs a new expr4j exception with the specified detail message and cause.
	 * 
	 * @param message the detail message
	 * @param cause the cause of the exception
	 */
	public Expr4jException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new expr4j exception with the specified detail message.
	 * 
	 * @param message the detail message
	 */
	public Expr4jException(String message) {
		super(message);
	}

	/**
	 * Constructs a new expr4j exception with the specified cause and a detail message of (cause==null ? null : cause.toString()) (which typically contains the class and detail message of cause).
	 * 
	 * @param cause
	 */
	public Expr4jException(Throwable cause) {
		super(cause);
	}

}
