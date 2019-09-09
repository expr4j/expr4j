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

package tk.pratanumandal.expr4j;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import tk.pratanumandal.expr4j.shuntingyard.ShuntingYard;
import tk.pratanumandal.expr4j.shuntingyard.ShuntingYardDualStack;

public class ShuntingYardDualStackTest extends TestCase {
	
	private ShuntingYard sy;
	
    public ShuntingYardDualStackTest(String testName) {
        super(testName);
        sy = new ShuntingYardDualStack();
    }
    
    public static Test suite() {
        return new TestSuite(ShuntingYardDualStackTest.class);
    }
    
    public void test1() {
    	double expected = 8.0298136373;
    	double actual = sy.evaluate("5+3/cos(sin(-6))^0.25");
        assertEquals(expected, actual);
    }
    
    public void test2() {
    	double expected = 99.99;
    	double actual = sy.evaluate("1e+2 - 1e-2");
        assertEquals(expected, actual);
    }
    
    public void test3() {
    	double expected = 1.0;
    	double actual = sy.evaluate("ceil(rand)");
        assertEquals(expected, actual);
    }
    
    public void test4() {
    	double expected = -1.0;
    	double actual = sy.evaluate("floor(-rand)");
        assertEquals(expected, actual);
    }
    
    public void test5() {
    	double expected = 30.0;
    	double actual = sy.evaluate("deg(asin(sin(rad(30))))");
        assertEquals(expected, actual);
    }
    
    public void test6() {
    	double expected = 0.3722236412;
    	double actual = sy.evaluate("log(2, (ln(2 + 3) * 4))");
        assertEquals(expected, actual);
    }
    
}
