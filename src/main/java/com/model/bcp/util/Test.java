package com.model.bcp.util;

import rx.Observable;

public class Test {

	public static void main(String[] args) {
		Integer[] numbers = {3,1,5,8,65,8,2,1,7,9,6,4};
		
		Observable.from(numbers)
		.filter(number -> number > 4)
		.first()
		.subscribe(number -> System.out.println("Este es el primer numero mayor que 4:"+ number));
	
		
	}
}
