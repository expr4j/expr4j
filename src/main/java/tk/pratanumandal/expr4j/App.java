package tk.pratanumandal.expr4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * The <code>App</code> class acts the main entry point when the library is used as an executable jar.
 * <p>
 * This class also acts as a demonstration of how to use the library:
 * <pre>
 * {@code
 * 	// using dual stack implementation
 * 	String expr = ...;
 * 	ShuntingYard sy = new ShuntingYardDualStack();
 * 	double result = sy.evaluate(expr);
 * 	
 * 	// using tree implementation
 * 	String expr = ...;
 * 	ShuntingYard sy = new ShuntingYardTree();
 * 	double result = sy.evaluate(expr);
 * }
 * </pre>
 * 
 * @author Pratanu Mandal
 *
 */
public class App {
	
	/**
	 * Buffered reader instance to read input from the command line
	 */
	public static final BufferedReader BR = new BufferedReader(new InputStreamReader(System.in));
	
	/**
	 * Main method to allow using the library directly as an executable
	 * 
	 * @param args Command line arguments
	 * @throws IOException expression is thrown for invalid input
	 */
    public static void main(String[] args) throws IOException {
    	
    	System.out.print("Enter expression: ");
    	String expr = BR.readLine();
    	
    	ShuntingYard sy = new ShuntingYardDualStack();
    	double result = sy.evaluate(expr);
    	
    	System.out.println("Result: " + result);
    	
    }
    
}
