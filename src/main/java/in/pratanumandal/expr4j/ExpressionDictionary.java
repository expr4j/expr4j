package in.pratanumandal.expr4j;

import in.pratanumandal.expr4j.exception.Expr4jException;
import in.pratanumandal.expr4j.token.Function;
import in.pratanumandal.expr4j.token.Operator;
import in.pratanumandal.expr4j.token.OperatorType;

import java.util.*;

/**
 * The <code>ExpressionDictionary&lt;T&gt;</code> class stores all operators, functions and constants of a specified type.
 *
 * @author Pratanu Mandal
 * @since 1.0
 *
 * @param <T> The type of operand for this dictionary
 */
public class ExpressionDictionary<T> {

    /** Map of prefix operators */
    protected final Map<String, Operator<T>> prefixMap;

    /** Map of postfix operators */
    protected final Map<String, Operator<T>> postfixMap;

    /** Map of infix operators */
    protected final Map<String, Operator<T>> infixMap;

    /** Map of functions */
    protected final Map<String, Function<T>> functionMap;

    /** Map of constants */
    protected Map<String, T> constants;

    /**
     * No-Argument Constructor.
     */
    public ExpressionDictionary() {
        this.prefixMap = new TreeMap<>();
        this.postfixMap = new TreeMap<>();
        this.infixMap = new TreeMap<>();
        this.functionMap = new TreeMap<>();
        this.constants = new TreeMap<>();
    }

    /**
     * Add an operator.
     *
     * @param operator The operator
     */
    public void addOperator(Operator<T> operator) {
        if (operator.type == OperatorType.PREFIX) {
            prefixMap.put(operator.label, operator);
        }
        else if (operator.type == OperatorType.POSTFIX) {
            postfixMap.put(operator.label, operator);
        }
        else {
            infixMap.put(operator.label, operator);
        }
    }

    /**
     * Remove all operators for a specified label irrespective of their type.
     *
     * @param label The label of the operators
     */
    public void removeOperator(String label) {
        removeOperator(label, null);
    }

    /**
     * Remove an operator for a specified label and type.
     * If type is null, all operators for the specified label are removed irrespective of their type.
     *
     * @param label The label of the operator(s)
     * @param type The type of the operator
     */
    public void removeOperator(String label, OperatorType type) {
        if (type == null) {
            prefixMap.remove(label);
            postfixMap.remove(label);
            infixMap.remove(label);
        }
        else if (type == OperatorType.PREFIX) {
            prefixMap.remove(label);
        }
        else if (type == OperatorType.POSTFIX) {
            postfixMap.remove(label);
        }
        else {
            infixMap.remove(label);
        }
    }

    /**
     * Get set of all operators available.
     *
     * @return Set of all operators
     */
    public Set<Operator<T>> getOperators() {
        Set<Operator<T>> operatorSet = new HashSet<>();
        operatorSet.addAll(prefixMap.values());
        operatorSet.addAll(postfixMap.values());
        operatorSet.addAll(infixMap.values());
        return Collections.unmodifiableSet(operatorSet);
    }

    /**
     * Check if a prefix operator exists with specified label.
     *
     * @param label The label of the operator
     * @return True if found, false otherwise
     */
    public boolean hasPrefixOperator(String label) {
        return prefixMap.containsKey(label);
    }

    /**
     * Get the prefix operator with specified label.
     *
     * @param label The label of the operator
     * @return The operator if found, null otherwise
     */
    public Operator<T> getPrefixOperator(String label) {
        return prefixMap.get(label);
    }

    /**
     * Check if a postfix operator exists with specified label.
     *
     * @param label The label of the operator
     * @return True if found, false otherwise
     */
    public boolean hasPostfixOperator(String label) {
        return postfixMap.containsKey(label);
    }

    /**
     * Get the postfix operator with specified label.
     *
     * @param label The label of the operator
     * @return The operator if found, null otherwise
     */
    public Operator<T> getPostfixOperator(String label) {
        return postfixMap.get(label);
    }

    /**
     * Check if an infix operator exists with specified label.
     *
     * @param label The label of the operator
     * @return True if found, false otherwise
     */
    public boolean hasInfixOperator(String label) {
        return infixMap.containsKey(label);
    }

    /**
     * Get the infix operator with specified label.
     *
     * @param label The label of the operator
     * @return The operator if found, null otherwise
     */
    public Operator<T> getInfixOperator(String label) {
        return infixMap.get(label);
    }

    /**
     * Add a function.
     *
     * @param function The function
     */
    public void addFunction(Function<T> function) {
        functionMap.put(function.label, function);
    }

    /**
     * Remove a function for a specified label.
     *
     * @param label The label of the function
     */
    public void removeFunction(String label) {
        functionMap.remove(label);
    }

    /**
     * Get set of all functions available.
     *
     * @return Set of all functions
     */
    public Set<Function<T>> getFunctions() {
        Set<Function<T>> functionSet = new HashSet<>();
        functionSet.addAll(functionMap.values());
        return Collections.unmodifiableSet(functionSet);
    }

    /**
     * Check if a function exists with specified label.
     *
     * @param label The label of the function
     * @return True if found, false otherwise
     */
    public boolean hasFunction(String label) {
        return functionMap.containsKey(label);
    }

    /**
     * Get the function with specified label.
     *
     * @param label The label of the function
     * @return The function if found, null otherwise
     */
    public Function<T> getFunction(String label) {
        return functionMap.get(label);
    }

    /**
     * Add a constant to the parser.
     *
     * @param label Label of the constant
     * @param value Value of the constant
     */
    public void addConstant(String label, T value) {
        constants.put(label, value);
    }

    /**
     * Remove constant from the parser for the specified label if present.
     *
     * @param label Label of the constant
     * @return Constant for the specified label if present, else null
     */
    public T removeConstant(String label) {
        return constants.remove(label);
    }

    /**
     * Get constant present in the parser for the specified label.
     *
     * @param label Label of the constant
     * @return Constant for the specified label if present, else null
     */
    public T getConstant(String label) {
        if (!constants.containsKey(label)) {
            throw new Expr4jException("Constant not found: " + label);
        }
        return constants.get(label);
    }

    /**
     * Get list of labels of all executables (operators and functions).
     *
     * @return The list of labels
     */
    Set<String> getExecutables() {
        Set<String> executables = new TreeSet<>();
        executables.addAll(prefixMap.keySet());
        executables.addAll(postfixMap.keySet());
        executables.addAll(infixMap.keySet());
        executables.addAll(functionMap.keySet());
        return executables;
    }

}
