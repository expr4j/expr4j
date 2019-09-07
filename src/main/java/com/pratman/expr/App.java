package com.pratman.expr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App {
	
	public static final BufferedReader BR = new BufferedReader(new InputStreamReader(System.in));
	
	
    public static void main(String[] args) throws IOException {
    	
    	System.out.print("Enter expression: ");
    	String expr = BR.readLine();
    	
    	ShuntingYard sy = new ShuntingYardDualStack();
    	double result = sy.evaluate(expr);
    	
    	System.out.println("Result: " + result);
    	
    }
    
}
