<p>
    <img src="https://raw.githubusercontent.com/prat-man/expr4j/dev/images/expr4j.png" height="100px">
    <img src="https://raw.githubusercontent.com/prat-man/expr4j/dev/images/java.png" height="100px" align="right">
</p>

# Expression Evaluator for Java
This is a Java library to parse and evaluate mathematical expression strings.

The expressions are evaluated using [Dijkstra's Shunting Yard algorithm](https://en.wikipedia.org/wiki/Shunting-yard_algorithm). An expression tree is created from the postfix (or RPN) expression which is then parsed to evaluate the expression.

<br/>

## Dependency Management

### Maven
    <dependency>
        <groupId>in.pratanumandal</groupId>
        <artifactId>expr4j</artifactId>
        <version>1.0</version>
    </dependency>

### Gradle
    dependencies {
        implementation 'in.pratanumandal:expr4j:1.0'
    }

<br/>

## Implementations
Three predefined implementations are provided. However, the library is completely written using generics so it can be easily used with any type of operands.

| Implementation | Description |
| -------------- | ----------- |
| Double         | Implementation for double operands. It is the fastest implementation provided. |
| BigDecimal     | Implementation for BigDecimal operands. It is slower than the Double implementation but allows for high precision. |
| Complex        | Implementation for complex numbers. It uses the Complex type from [Apache Commons Math](https://commons.apache.org/proper/commons-math/javadocs/api-3.6.1/org/apache/commons/math3/complex/Complex.html). |
