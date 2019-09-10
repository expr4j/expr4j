# Expression Evaluator for Java

![expr4j logo](expr4j.png)

<br/>

## Introduction
This is a simple Java library to evaluate mathematical expressions.

The expressions are evaluated using Dijkstra's Shunting Yard algorithm.<br/>
https://en.wikipedia.org/wiki/Shunting-yard_algorithm

<br/>

## Implementations
Two implementations of the Shunting Yard algorithm are provided:


| Implementation | Description |
| -------------- | ----------- |
| Dual Stack<br/>*(recommended)* | Two stacks are used to immediately evaluate the expression without generating the postfix (or RPN) expression. This is the recommended implementation since theoretically it should use less memory and require less time due to lesser complexity. |
| Expression Tree | An expression tree is created from the postfix (or RPN) expression. The expression tree is then parsed to evaluate the expression. |

<br/>

## Operators


| Operator | Description    |
| :------: | -------------- |
| ( )      | Parenthesis    |
| +        | Unary Plus     |
| -        | Unary Minus    |
| +        | Addition       |
| -        | Subtraction    |
| *        | Multiplication |
| /        | Division       |
| %        | Remainder      |
| ^        | Exponent       |

<br/>

## Variables & Constants


| Variable / Constant | Description                                       |
| ------------------- | ------------------------------------------------- |
| rand                | Random number between 0 and 1                     |
| pi                  | Ratio of a circle's circumference to its diameter |
| e                   | Euler's number                                    |

<br/>


## Functions


| Function  | Description                   |
| --------- | ----------------------------- |
| abs(x)    | Absolute value                |
| sin(x)    | Sine                          |
| cos(x)    | Cosine                        |
| tan(x)    | Tangent                       |
| asin(x)   | Arc Sine                      |
| acos(x)   | Arc Cosine                    |
| atan(x)   | Arc Tangent                   |
| sinh(x)   | Hyperbolic Sine               |
| cosh(x)   | Hyperbolic Cosine             |
| tanh(x)   | Hyperbolic Tangent            |
| asinh(x)  | Area Hyperbolic Sine          |
| acosh(x)  | Area Hyperbolic Cosine        |
| atanh(x)  | Area Hyperbolic Tangent       |
| deg(x)    | Radian to Degree              |
| rad(x)    | Degree to Radian              |
| round(x)  | Round off to nearest integer  |
| floor(x)  | Mathematical floor function   |
| ceil(x)   | Mathematical ceiling function |
| ln(x)     | Natural logarithm of x        |
| log10(x)  | Log of x to the base 10       |
| log(x, b) | Log of x to the base b        |
| sqrt(x)   | Square root                   |
| cbrt(x)   | Cube root                     |
| fact(x)   | Factorial of x                |
| max(x, y) | Maximum of x and y            |
| min(x, y) | Minimum of x and y            |
