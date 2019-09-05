package com.pramanda.expr;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AppTest extends TestCase {
	
    public AppTest(String testName) {
        super(testName);
    }
    
    public static Test suite() {
        return new TestSuite(AppTest.class);
    }
    
    public void testApp() {
    	ShuntingYard sy = new ShuntingYard();
    	double expected = 8.029813637262905;
    	double actual = sy.evaluate("5+3/cossin-6^0.25");
        assertEquals(expected, actual);
    }
}
