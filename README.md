<p>
    <img src="https://raw.githubusercontent.com/expr4j/expr4j/main/images/expr4j-rounded.png" height="100px">
    <img src="https://raw.githubusercontent.com/expr4j/expr4j/main/images/java.png" height="100px" align="right">
</p>

# Expression Evaluator for Java

![Maven](https://github.com/expr4j/expr4j/actions/workflows/maven.yml/badge.svg)

Expr4j is a Java library to parse and evaluate mathematical expression strings.

The expressions are evaluated using [Dijkstra's Shunting Yard algorithm](https://en.wikipedia.org/wiki/Shunting-yard_algorithm). An expression tree is created from the postfix (or RPN) expression which is then parsed to evaluate the expression. The library is written entirely using generics so it can be used with any type of operands.

Predefined implementations are provided in the packages [expr4j-double](https://github.com/expr4j/expr4j-double), [expr4j-bigdecimal](https://github.com/expr4j/expr4j-bigdecimal), and [expr4j-complex](https://github.com/expr4j/expr4j-complex).

> **Note**<br/>
> Version 1.x of Expr4j is a complete rewrite of the library. The new API is incompatible with the 0.0.x versions.

<br/>

## Key Features
- Supports numerical functions, operations, constants, and variables.
- Written using generics to allow easy extension for any type of operands including user defined types.
- Multiple predefined implementations including BigDecimal and Complex numbers.
- Supports addition of custom functions and operators.
- Functions can be defined with a variable number of arguments.
- Supports implicit multiplication, ex: `2x` is treated as `2*x` and `(a+b)(a-b)` is treated as `(a+b)*(a-b)`.
- Supports lazy evaluation of functions and operators for improved performance.
- Supports asynchronous evaluation for improved performance.
- Supports scientific notations of numbers.

<br/>

## Dependency Management

### Maven
```xml
<dependency>
    <groupId>in.pratanumandal</groupId>
    <artifactId>expr4j</artifactId>
    <version>1.0</version>
</dependency>
```

### Gradle
```gradle
dependencies {
    implementation 'in.pratanumandal:expr4j:1.0'
}
```
