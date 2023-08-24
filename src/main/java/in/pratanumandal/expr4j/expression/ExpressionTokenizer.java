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

package in.pratanumandal.expr4j.expression;

import in.pratanumandal.expr4j.exception.Expr4jException;
import in.pratanumandal.expr4j.token.Function;
import in.pratanumandal.expr4j.token.Operand;
import in.pratanumandal.expr4j.token.Operator;
import in.pratanumandal.expr4j.token.OperatorType;
import in.pratanumandal.expr4j.token.Separator;
import in.pratanumandal.expr4j.token.Token;
import in.pratanumandal.expr4j.token.Variable;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * The <code>ExpressionTokenizer&lt;T&gt;</code> class tokenizes expressions independent of the type of operand.
 *
 * @author Pratanu Mandal
 * @since 1.0
 *
 * @param <T> The type of operand
 */
public class ExpressionTokenizer<T> {

    /** Expression dictionary */
    private final ExpressionDictionary<T> expressionDictionary;

    /**
     * Expression confiuration.
     */
    private final ExpressionConfig<T> expressionConfig;

    /**
     * Parameterized constructor.
     *
     * @param expressionDictionary The expression dictionary
     * @param expressionConfig The expression configuration
     */
    public ExpressionTokenizer(ExpressionDictionary<T> expressionDictionary,
                               ExpressionConfig<T> expressionConfig) {
        this.expressionDictionary = expressionDictionary;
        this.expressionConfig = expressionConfig;
    }

    /**
     * Tokenize an expression.
     *
     * @param expr The expression
     * @return The list of tokens
     */
    public List<Token> tokenize(String expr) {
        // do not allow blank expressions
        if (isBlank(expr)) {
            throw new Expr4jException("Invalid expression");
        }

        // list of tokens
        List<Token> tokenList = new ArrayList<>();

        // initialize patterns
        Pattern executablePattern = Pattern.compile(expressionDictionary.getExecutables()
                .stream()
                .map(Pattern::quote)
                .sorted((e1, e2) -> (e2.length() - e1.length()))
                .collect(Collectors.joining("|")));

        Pattern unaryPattern = Pattern.compile("\\+|\\-");
        Pattern separatorPattern = Pattern.compile("\\(|\\)|,");
        Pattern variablePattern = Pattern.compile("[a-zA-Z]+[0-9]*[a-zA-Z]*");
        Pattern whitespacePattern = Pattern.compile("\\s+");

        List<Pattern> operandPatternList = new ArrayList<>();
        for (String patternString : expressionConfig.getOperandPattern()) {
            Pattern operandPattern = Pattern.compile(patternString);
            operandPatternList.add(operandPattern);
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
                    addImplicitMultiplication(tokenList, lastToken);
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

                Operator<T> operator = expressionDictionary.getPrefixOperator(match);
                tokenList.add(operator);

                probableUnary = false;
                lastToken = operator;

                continue;
            }

            // check for executables
            matcher = executablePattern.matcher(expr.substring(index));
            if (matcher.lookingAt()) {
                String match = matcher.group();
                index += match.length();

                // encountered a function
                if (expressionDictionary.hasFunction(match)) {
                    Function<T> function = expressionDictionary.getFunction(match);

                    addImplicitMultiplication(tokenList, lastToken);
                    tokenList.add(function);

                    probableUnary = false;
                    lastToken = function;
                }

                // encountered an operator
                else {
                    Operator<T> operator;

                    if (infixOperatorAllowed(lastToken) &&
                            expressionDictionary.hasInfixOperator(match)) {
                        operator = expressionDictionary.getInfixOperator(match);
                    }
                    else if (postfixOperatorAllowed(lastToken) &&
                            expressionDictionary.hasPostfixOperator(match)) {
                        operator = expressionDictionary.getPostfixOperator(match);
                    }
                    else if (expressionDictionary.hasPrefixOperator(match)) {
                        operator = expressionDictionary.getPrefixOperator(match);
                    }
                    else {
                        throw new Expr4jException("Undefined symbol: " + match);
                    }

                    if (operator.type == OperatorType.PREFIX) {
                        addImplicitMultiplication(tokenList, lastToken);
                    }
                    tokenList.add(operator);

                    probableUnary = operator.type != OperatorType.POSTFIX;
                    lastToken = operator;
                }

                continue;
            }

            // check for operands
            for (Pattern numberPattern : operandPatternList) {
                matcher = numberPattern.matcher(expr.substring(index));
                if (matcher.lookingAt()) {
                    String match = matcher.group();
                    index += match.length();

                    addImplicitMultiplication(tokenList, lastToken);

                    Operand<T> operand = new Operand<T>(expressionConfig.stringToOperand(match));
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

                addImplicitMultiplication(tokenList, lastToken);

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
     * Check if a string is blank or not.
     *
     * @param str The string
     * @return True if blank, false otherwise
     */
    private boolean isBlank(String str) {
        return str == null || str.length() == 0 || str.chars().allMatch(Character::isWhitespace);
    }

    /**
     * Add an implicit multiplication operator to the token list.
     *
     * @param tokenList The token list
     * @param lastToken The last token encountered
     */
    private void addImplicitMultiplication(List<Token> tokenList, Token lastToken) {
        if (lastToken instanceof Operator) {
            Operator<T> operator = (Operator<T>) lastToken;
            if (operator.type == OperatorType.POSTFIX) {
                tokenList.add(expressionDictionary.getInfixOperator("*"));
            }
        }
        else if (lastToken instanceof Separator) {
            Separator lastSeparator = (Separator) lastToken;
            if (lastSeparator == Separator.CLOSE_BRACKET) {
                tokenList.add(expressionDictionary.getInfixOperator("*"));
            }
        }
        else if (lastToken instanceof Operand || lastToken instanceof Variable) {
            tokenList.add(expressionDictionary.getInfixOperator("*"));
        }
    }

    /**
     * Check if postfix operator is allowed at current position.
     *
     * @param lastToken Last token encountered
     * @return True if postfix operator is allowed, false otherwise
     */
    private boolean postfixOperatorAllowed(Token lastToken) {
        if (lastToken == null) {
            return false;
        }

        if (lastToken instanceof Separator) {
            Separator separator = (Separator) lastToken;
            return (separator == Separator.CLOSE_BRACKET);
        }
        else if (lastToken instanceof Operator) {
            Operator<T> operator = (Operator<T>) lastToken;
            return (operator.type == OperatorType.POSTFIX);
        }
        else if (lastToken instanceof Operand || lastToken instanceof Variable) {
            return true;
        }

        return false;
    }

    /**
     * Check if infix operator is allowed at current position.
     *
     * @param lastToken Last token encountered
     * @return True if infix operator is allowed, false otherwise
     */
    private boolean infixOperatorAllowed(Token lastToken) {
        if (lastToken == null) {
            return false;
        }

        if (lastToken instanceof Separator) {
            Separator separator = (Separator) lastToken;
            return (separator == Separator.CLOSE_BRACKET);
        }
        else if (lastToken instanceof Operator) {
            Operator<T> operator = (Operator<T>) lastToken;
            return (operator.type == OperatorType.POSTFIX);
        }
        else if (lastToken instanceof Operand || lastToken instanceof Variable) {
            return true;
        }

        return false;
    }

}
