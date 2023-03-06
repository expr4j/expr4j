/**
 * Copyright 2023 Pratanu Mandal
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

package in.pratanumandal.expr4j;

import in.pratanumandal.expr4j.exception.Expr4jException;
import in.pratanumandal.expr4j.token.*;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * The <code>ExpressionTokenizer&lt;T&gt;</code> class tokenizes expressions independent of the type of operand.
 *
 * @author Pratanu Mandal
 * @since 1.0
 *
 * @param <T> The type of operand for this parser
 */
public abstract class ExpressionTokenizer<T> {

    /**
     * Tokenize an expression.
     *
     * @param expr The expression
     * @param executables Map of executables
     * @return The list of tokens
     */
    public List<Token> tokenize(String expr, Map<String, Executable<T>> executables) {
        // do not allow blank expressions
        if (StringUtils.isBlank(expr)) {
            throw new Expr4jException("Invalid expression");
        }

        // list of tokens
        List<Token> tokenList = new ArrayList<>();

        // separate executables into functions and operators
        Map<String, Function<T>> functions = new HashMap<>();
        Map<String, Operator<T>> operators = new HashMap<>();

        Iterator<Map.Entry<String, Executable<T>>> iterator = executables.entrySet().iterator();
        while (iterator.hasNext()) {
            Executable<T> executable = iterator.next().getValue();

            if (executable instanceof Function) {
                Function<T> function = (Function<T>) executable;
                functions.put(function.label, function);
            }
            else if (executable instanceof Operator) {
                Operator<T> operator = (Operator<T>) executable;
                operators.put(operator.label, operator);
            }
        }

        // initialize patterns
        Pattern functionAndOperatorPattern = Pattern.compile(executables.keySet()
                .stream()
                .map(Pattern::quote)
                .sorted((e1, e2) -> (e2.length() - e1.length()))
                .collect(Collectors.joining("|")));

        Pattern unaryPattern = Pattern.compile("\\+|\\-");
        Pattern separatorPattern = Pattern.compile("\\(|\\)|,");
        Pattern variablePattern = Pattern.compile("[a-zA-Z]+[0-9]*[a-zA-Z]*");
        Pattern whitespacePattern = Pattern.compile("\\s+");

        List<Pattern> numberPatternList = new ArrayList<>();
        for (String patternString : this.getNumberPattern()) {
            Pattern numberPattern = Pattern.compile(patternString);
            numberPatternList.add(numberPattern);
        }

        // initialize parsing variables
        int index = 0;
        Token lastToken = null;
        boolean probableUnary = true;

        // while has more characters
        outer:
        while (index < expr.length()) {
            Matcher matcher;

            // check for separator
            matcher = separatorPattern.matcher(expr.substring(index));
            if (matcher.lookingAt()) {
                String match = matcher.group();
                index += match.length();

                Separator separator = Separator.getSeparator(match);

                if (separator == Separator.OPEN_BRACKET) {
                    addImplicitMultiplication(tokenList, lastToken, operators);
                    probableUnary = true;
                }
                else if (separator == Separator.CLOSE_BRACKET) {
                    probableUnary = false;
                }
                else {
                    probableUnary = true;
                }
                tokenList.add(separator);

                lastToken = separator;

                continue;
            }

            // check for unary operators
            matcher = unaryPattern.matcher(expr.substring(index));
            if (probableUnary && matcher.lookingAt()) {
                String match = matcher.group();
                index += match.length();

                Operator<T> operator = operators.get(match.equals("+") ? Operator.UNARY_PLUS : Operator.UNARY_MINUS);
                tokenList.add(operator);

                probableUnary = false;
                lastToken = operator;

                continue;
            }

            // check for functions and operators
            matcher = functionAndOperatorPattern.matcher(expr.substring(index));
            if (matcher.lookingAt()) {
                String match = matcher.group();
                index += match.length();

                // encountered a function
                if (functions.containsKey(match)) {
                    Function<T> function = functions.get(match);

                    addImplicitMultiplication(tokenList, lastToken, operators);
                    tokenList.add(function);

                    probableUnary = false;
                    lastToken = function;
                }

                // encountered an operator
                else {
                    Operator<T> operator = operators.get(match);

                    if (operator.operatorType == Operator.OperatorType.PREFIX) {
                        addImplicitMultiplication(tokenList, lastToken, operators);
                    }
                    tokenList.add(operator);

                    probableUnary = operator.operatorType != Operator.OperatorType.SUFFIX;
                    lastToken = operator;
                }

                continue;
            }

            // check for numbers
            for (Pattern numberPattern : numberPatternList) {
                matcher = numberPattern.matcher(expr.substring(index));
                if (matcher.lookingAt()) {
                    String match = matcher.group();
                    index += match.length();

                    addImplicitMultiplication(tokenList, lastToken, operators);

                    Operand<T> operand = new Operand<T>(this.stringToOperand(match));
                    tokenList.add(operand);

                    probableUnary = false;
                    lastToken = operand;

                    continue outer;
                }
            }

            // check for variables
            matcher = variablePattern.matcher(expr.substring(index));
            if (matcher.lookingAt()) {
                String match = matcher.group();
                index += match.length();

                addImplicitMultiplication(tokenList, lastToken, operators);

                Variable variable = new Variable(match);
                tokenList.add(variable);

                probableUnary = false;
                lastToken = variable;

                continue;
            }

            // check for whitespace
            matcher = whitespacePattern.matcher(expr.substring(index));
            if (matcher.lookingAt()) {
                String match = matcher.group();
                index += match.length();

                continue;
            }

            // invalid character
            throw new Expr4jException("Invalid expression");
        }
        
        return tokenList;
    }

    /**
     * Add an implicit multiplication operator to the token list.
     *
     * @param tokenList The token list
     * @param lastToken The last token encountered
     * @param operators The list of operators
     */
    private void addImplicitMultiplication(List<Token> tokenList, Token lastToken, Map<String, Operator<T>> operators) {
        if (lastToken instanceof Operator) {
            Operator<T> operator = (Operator<T>) lastToken;
            if (operator.operatorType == Operator.OperatorType.SUFFIX){
                tokenList.add(operators.get(Operator.IMPLICIT_MULTIPLICATION));
            }
        } else if (lastToken instanceof Separator) {
            Separator lastSeparator = (Separator) lastToken;
            if (lastSeparator == Separator.CLOSE_BRACKET) {
                tokenList.add(operators.get(Operator.IMPLICIT_MULTIPLICATION));
            }
        } else if (lastToken instanceof Operand || lastToken instanceof Variable) {
            tokenList.add(operators.get(Operator.IMPLICIT_MULTIPLICATION));
        }
    }

    /**
     * Method to define procedure to obtain operand from string representation.
     *
     * @param operand String representation of operand
     * @return Operand
     */
    protected abstract T stringToOperand(String operand);

    /**
     * Method to define the patterns to identify numbers.<br>
     * Override this method if the patterns to identify numbers need to be customized.
     *
     * @return List of patterns to identify numbers
     */
    protected abstract List<String> getNumberPattern();

}
