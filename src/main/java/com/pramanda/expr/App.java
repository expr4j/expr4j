package com.pramanda.expr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App {
	
	public static final BufferedReader BR = new BufferedReader(new InputStreamReader(System.in));
	
	
    public static void main(String[] args) throws IOException {
    	
    	System.out.print("Enter expression: ");
    	String expr = BR.readLine();
    	
    	long startTime = System.nanoTime();
    	
    	Runtime runtime = Runtime.getRuntime();
        long usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();
    	
    	ShuntingYard sy = new ShuntingYardTree();
    	double result = sy.evaluate(expr);
    	
        long usedMemoryAfter = runtime.totalMemory() - runtime.freeMemory();
    	long endTime = System.nanoTime();

    	long duration = (endTime - startTime) / 1000000;
    	long memoryUsed = (usedMemoryAfter - usedMemoryBefore);
    	
    	System.out.println("Result: " + result);
    	
    	System.out.println("\nTime taken: " + duration);
    	System.out.println("Memory utilized: " + memoryUsed);
    	
    }
    
}
