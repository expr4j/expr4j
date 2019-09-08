package tk.pratanumandal.expr4j;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AppTest extends TestCase {
	
    public AppTest(String testName) {
        super(testName);
    }
    
    public static Test suite() {
    	TestSuite testSuite = new TestSuite();
    	testSuite.addTestSuite(ShuntingYardTreeTest.class);
    	testSuite.addTestSuite(ShuntingYardDualStackTest.class);
        return testSuite;
    }
    
}
