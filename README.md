# Expression Evaluator

## Introduction
This is a simple Java library to evaluate mathematical expressions.

The expressions are evaluated using Shunting Yard algorithm.<br/>
https://en.wikipedia.org/wiki/Shunting-yard_algorithm

<br/>

## Implementations
Two implementations of the Shunting Yard algorithm are provided:


| Implementation | Description |
| -------------- | ----------- |
| Dual Stack<br/>(*recommended*) | Two stacks are used to immediately evaluate the expression without generating the postfix (or RPN) expression. This is the recommended implementation since theoretically it should use less memory and require less time due to lesser complexity |
| Expression Tree | An expression tree is created from the postfix (or RPN) expression. The expression tree is then parsed to evaluate the expression. |
