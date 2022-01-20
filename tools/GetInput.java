package tools;

import java.util.Scanner;

public class GetInput {
	static Scanner scanner = new Scanner(java.lang.System.in);
	
	public static int get_number() {
		int number;
		while(true) {
	    	try {
	    		number = Integer.parseInt(scanner.nextLine());
	    		return number;
	    	}catch (Exception e){
	            System.out.println("your input must be a number");
	            continue;
	        }
		}
	}

	public static String get_string() {
		return scanner.nextLine();
	}
}

