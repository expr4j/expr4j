<p>
    <img src="https://raw.githubusercontent.com/prat-man/expr4j/dev/images/expr4j.png" height="100px">
    <img src="https://raw.githubusercontent.com/prat-man/expr4j/dev/images/java.png" height="100px" align="right">
</p>

# Expression Evaluator for Java
This is a Java library to parse and evaluate mathematical expression strings.

The expressions are evaluated using [Dijkstra's Shunting Yard algorithm](https://en.wikipedia.org/wiki/Shunting-yard_algorithm). An expression tree is created from the postfix (or RPN) expression which is then parsed to evaluate the expression.

> **_NOTE:_** &nbsp; Version 1.x of Expr4j is a complete rewrite of the library. The new API is incompatible with the 0.0.x versions.

<br/>

## Key Features
- Supports numerical functions, operations, constants, and variables.
- Written using generics to allow easy extension for any type of operands including user defined types.
- Multiple predefined implementations including BigDecimal and Complex numbers.
- Supports addition of custom functions and operators.
- Functions can be defined with a variable number of arguments.
- Supports implicit multiplication, ex: `2x` is treated as `2*x` and `(a+b)(a-b)` is treated as `(a+b)*(a-b)`.
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

<br/>

## Implementations
Three predefined implementations are provided. However, the library is completely written using generics so it can be easily used with any type of operands.

| Implementation | Description                                                                                                                                                                                                                                                                                                           |
|----------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Double         | Implementation for Double operands. It is the fastest implementation provided.                                                                                                                                                                                                                                        |
| BigDecimal     | Implementation for BigDecimal operands. It is slower than the Double implementation but allows for high precision.                                                                                                                                                                                                    |
| Complex        | Implementation for complex numbers. It uses the [Complex](https://commons.apache.org/proper/commons-numbers/commons-numbers-complex/javadocs/api-1.1/org/apache/commons/numbers/complex/Complex.html) type from [Apache Commons Numbers](https://commons.apache.org/proper/commons-numbers/commons-numbers-complex/). |
