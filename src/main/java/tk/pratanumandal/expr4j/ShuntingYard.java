package tk.pratanumandal.expr4j;

/**
 * The <code>ShuntingYard</code> abstract class provides a blueprint for ShuntingYard implementations.<br><br>
 * It provides only one method evaluate() which must be overridden by any implementing class.<br>
 * This evaluate() method should act as the single point of access for the implementation.
 * 
 * @author Pratanu Mandal
 *
 */
public abstract class ShuntingYard {
	
	/**
	 * Method to evaluate an expression.<br>
	 * This method should act as the single point of access for the implementation.
	 * 
	 * @param expr Expression string
	 * @return Result of expression evaluation as a double
	 */
	public abstract double evaluate(String expr);

}
