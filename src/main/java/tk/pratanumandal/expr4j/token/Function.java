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
 * The <code>Function</code> interface represents functions in the expression.<br><br>
 * 
 * It is a functional interface and provides only one method evaluate() which shall be invoked to evaluate the function.<br>
 * The evaluate() method must be overridden by any implementing class.
 * 
 * @author Pratanu Mandal
 * @since 0.0.2
 *
 */
public interface Function {
	
	/**
	 * Method invoked to evaluate the function.
	 * 
	 * @param operands Variable number of operands required by this operator
	 * @return The evaluated result as another operand
	 */
	public Operand evaluate(Operand ... operands); 

}
