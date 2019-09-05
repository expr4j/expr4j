package com.pramanda.expr;

public class App {
	
    public static void main(String[] args) {
    	
    	ShuntingYard sy = new ShuntingYard();
    	
    	double result = sy.evaluate("5+3/cossin-6^0.25");
    	
    	System.out.println(result);
    	
    }
    
}
