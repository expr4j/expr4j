/**
 * Copyright 2021 Pratanu Mandal
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

package tk.pratanumandal.expr4j.exception;

/**
 * The <code>Expr4jException</code> class represents exceptions that can be thrown during the execution of Expr4j library.
 * 
 * @author Pratanu Mandal
 * @since 1.0
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
	 * @param cause the cause of the exception
	 */
	public Expr4jException(Throwable cause) {
		super(cause);
	}

}
